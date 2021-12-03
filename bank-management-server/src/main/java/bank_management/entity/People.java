package bank_management.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
public class People extends BaseEntity {
	
	@Column(name = "IdentityNumber")
	@Digits(message = "IdentityNumber chỉ chứa chữ số", fraction = 0, integer = 20)
	protected String identityNumber; // CMND
	
	@Column(name = "Name")
	@NotBlank(message = "Name không được để trống")
	protected String name;

	@Column(name = "Address")
	protected String address;
	
	@Column(name = "DateOfBirth")
	protected Date dateOfBirth;
	
	@Column(name = "Email")
	@Email (message = "Email không đúng định dạng")
	protected String email;
}