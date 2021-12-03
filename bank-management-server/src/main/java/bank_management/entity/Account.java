package bank_management.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
	
	@OneToOne(targetEntity = People.class)
	@JoinColumn(name = "PeopleID")
	private People people;
}