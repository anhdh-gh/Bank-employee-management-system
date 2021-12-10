package bank_management.entity;

import bank_management.enumeration.BankAccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @OneToOne
    @JoinColumn(name = "CustomerID")
    private Customer customer;

    public CreditAccount(MemberLevel memberLevel, @Size(max = 30, message = "AccountCode tối đa 30 ký tự") @Pattern(regexp = "^\\d+$", message = "AccountCode chỉ chứa chữ số") String accountCode, @Size(max = 30, message = "AccountNumber tối đa 30 ký tự") @Pattern(regexp = "^\\d+$", message = "AccountNumber chỉ chứa chữ số") String accountNumber, @NotNull(message = "expireDate không được trống") Date expireDate, @NotBlank(message = "Branch không được trống!") String branch, @NotBlank(message = "Type không được trống!") BankAccountType type, @NotBlank(message = "Status không được trống!") boolean status, Employee employee, double creditLimit, double debtAmount, double interestRate, String CVV, Customer customer) {
        super(memberLevel, accountCode, accountNumber, expireDate, branch, type, status, employee);
        this.creditLimit = creditLimit;
        this.debtAmount = debtAmount;
        this.interestRate = interestRate;
        this.CVV = CVV;
        this.customer = customer;
    }

    public CreditAccount(String ID, Date createDate, Date editDate, MemberLevel memberLevel, String accountCode, String accountNumber, Date expireDate, String branch, BankAccountType type, boolean status, Employee employee, double creditLimit, double debtAmount, double interestRate, String CVV, Customer customer) {
        super(ID, createDate, editDate, memberLevel, accountCode, accountNumber, expireDate, branch, type, status, employee);
        this.creditLimit = creditLimit;
        this.debtAmount = debtAmount;
        this.interestRate = interestRate;
        this.CVV = CVV;
        this.customer = customer;
    }
}