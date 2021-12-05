package bank_management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "paymentAccount")
public class PaymentAccount extends BankAccount{
    @Column(name = "InterestRate")
    @NotNull(message = "InterestRate không được để trống!")
    private double interesRate;

    @Column(name = "MinBalance")
    @NotNull(message = "minBalance không được để trống!")
    private  double minBalance;

    @Column(name = "Amount")
    @NotNull(message = "amount không được để trống!")
    private double amount;

    public PaymentAccount(@Digits(message = "accountCode chỉ chứa chữ số.", fraction = 0, integer = 30) String accountCode,
                          @Digits(message = "accountNumber chỉ chứa chữ số.", fraction = 0, integer = 30) String accountNumber,
                          @NotNull(message = "expireDate không được trống") Date expireDate,
                          @NotBlank(message = "Branch không được trống!") String branch,
                          @NotBlank(message = "Type không được trống!") String type,
                          @NotBlank(message = "Status không được trống!") boolean status,
                          Employee employee,
                          MemberLevel memberLevel,
                          double interesRate,
                          double minBalance,
                          double amount) {
        super(accountCode, accountNumber, expireDate, branch, type, status, employee, memberLevel);
        this.interesRate = interesRate;
        this.minBalance = minBalance;
        this.amount = amount;
    }

    public PaymentAccount(String ID, Date createDate, Date editDate, String accountCode, String accountNumber, Date expireDate, String branch, String type, boolean status, Employee employee, MemberLevel memberLevel, double interesRate, double minBalance, double amount) {
        super(ID, createDate, editDate, accountCode, accountNumber, expireDate, branch, type, status, employee, memberLevel);
        this.interesRate = interesRate;
        this.minBalance = minBalance;
        this.amount = amount;
    }
}
