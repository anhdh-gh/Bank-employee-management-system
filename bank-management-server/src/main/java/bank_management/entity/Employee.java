package bank_management.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

import bank_management.dto.BankAccountDto;
import bank_management.dto.EmployeeDto;
import bank_management.dto.SalaryDto;
import bank_management.enumeration.Gender;
import bank_management.enumeration.Position;
import bank_management.enumeration.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee extends User {

	@Column(name = "BaseSalary")
	@Positive(message = "BaseSalary phải là số dương")
	@NotNull(message = "BaseSalary không được để trống")
	private double baseSalary;

	@ManyToOne(targetEntity = Manager.class)
	@JoinColumn(name = "ManagerID")
	private Manager manager;

	@JsonIgnore
	@OneToMany(targetEntity = BankAccount.class, mappedBy = "employee", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@Fetch(FetchMode.SUBSELECT)
	private List<BankAccount> bankAccountList;

	@JsonIgnore
	@OneToMany(targetEntity = Salary.class, mappedBy = "employee", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<Salary> salaryList;

	public Employee(@NotBlank(message = "EmployeeCode không được để trống") @Size(max = 30, message = "EmployeeCode tối đa 30 ký tự") String employeeCode, @NotNull(message = "Role không được để trống") Role role, @PositiveOrZero(message = "Seniority phải lớn hơn 0") double seniority, @NotNull(message = "Position không được để trống") Position position, double baseSalary, Manager manager, List<BankAccount> bankAccountList, List<Salary> salaryList) {
		super(employeeCode, role, seniority, position);
		this.baseSalary = baseSalary;
		this.bankAccountList = bankAccountList;
		this.salaryList = salaryList;
		this.manager = manager;
	}

	public Employee(String identityNumber, Date dateOfBirth, String email, String phoneNumber, Gender gender, Account account, Address address, FullName fullName, String employeeCode, Role role, double seniority, Position position, double baseSalary, Manager manager, List<BankAccount> bankAccountList, List<Salary> salaryList) {
		super(identityNumber, dateOfBirth, email, phoneNumber, gender, account, address, fullName, employeeCode, role, seniority, position);
		this.baseSalary = baseSalary;
		this.bankAccountList = bankAccountList;
		this.salaryList = salaryList;
		this.manager = manager;
	}

	public Employee(String identityNumber, Date dateOfBirth, String email, String phoneNumber, Gender gender, String username, String password, String city, String district, String country, String houseNumber, String zipCode, String firstName, String lastName, String employeeCode, Role role, double seniority, Position position, double baseSalary, Manager manager, List<BankAccount> bankAccountList, List<Salary> salaryList) {
		super(identityNumber, dateOfBirth, email, phoneNumber, gender, username, password, city, district, country, houseNumber, zipCode, firstName, lastName, employeeCode, role, seniority, position);
		this.baseSalary = baseSalary;
		this.bankAccountList = bankAccountList;
		this.salaryList = salaryList;
		this.manager = manager;
	}

	public Employee(EmployeeDto employeeDto) {
		super(employeeDto.getIdentityNumber(), employeeDto.getDateOfBirth(), employeeDto.getEmail(), employeeDto.getPhoneNumber(),
			employeeDto.getGender(), employeeDto.getAccount().getUsername(), employeeDto.getAccount().getPassword(),
				employeeDto.getAddress().getCity(), employeeDto.getAddress().getDistrict(), employeeDto.getAddress().getCountry(),
				employeeDto.getAddress().getHouseNumber(), employeeDto.getAddress().getZipCode(), employeeDto.getFullName().getFirstName(),
				employeeDto.getFullName().getLastName(), employeeDto.getEmployeeCode(), employeeDto.getRole(), employeeDto.getSeniority(), employeeDto.getPosition());
		this.baseSalary = employeeDto.getBaseSalary();
		List<BankAccount> bankAccounts = new ArrayList<>();
		for(BankAccountDto bankAccountDto : employeeDto.getBankAccountList()) {
			bankAccounts.add(new BankAccount(bankAccountDto));
		}
		this.bankAccountList = bankAccounts;
		List<Salary> salaries = new ArrayList<>();
		for(SalaryDto salaryDto : employeeDto.getSalaryList()) {
			salaries.add(new Salary(salaryDto));
		}
		this.salaryList = salaries;
		this.manager = employeeDto.getManager();
	}
}