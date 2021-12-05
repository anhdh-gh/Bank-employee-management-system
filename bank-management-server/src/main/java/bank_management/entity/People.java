package bank_management.entity;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import bank_management.enumeration.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Inheritance(strategy = InheritanceType.JOINED) // Mỗi class là một bảng, yêu cầu join bảng. https://techmaster.vn/posts/36499/hibernate-inheritance-mapping
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "people")
@AttributeOverride(name = "ID", column = @Column(name = "PeopleID"))
public class People extends BaseEntity {
	
	@Column(name = "IdentityNumber")
	@NotBlank(message = "IdentityNumber không được để trống")
	@Digits(message = "IdentityNumber chỉ chứa chữ số", fraction = 0, integer = 15)
	protected String identityNumber; // CMND

	@NotNull(message = "DateOfBirth không được để trống")
	@Column(name = "DateOfBirth")
	protected Date dateOfBirth;

	@Column(name = "Email")
	@NotBlank(message = "Email không được để trống")
	@Email (message = "Email không đúng định dạng")
	protected String email;

	@Column(name = "PhoneNumber")
	@NotBlank(message = "PhoneNumber không được để trống")
	@Digits(message = "PhoneNumber chỉ chứa chữ số", fraction = 0, integer = 15)
	protected String phoneNumber;

	@Column(name = "Gender")
	@NotNull(message = "Gender không được để trống")
	protected Gender gender;
	
	@OneToOne(targetEntity = Account.class)
	@JoinColumn(name = "AccountID")
	protected Account account;

	@OneToOne(targetEntity = Address.class)
	@JoinColumn(name = "AddressID")
	protected Address address;

	@OneToOne(targetEntity = FullName.class)
	@JoinColumn(name = "FullNameID")
	protected FullName fullName;

	public People(String identityNumber, Date dateOfBirth, String email, String phoneNumber, Gender gender, String username, String password, String city, String district, String country, String houseNumber, String zipCode, String firstName, String lastName) {
		this.identityNumber = identityNumber;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.account = new Account(username, password);
		this.address = new Address(city, district, country, houseNumber, zipCode);
		this.fullName = new FullName(firstName, lastName);
	}
}