 package bank_management.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.*;

import bank_management.enumeration.Gender;
import bank_management.enumeration.Position;
import bank_management.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Inheritance(strategy = InheritanceType.JOINED)
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends People {
	
	@Column(name = "EmployeeCode")
	@NotBlank(message = "EmployeeCode không được để trống")
	@Size(max = 30, message = "EmployeeCode tối đa 30 ký tự")
	protected String employeeCode;

	@NotNull(message = "Role không được để trống")
	@Column(name = "Role")
	@Enumerated(EnumType.STRING)
	protected Role role;

	@PositiveOrZero(message = "Seniority phải lớn hơn 0")
	@Column(name = "Seniority")
	protected double seniority;

	@NotNull(message = "Position không được để trống")
	@Column(name = "Position")
	@Enumerated(EnumType.STRING)
	protected Position position;

	public User(@NotBlank(message = "IdentityNumber không được để trống") @Digits(message = "IdentityNumber chỉ chứa chữ số", fraction = 0, integer = 15) String identityNumber, @NotNull(message = "DateOfBirth không được để trống") Date dateOfBirth, @NotBlank(message = "Email không được để trống") @Email(message = "Email không đúng định dạng") String email, @NotBlank(message = "PhoneNumber không được để trống") @Digits(message = "PhoneNumber chỉ chứa chữ số", fraction = 0, integer = 15) String phoneNumber, @NotNull(message = "Gender không được để trống") Gender gender, Account account, Address address, FullName fullName, String employeeCode, Role role, double seniority, Position position) {
		super(identityNumber, dateOfBirth, email, phoneNumber, gender, account, address, fullName);
		this.employeeCode = employeeCode;
		this.role = role;
		this.seniority = seniority;
		this.position = position;
	}

	public User(String identityNumber, Date dateOfBirth, String email, String phoneNumber, Gender gender, String username, String password, String city, String district, String country, String houseNumber, String zipCode, String firstName, String lastName, String employeeCode, Role role, double seniority, Position position) {
		super(identityNumber, dateOfBirth, email, phoneNumber, gender, username, password, city, district, country, houseNumber, zipCode, firstName, lastName);
		this.employeeCode = employeeCode;
		this.role = role;
		this.seniority = seniority;
		this.position = position;
	}
}