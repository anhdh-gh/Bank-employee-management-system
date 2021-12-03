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
public class PaymentAccount extends BankAccount{
    @Column(name = "InterestRate")
    @NotBlank(message = "InterestRate không được để trống!")
    private double interesRate;

    @Column(name = "MinBalance")
    @NotBlank(message = "minBalance không được để trống!")
    private  double minBalance;

    @Column(name = "Amount")
    @NotBlank(message = "amount không được để trống!")
    private double amount;

}
