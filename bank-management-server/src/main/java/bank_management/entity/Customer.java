package bank_management.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer extends People {
	
	@NotBlank(message = "CustomerCode không được để trống")
	@Column(name = "CustomerCode")
	private String customerCode;

	public Customer(String identityNumber, String name, String address, Date dateOfBirth, String email, Account account,
			@NotBlank(message = "CustomerCode không được để trống") String customerCode) {
		super(identityNumber, name, address, dateOfBirth, email, account);
		this.customerCode = customerCode;
	}

	public Customer(String ID, Date createDate, Date editDate,
			@Digits(message = "IdentityNumber chỉ chứa chữ số", fraction = 0, integer = 20) String identityNumber,
			@NotBlank(message = "Name không được để trống") String name, String address, Date dateOfBirth,
			@Email(message = "Email không đúng định dạng") String email, Account account,
			@NotBlank(message = "CustomerCode không được để trống") String customerCode) {
		super(ID, createDate, editDate, identityNumber, name, address, dateOfBirth, email, account);
		this.customerCode = customerCode;
	}
}