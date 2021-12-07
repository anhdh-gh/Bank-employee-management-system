package bank_management.service;

import bank_management.dto.BankAccountDto;
import bank_management.dto.SalaryDto;
import bank_management.entity.Salary;
import bank_management.repository.EmployeeRepository;
import bank_management.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SalaryService {
    @Autowired
    SalaryRepository salaryRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public List<SalaryDto> getAllSalary() {
        List<Salary> salaries = salaryRepository.findAll();
        List<SalaryDto> salaryDtoList = new ArrayList<>();
        for (Salary salary : salaries) {
            SalaryDto salaryDto = new SalaryDto(salary);
            salaryDto.setEmployee(employeeRepository.findById(salary.getEmployee().getID()).get());
            salaryDtoList.add(salaryDto);
        }
        return salaryDtoList;
    }

    public List<SalaryDto> getSalaryLístForEachEmployee(String ID) {
        List<Salary> salaries = salaryRepository.findAllByEmployee_ID(ID);
        List<SalaryDto> salaryDtoList = new ArrayList<>();
        for (Salary salary : salaries) {
            SalaryDto salaryDto = new SalaryDto(salary);
            salaryDtoList.add(salaryDto);
        }
        return salaryDtoList;
    }

    public List<BankAccountDto> getDetailSalary(String salaryID) {
       Optional<Salary> optionalSalary = salaryRepository.findById(salaryID);
       if (optionalSalary.isPresent()) {
           Salary salary = optionalSalary.get();
           int month = salary.getMonth();
           int year = salary.getYear();

       }
       return null;
    }

}
