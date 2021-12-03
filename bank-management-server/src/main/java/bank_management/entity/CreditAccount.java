package bank_management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class CreditAccount extends BankAccount{
    @Column(name = "CreditLimit")
    @NotBlank(message = "creditLimit không được để trống!")
    private double creditLimit;

    @Column(name = "DebtAmount")
    @NotBlank(message = "debtAmount không được để trống!")
    private double debtAmount;

    @Column(name = "InterestRate")
    @NotBlank(message = "InterestRate không được để trống!")
    private double interestRate;
}
