package bank_management.entity;

import bank_management.enumeration.BankAccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "creditAccount")
public class CreditAccount extends BankAccount{
    @Column(name = "CreditLimit")
    @NotNull(message = "CreditLimit không được để trống!")
    @Positive(message = "CreditLimit phải là số dương")
    private double creditLimit;

    @Column(name = "DebtAmount")
    @NotNull(message = "DebtAmount không được để trống!")
    @Positive(message = "DebtAmount phải là số dương")
    private double debtAmount;

    @Column(name = "InterestRate")
    @NotNull(message = "InterestRate không được để trống!")
    @Positive(message = "InterestRate phải là số dương")
    private double interestRate;

    @NotBlank(message = "CVV không được để trống")
    @Size(max = 10, message = "CVV tối đa 10 ký tự")
    @Column(name = "CVV")
    private String CVV;

    public CreditAccount(@Digits(message = "accountCode chỉ chứa chữ số.", fraction = 0, integer = 30) String accountCode, @Digits(message = "accountNumber chỉ chứa chữ số.", fraction = 0, integer = 30) String accountNumber, @NotNull(message = "expireDate không được trống") Date expireDate, @NotBlank(message = "Branch không được trống!") String branch, @NotBlank(message = "Type không được trống!") BankAccountType type, @NotBlank(message = "Status không được trống!") boolean status, Employee employee, MemberLevel memberLevel, double creditLimit, double debtAmount, double interestRate, String CVV) {
        super(accountCode, accountNumber, expireDate, branch, type, status, employee, memberLevel);
        this.creditLimit = creditLimit;
        this.debtAmount = debtAmount;
        this.interestRate = interestRate;
        this.CVV = CVV;
    }

    public CreditAccount(String ID, Date createDate, Date editDate, String accountCode, String accountNumber, Date expireDate, String branch, BankAccountType type, boolean status, Employee employee, MemberLevel memberLevel, double creditLimit, double debtAmount, double interestRate, String CVV) {
        super(ID, createDate, editDate, accountCode, accountNumber, expireDate, branch, type, status, employee, memberLevel);
        this.creditLimit = creditLimit;
        this.debtAmount = debtAmount;
        this.interestRate = interestRate;
        this.CVV = CVV;
    }
}
