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
@Table(name = "paymentAccount")
public class PaymentAccount extends BankAccount {

    @Column(name = "InterestRate")
    @NotNull(message = "InterestRate không được để trống")
    @Positive(message = "InterestRate phải là số dương")
    private double interestRate;

    @Column(name = "MinBalance")
    @NotNull(message = "MinBalance không được để trống")
    @Positive(message = "MinBalance phải là số dương")
    private double minBalance;

    @Column(name = "Amount")
    @NotNull(message = "Amount không được để trống")
    @Positive(message = "Amount phải là số dương")
    private double amount;

    @OneToOne
    @JoinColumn(name = "CustomerID")
    private Customer customer;

    public PaymentAccount(MemberLevel memberLevel, @Size(max = 30, message = "AccountCode tối đa 30 ký tự") @Pattern(regexp = "^\\d+$", message = "AccountCode chỉ chứa chữ số") String accountCode, @Size(max = 30, message = "AccountNumber tối đa 30 ký tự") @Pattern(regexp = "^\\d+$", message = "AccountNumber chỉ chứa chữ số") String accountNumber, @NotNull(message = "expireDate không được trống") Date expireDate, @NotBlank(message = "Branch không được trống!") String branch, @NotBlank(message = "Type không được trống!") BankAccountType type, @NotBlank(message = "Status không được trống!") boolean status, Employee employee, double interestRate, double minBalance, double amount, Customer customer) {
        super(memberLevel, accountCode, accountNumber, expireDate, branch, type, status, employee);
        this.interestRate = interestRate;
        this.minBalance = minBalance;
        this.amount = amount;
        this.customer = customer;
    }

    public PaymentAccount(String ID, Date createDate, Date editDate, MemberLevel memberLevel, String accountCode, String accountNumber, Date expireDate, String branch, BankAccountType type, boolean status, Employee employee, double interestRate, double minBalance, double amount, Customer customer) {
        super(ID, createDate, editDate, memberLevel, accountCode, accountNumber, expireDate, branch, type, status, employee);
        this.interestRate = interestRate;
        this.minBalance = minBalance;
        this.amount = amount;
        this.customer = customer;
    }
}
