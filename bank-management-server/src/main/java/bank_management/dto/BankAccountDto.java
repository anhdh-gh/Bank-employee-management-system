package bank_management.dto;

import bank_management.entity.BankAccount;
import bank_management.entity.Customer;
import bank_management.entity.Employee;
import bank_management.enumeration.BankAccountType;
import bank_management.enumeration.MemberLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
public class BankAccountDto {
    private String ID;

    @Size(max = 30, message = "AccountCode tối đa 30 ký tự")
    @Pattern(regexp="^\\d+$", message = "AccountCode chỉ chứa chữ số")
    private String accountCode;

    @Size(max = 30, message = "AccountNumber tối đa 30 ký tự")
    @Pattern(regexp="^\\d+$", message = "AccountNumber chỉ chứa chữ số")
    private String accountNumber;

    @NotNull(message = "expireDate không được trống")
    private Date expireDate;

    @NotBlank(message = "Branch không được trống!")
    private String branch;

    @NotBlank(message = "Type không được trống!")
    @Enumerated(EnumType.STRING)
    private BankAccountType type;

    @NotBlank(message = "Status không được trống!")
    private boolean status;

    private Employee employee;

    private MemberLevel memberLevel;


    public BankAccountDto(String ID, String accountCode, String accountNumber, Date expireDate, String branch, BankAccountType type, boolean status, MemberLevel memberLevel) {
        this.ID = ID;
        this.accountCode = accountCode;
        this.accountNumber = accountNumber;
        this.expireDate = expireDate;
        this.branch = branch;
        this.type = type;
        this.status = status;
        this.memberLevel = memberLevel;
    }

    public BankAccountDto(BankAccount bankAccount) {
        this.ID = bankAccount.getID();
        this.accountCode = bankAccount.getAccountCode();
        this.accountNumber = bankAccount.getAccountNumber();
        this.expireDate = bankAccount.getExpireDate();
        this.branch = bankAccount.getBranch();
        this.type = bankAccount.getType();
        this.status = bankAccount.isStatus();
        this.memberLevel = bankAccount.getMemberLevel();
    }

    public BankAccountDto(BankAccount b, Customer customer) {
    }
}
