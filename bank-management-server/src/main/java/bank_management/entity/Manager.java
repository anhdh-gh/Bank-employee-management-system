package bank_management.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Positive;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
// @NoArgsConstructor
// @AllArgsConstructor
@Entity
public class Manager extends User {
	
	@Column(name = "Rate")
	@Positive (message = "Rate phải là số dương")
	private double rate;
}