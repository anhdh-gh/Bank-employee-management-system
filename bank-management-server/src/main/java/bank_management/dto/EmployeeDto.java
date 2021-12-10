package bank_management.dto;

import bank_management.entity.BankAccount;
import bank_management.entity.Employee;
import bank_management.entity.Manager;
import bank_management.entity.Salary;
import bank_management.enumeration.Gender;
import bank_management.enumeration.Position;
import bank_management.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeDto {
    private String ID;
    private AccountDto account;
    private String identityNumber;
    private Date dateOfBirth;
    private String email;
    private String phoneNumber;
    @Enumerated
    private Gender gender;
    private String employeeCode;
    @Enumerated
    private Role role;
    private double seniority;
    private Position position;
    private double baseSalary;
    private FullnameDto fullname;
    private AddressDto address;
    private List<BankAccount> bankAccountList;
    private List<Salary> salaryList;
    private Manager manager;
    private Date createDate;

    public EmployeeDto(Employee employee) {
        employee.getAccount().setPassword("");
        this.ID = employee.getID();
        this.account = new AccountDto(employee.getAccount());
        this.identityNumber = employee.getIdentityNumber();
        this.dateOfBirth = employee.getDateOfBirth();
        this.email = employee.getEmail();
        this.phoneNumber = employee.getPhoneNumber();
        this.gender = employee.getGender();
        this.employeeCode = employee.getEmployeeCode();
        this.role = employee.getRole();
        this.seniority = employee.getSeniority();
        this.position = employee.getPosition();
        this.baseSalary = employee.getBaseSalary();
        this.fullname = new FullnameDto(employee.getFullName());
        this.address = new AddressDto(employee.getAddress());
        this.bankAccountList = employee.getBankAccountList();
        this.salaryList = employee.getSalaryList();
        this.createDate = employee.getCreateDate();
    }
}
