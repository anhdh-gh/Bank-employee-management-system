package bank_management.entity;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.*;

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
@Table(name = "person")
@AttributeOverride(name = "ID", column = @Column(name = "PersonID"))
public class Person extends BaseEntity {
	
	@Column(name = "IdentityNumber")
	@NotBlank(message = "IdentityNumber không được để trống")
	@Pattern(regexp="^\\d+$", message = "IdentityNumber chỉ chứa chữ số")
	@Size(min = 0, max = 15, message = "IdentityNumber tối đa 15 ký tự số")
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
	@Size(min = 0, max = 15, message = "PhoneNumber tối đa 15 ký tự số")
	@Pattern(regexp="^\\d+$", message = "PhoneNumber chỉ chứa chữ số")
	protected String phoneNumber;

	@Column(name = "Gender")
	@NotNull(message = "Gender không được để trống")
	protected Gender gender;
	
	@OneToOne(targetEntity = Account.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "AccountID")
	protected Account account;

	@OneToOne(targetEntity = Address.class)
	@JoinColumn(name = "AddressID")
	protected Address address;

	@OneToOne(targetEntity = FullName.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "FullNameID")
	protected FullName fullName;

	public Person(String identityNumber, Date dateOfBirth, String email, String phoneNumber, Gender gender, String username, String password, String city, String district, String country, String houseNumber, String zipCode, String firstName, String lastName) {
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

// https://hocspringboot.net/2020/10/23/onetomany-va-manytoone-trong-spring-boot/