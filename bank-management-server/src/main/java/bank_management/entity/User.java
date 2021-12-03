 package bank_management.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
}