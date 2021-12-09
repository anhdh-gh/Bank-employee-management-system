package bank_management.entity;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.*;

import bank_management.dto.CustomerDto;
import bank_management.enumeration.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer extends Person {
	
	@NotBlank(message = "CustomerCode không được để trống")
	@Column(name = "CustomerCode")
	@Size(max = 30, message = "customerCode tối đa 30 ký tự")
	private String customerCode;

	@OneToOne(targetEntity = PaymentAccount.class)
	@JoinColumn(name = "PaymentAccountID")
	private PaymentAccount paymentAccount;

	@OneToOne(targetEntity = CreditAccount.class)
	@JoinColumn(name = "CreditAccountID")
	private CreditAccount creditAccount;

	public Customer(@NotBlank(message = "IdentityNumber không được để trống") @Digits(message = "IdentityNumber chỉ chứa chữ số", fraction = 0, integer = 15) String identityNumber, @NotNull(message = "DateOfBirth không được để trống") Date dateOfBirth, @NotBlank(message = "Email không được để trống") @Email(message = "Email không đúng định dạng") String email, @NotBlank(message = "PhoneNumber không được để trống") @Digits(message = "PhoneNumber chỉ chứa chữ số", fraction = 0, integer = 15) String phoneNumber, @NotNull(message = "Gender không được để trống") Gender gender, Account account, Address address, FullName fullName, String customerCode, PaymentAccount paymentAccount, CreditAccount creditAccount) {
		super(identityNumber, dateOfBirth, email, phoneNumber, gender, account, address, fullName);
		this.customerCode = customerCode;
		this.paymentAccount = paymentAccount;
		this.creditAccount = creditAccount;
	}

	public Customer(String identityNumber, Date dateOfBirth, String email, String phoneNumber, Gender gender, String username, String password, String city, String district, String country, String houseNumber, String zipCode, String firstName, String lastName, String customerCode, PaymentAccount paymentAccount, CreditAccount creditAccount) {
		super(identityNumber, dateOfBirth, email, phoneNumber, gender, username, password, city, district, country, houseNumber, zipCode, firstName, lastName);
		this.customerCode = customerCode;
		this.paymentAccount = paymentAccount;
		this.creditAccount = creditAccount;
	}
	public Customer(CustomerDto customerDto){
		super(customerDto.getIdentityNumber(), customerDto.getDateOfBirth(), customerDto.getEmail(), customerDto.getPhoneNumber(),
				customerDto.getGender(), customerDto.getAccount().getUsername(), customerDto.getAccount().getPassword(),
				customerDto.getAddress().getCity(), customerDto.getAddress().getDistrict(), customerDto.getAddress().getCountry(),
				customerDto.getAddress().getHouseNumber(), customerDto.getAddress().getZipCode(), customerDto.getFullName().getFirstName(),
				customerDto.getFullName().getLastName());
		this.customerCode = customerDto.getCustomerCode();
		this.paymentAccount = customerDto.getPaymentAccount();
		this.creditAccount = customerDto.getCreditAccount();
	}
}