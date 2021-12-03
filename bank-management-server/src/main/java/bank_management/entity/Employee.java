package bank_management.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee extends User {

	@Column(name = "BaseSalary")
	@Positive (message = "BaseSalary phải là số dương")
	private double baseSalary;
}