 package bank_management.entity;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
@AttributeOverride(name = "ID", column = @Column(name = "PeopleID"))
public class User extends People {
	
	@Column(name = "EmployeeCode")
	@NotBlank(message = "EmployeeCode không được để trống")
	protected String employeeCode;
	
	@Column(name = "Role")
	@Enumerated(EnumType.STRING)
	protected Role role;
	
	@Column(name = "Seniority")
	protected double seniority;
	
	@Column(name = "Position")
	@Enumerated(EnumType.STRING)
	protected Position position;

	public User(String ID, Date createDate, Date editDate,
			@Digits(message = "IdentityNumber chỉ chứa chữ số", fraction = 0, integer = 20) String identityNumber,
			@NotBlank(message = "Name không được để trống") String name, String address, Date dateOfBirth,
			@Email(message = "Email không đúng định dạng") String email, Account account,
			@NotBlank(message = "EmployeeCode không được để trống") String employeeCode, Role role, double seniority,
			Position position) {
		super(ID, createDate, editDate, identityNumber, name, address, dateOfBirth, email, account);
		this.employeeCode = employeeCode;
		this.role = role;
		this.seniority = seniority;
		this.position = position;
	}

	public User(String identityNumber, String name, String address, Date dateOfBirth, String email, Account account,
			@NotBlank(message = "EmployeeCode không được để trống") String employeeCode, Role role, double seniority,
			Position position) {
		super(identityNumber, name, address, dateOfBirth, email, account);
		this.employeeCode = employeeCode;
		this.role = role;
		this.seniority = seniority;
		this.position = position;
	}
}