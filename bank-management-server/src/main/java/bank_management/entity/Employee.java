package bank_management.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

import bank_management.enumeration.Gender;
import bank_management.enumeration.Position;
import bank_management.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED)
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

	@OneToMany(targetEntity = BankAccount.class, mappedBy = "employee")
	private List<BankAccount> bankAccountList;

	@OneToMany(targetEntity = Salary.class, mappedBy = "employee")
	private List<Salary> salaryList;

	public Employee(@NotBlank(message = "EmployeeCode không được để trống") @Size(max = 30, message = "EmployeeCode tối đa 30 ký tự") String employeeCode, @NotNull(message = "Role không được để trống") Role role, @PositiveOrZero(message = "Seniority phải lớn hơn 0") double seniority, @NotNull(message = "Position không được để trống") Position position, double baseSalary, List<BankAccount> bankAccountList, List<Salary> salaryList) {
		super(employeeCode, role, seniority, position);
		this.baseSalary = baseSalary;
		this.bankAccountList = bankAccountList;
		this.salaryList = salaryList;
	}

	public Employee(String identityNumber, Date dateOfBirth, String email, String phoneNumber, Gender gender, Account account, Address address, FullName fullName, String employeeCode, Role role, double seniority, Position position, double baseSalary, List<BankAccount> bankAccountList, List<Salary> salaryList) {
		super(identityNumber, dateOfBirth, email, phoneNumber, gender, account, address, fullName, employeeCode, role, seniority, position);
		this.baseSalary = baseSalary;
		this.bankAccountList = bankAccountList;
		this.salaryList = salaryList;
	}

	public Employee(String identityNumber, Date dateOfBirth, String email, String phoneNumber, Gender gender, String username, String password, String city, String district, String country, String houseNumber, String zipCode, String firstName, String lastName, String employeeCode, Role role, double seniority, Position position, double baseSalary, List<BankAccount> bankAccountList, List<Salary> salaryList) {
		super(identityNumber, dateOfBirth, email, phoneNumber, gender, username, password, city, district, country, houseNumber, zipCode, firstName, lastName, employeeCode, role, seniority, position);
		this.baseSalary = baseSalary;
		this.bankAccountList = bankAccountList;
		this.salaryList = salaryList;
	}
}