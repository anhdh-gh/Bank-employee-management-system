package bank_management.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account extends BaseEntity {

	@NotBlank(message = "Username không được để trống")
	@Size(max = 30, message = "Username tối đa 30 ký tự")
	@Column(name = "Username")
	private String username;
	
	@NotBlank(message = "Password không được để trống")
	@Size(min = 8, message = "Password tối thiểu 8 ký tự")
	@Column(name = "Password")
	private String password;

	public Account(String ID, Date createDate, Date editDate, String username, String password) {
		super(ID, createDate, editDate);
		this.username = username;
		this.password = password;
	}
}