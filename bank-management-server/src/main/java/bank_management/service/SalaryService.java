package bank_management.service;

import bank_management.dto.EmployeeDto;
import bank_management.payload.BankAccountAndCommission;
import bank_management.dto.BankAccountDto;
import bank_management.payload.DetailSalary;
import bank_management.dto.SalaryDto;
import bank_management.entity.*;
import bank_management.enumeration.BankAccountType;
import bank_management.payload.SearchSalaryRequest;
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
        Date currentDate = new Date();
        int currentYear = DateUtils.getYear(currentDate);
        int currentMonth = DateUtils.getMonth(currentDate);
        List<Salary> salaries = salaryRepository.findAllByMonthAndYearOrderByMonth(currentMonth - 1, currentYear);
        if(salaries == null) {
            calcSalary(currentMonth-1, currentYear);
            salaries = salaryRepository.findAllByMonthAndYearOrderByMonth(currentMonth-1, currentYear);
        }
        List<SalaryDto> salaryDtoList = new ArrayList<>();
        for (Salary salary : salaries) {
            SalaryDto salaryDto = new SalaryDto(salary);
            salaryDto.setEmployee(employeeRepository.findById(salary.getEmployee().getID()).get());
            salaryDtoList.add(salaryDto);
        }
        return salaryDtoList;
    }

    public List<SalaryDto> searchSalaries(SearchSalaryRequest searchSalaryRequest) {
        String position = searchSalaryRequest.getPosition();
        int startMonth = searchSalaryRequest.getStartMonth();
        int startYear = searchSalaryRequest.getStartYear();
        int endMonth = searchSalaryRequest.getEndMonth();
        int endYear = searchSalaryRequest.getEndYear();
        List<Salary> salaryList = new ArrayList<>();
        if (startMonth > 0 || startYear > 0 || endMonth > 0 || endYear > 0) {
            startYear = (startYear > endYear) ? startYear : endYear;
            //n???u ch??a t??nh l????ng th?? t??nh l????ng
            Date currentDate = new Date();
            int currentYear = DateUtils.getYear(currentDate);
            int currentMonth = DateUtils.getMonth(currentDate);
            int currentDay = DateUtils.getDay(currentDate);
            ;
            int lastestDayOfMonth = DateUtils.getLastestDayOfMonth(currentDate);
            for (int year = startYear; year <= endYear; year++) {
                for (int month = 1; month <= 12; month++) {
                    if (year < currentYear) { //t??nh h???t c??c n??m tr?????c n??m hi???n t???i
                        calcSalary(month, year);
                    } else if (year == currentYear) { // n??m hi???n t???i
                        if (month < currentMonth) { // n???u th??ng nh??? h??n th??ng hi???n t???i
                            calcSalary(month, year);
                        } else if (month == currentMonth && currentDay == lastestDayOfMonth) { // n???u l?? th??ng hi???n t???i v?? ng??y hi???n t???i l?? cu???i th??ng
                            calcSalary(month, year);
                        }
                    }
                }
            }
            salaryList = salaryRepository.searchSalary(startMonth, startYear, endMonth, endYear);
            if (!position.equals("All")) {
                salaryList.removeIf(b -> !b.getEmployee().getPosition().name().equals(position));
            }
        } else if (!position.equals("All")){
            salaryList = salaryRepository.searchSalaryByPosition(position);
        } else {
            salaryList = salaryRepository.findAllOrderByMonthAndYear();
        }

        List<SalaryDto> salaryDtoList = new ArrayList<>();
        for (Salary salary : salaryList) {
            SalaryDto salaryDto = new SalaryDto(salary);
            salaryDto.setEmployee(employeeRepository.findById(salary.getEmployee().getID()).get());
            salaryDtoList.add(salaryDto);
        }
        return salaryDtoList;
    }

    public List<SalaryDto> getSalaryL??stForEachEmployee(String ID) {
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
                // t??m c??c t??i kho???n credit ???????c t???o theo trong timeline l????ng
                if (bankAccount.getType().equals(BankAccountType.Credit)) {
                    Date createDate = bankAccount.getCreateDate();
                    if (month == DateUtils.getMonth(createDate) && year == DateUtils.getYear(createDate) && bankAccount.isStatus()) {
                        BankAccountDto bankAccountDto = new BankAccountDto(bankAccount);
                        bankAccountDto.setEmployee(employee);
                        list.add(new BankAccountAndCommission(bankAccountDto, 500000.0)); // credit th?? commission = 500000
                    }
                }

                // t??m c??c t??i kho???n payment c?? giao d???ch l???n ?????u ti??n theo trong timeline l????ng
                if (bankAccount.getType().equals(BankAccountType.Payment)) {
                    Transaction transaction = transactionRepository.getAmountOfFirstTransactionByPaymentAccount(bankAccount.getID());
                    if (transaction != null) {
                        Date createDate = transaction.getCreateDate();
                        if (month == DateUtils.getMonth(createDate) && year == DateUtils.getYear(createDate) && bankAccount.isStatus()) {
                            BankAccountDto bankAccountDto = new BankAccountDto(bankAccount);
                            bankAccountDto.setEmployee(employee);
                            //n???u l?? payment th?? commission = 0.02 * ti???n g???i l???n ?????u ti??n
                            list.add(new BankAccountAndCommission(bankAccountDto, 0.02 * transaction.getAmount()));
                        }
                    }
                }
            }
            EmployeeDto employeeDto = new EmployeeDto(employee);
            employeeDto.setBankAccountList(null);
            employeeDto.setSalaryList(null);

            SalaryDto salaryDto = new SalaryDto(salary);
            salary.setEmployee(null);
            DetailSalary detailSalary = new DetailSalary(employeeDto, salaryDto, list);
            return detailSalary;
        }
        return null;
    }

    public void calcSalary(int month, int year) {
        // ki???m tra xem month - year ???? t??nh l????ng ch??a
        List<Salary> salaries = salaryRepository.findByMonthAndYear(month, year);
        if (salaries.size() == 0) { // ch??a t??nh
            List<SalaryDto> salaryDtoList = new ArrayList<>();
            List<Employee> employees = employeeRepository.findAll();
            for (Employee employee : employees) {
                List<BankAccount> bankAccountList = employee.getBankAccountList();
                double salary_ = employee.getBaseSalary();
                for (BankAccount bankAccount : bankAccountList) {
                    // t??m c??c t??i kho???n credit ???????c t???o theo trong month - year
                    if (bankAccount.getType().equals(BankAccountType.Credit)) {

                        Date createDate = bankAccount.getCreateDate();
                        // t??m t??i kho???n ch??a ???????c c???ng hoa h???ng, status = false
                        if (month == DateUtils.getMonth(createDate) && year == DateUtils.getYear(createDate)) {
                            salary_ += 500000.0; // credit th?? commission = 500000
                            bankAccount.setStatus(true); // set l???i tr???ng th??i ???? + hoa h???ng
                        }
                    }

                    // t??m c??c t??i kho???n payment c?? giao d???ch l???n ?????u ti??n theo trong month - year
                    if (bankAccount.getType().equals(BankAccountType.Payment)) {
//                        System.out.println(bankAccount.getID());
                        Transaction transaction = transactionRepository.getAmountOfFirstTransactionByPaymentAccount(bankAccount.getID());
//                        System.out.println(transaction);
                        if (transaction != null) {
                            Date createDate = transaction.getCreateDate();
                            // t??m t??i kho???n ch??a ???????c c???ng hoa h???ng, status = false
                            if (month == DateUtils.getMonth(createDate) && year == DateUtils.getYear(createDate) && !bankAccount.isStatus()) {
                                //n???u l?? payment th?? commission = 0.02 * ti???n g???i l???n ?????u ti??n
                                salary_ += 0.02 * transaction.getAmount();
//                                System.out.println("checked");
                                bankAccount.setStatus(true); // set l???i tr???ng th??i ???? + hoa h???ng
                            }
                        }
                    }
                }
                employee.setBankAccountList(bankAccountList); // set l???i danh s??ch bankAccount
                employee = employeeRepository.save(employee); // l??u l???i employee v??o db (???? g???m l??u c??? list bankAccount)
                Salary salary = new Salary(salary_, month, year, employee);
                salary = salaryRepository.save(salary); //l??u b???ng l????ng v??o db
                SalaryDto salaryDto = new SalaryDto(salary); // convert sang dto
                salaryDto.setEmployee(employee);
                salaryDtoList.add(salaryDto);
            }
//            return salaryDtoList;
        }
//        else return null;
    }


}
