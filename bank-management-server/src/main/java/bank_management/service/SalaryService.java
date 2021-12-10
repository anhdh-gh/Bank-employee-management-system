package bank_management.service;

import bank_management.payload.BankAccountAndCommission;
import bank_management.dto.BankAccountDto;
import bank_management.payload.DetailSalary;
import bank_management.dto.SalaryDto;
import bank_management.entity.*;
import bank_management.enumeration.BankAccountType;
import bank_management.repository.EmployeeRepository;
import bank_management.repository.SalaryRepository;
import bank_management.repository.TransactionRepository;
import bank_management.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SalaryService {
    @Autowired
    SalaryRepository salaryRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public List<SalaryDto> getAllSalary() {
        List<Salary> salaries = salaryRepository.findAllOrderByMonthAndYear();
        List<SalaryDto> salaryDtoList = new ArrayList<>();
        for (Salary salary : salaries) {
            SalaryDto salaryDto = new SalaryDto(salary);
            salaryDto.setEmployee(employeeRepository.findById(salary.getEmployee().getID()).get());
            salaryDtoList.add(salaryDto);
        }
        return salaryDtoList;
    }

    public List<SalaryDto> getSalaryLístForEachEmployee(String ID) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(ID);
        if (optionalEmployee.isPresent()) {
            List<Salary> salaries = optionalEmployee.get().getSalaryList();
            List<SalaryDto> salaryDtoList = new ArrayList<>();
            for (Salary salary : salaries) {
                SalaryDto salaryDto = new SalaryDto(salary);
                salaryDtoList.add(salaryDto);
            }
            return salaryDtoList;
        }
        return null;
    }

    public DetailSalary getDetailSalary(String salaryID) {
        Optional<Salary> optionalSalary = salaryRepository.findById(salaryID);
        if (optionalSalary.isPresent()) {
            List<BankAccountAndCommission> list = new ArrayList<>();
            Salary salary = optionalSalary.get();
            int month = salary.getMonth();
            int year = salary.getYear();
            Employee employee = salary.getEmployee();
            List<BankAccount> bankAccountList = employee.getBankAccountList();
            for (BankAccount bankAccount : bankAccountList) {
                // tìm các tài khoản credit được tạo theo trong timeline lương
                if (bankAccount.getType().equals(BankAccountType.Credit)) {
                    Date createDate = bankAccount.getCreateDate();
                    if (month == DateUtils.getMonth(createDate) && year == DateUtils.getYear(createDate) && bankAccount.isStatus()) {
                        BankAccountDto bankAccountDto = new BankAccountDto(bankAccount);
                        bankAccountDto.setEmployee(employee);
                        list.add(new BankAccountAndCommission(bankAccountDto, 500000.0)); // credit thì commission = 500000
                    }
                }

                // tìm các tài khoản payment có giao dịch lần đầu tiên theo trong timeline lương
                if (bankAccount.getType().equals(BankAccountType.Payment)) {
                    Transaction transaction = transactionRepository.findTransactionByBankAccountReceiveOrderByCreateDate(bankAccount);
                    Date createDate = transaction.getCreateDate();
                    if (month == DateUtils.getMonth(createDate) && year == DateUtils.getYear(createDate) && bankAccount.isStatus()) {
                        BankAccountDto bankAccountDto = new BankAccountDto(bankAccount);
                        bankAccountDto.setEmployee(employee);
                        //nếu là payment thì commission = 0.02 * tiền gửi lần đầu tiên
                        list.add(new BankAccountAndCommission(bankAccountDto, 0.02 * transaction.getAmount()));
                    }
                }
            }
            DetailSalary detailSalary = new DetailSalary(employee.getEmployeeCode(), salary.getMonth(), salary.getYear(), employee.getBaseSalary(), list);
            return detailSalary;
        }
        return null;
    }

    public List<SalaryDto> calcSalary(int month, int year) {
        // kiểm tra xem month - year đã tính lương chưa
        List<Salary> salaries = salaryRepository.findByMonthAndYear(month, year);
        if (salaries.size() == 0) { // chưa tính
            List<SalaryDto> salaryDtoList = new ArrayList<>();
            List<Employee> employees = employeeRepository.findAll();
            for (Employee employee : employees) {
                List<BankAccount> bankAccountList = employee.getBankAccountList();
                double salary_ = employee.getBaseSalary();
                for (BankAccount bankAccount : bankAccountList) {
                    // tìm các tài khoản credit được tạo theo trong month - year
                    if (bankAccount.getType().equals(BankAccountType.Credit)) {
                        Date createDate = bankAccount.getCreateDate();
                        // tìm tài khoản chưa được cộng hoa hồng, status = false
                        if (month == DateUtils.getMonth(createDate) && year == DateUtils.getYear(createDate) && !bankAccount.isStatus()) {
                            salary_ += 500000.0; // credit thì commission = 500000
                            bankAccount.setStatus(true); // set lại trạng thái đã + hoa hồng
                        }
                    }

                    // tìm các tài khoản payment có giao dịch lần đầu tiên theo trong month - year
                    if (bankAccount.getType().equals(BankAccountType.Payment)) {
                        Transaction transaction = transactionRepository.findTransactionByBankAccountReceiveOrderByCreateDate(bankAccount);
                        Date createDate = transaction.getCreateDate();
                        // tìm tài khoản chưa được cộng hoa hồng, status = false
                        if (month == DateUtils.getMonth(createDate) && year == DateUtils.getYear(createDate) && !bankAccount.isStatus()) {
                            //nếu là payment thì commission = 0.02 * tiền gửi lần đầu tiên
                            salary_ += 0.02 * transaction.getAmount();
                            bankAccount.setStatus(true); // set lại trạng thái đã + hoa hồng
                        }
                    }
                }
                employee.setBankAccountList(bankAccountList); // set lại danh sách bankAccount
                employee = employeeRepository.save(employee); // lưu lại employee vào db (đã gồm lưu cả list bankAccount)
                Salary salary = new Salary(salary_, month, year, employee);
                salary = salaryRepository.save(salary); //lưu bảng lương vào db
                SalaryDto salaryDto = new SalaryDto(salary); // convert sang dto
                salaryDto.setEmployee(employee);
                salaryDtoList.add(salaryDto);
            }
            return salaryDtoList;
        }
        else return null;
    }

}
