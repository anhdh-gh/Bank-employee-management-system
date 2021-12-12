package bank_management.entity;

import bank_management.dto.BankAccountDto;
import bank_management.enumeration.BankAccountType;
import bank_management.enumeration.MemberLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "bankaccount")
@AttributeOverride(name = "ID", column = @Column(name = "BankAccountID"))
public class BankAccount extends BaseEntity {

    @Column (name = "MemberLevel")
    @NotNull(message = "MemberLevel không được trống!")
    @Enumerated(EnumType.STRING)
    protected MemberLevel memberLevel;

    @Column(name = "AccountCode", unique = true)
    @Size(max = 30, message = "AccountCode tối đa 30 ký tự")
    @Pattern(regexp="^\\d+$", message = "AccountCode chỉ chứa chữ số")
    protected String accountCode;

    @Column(name = "AccountNumber", unique = true)
    @Size(max = 30, message = "AccountNumber tối đa 30 ký tự")
    @Pattern(regexp="^\\d+$", message = "AccountNumber chỉ chứa chữ số")
    protected String accountNumber;

    @Column(name = "ExpireDate")
    @NotNull(message = "ExpireDate không được trống")
    protected Date expireDate; // hạn tài khoản

    @Column(name = "Branch")
    @NotBlank(message = "Branch không được trống!")
    protected String branch;

    @Column (name = "Type")
    @NotNull(message = "Type không được trống!")
    @Enumerated(EnumType.STRING)
    protected BankAccountType type;

    @Column (name = "Status")
    @NotNull(message = "Status không được trống!") // true: đã được cộng hoa hồng cho nhân viên, false: ngược lại
    protected boolean status;

    @ManyToOne
    @JoinColumn (name = "EmployeeID")
    protected Employee employee;

    @NotBlank(message = "CVV không được để trống")
    @Size(max = 10, message = "CVV tối đa 10 ký tự")
    @Column(name = "CVV")
    protected String CVV;

    public BankAccount(String ID, Date createDate, Date editDate, MemberLevel memberLevel, String accountCode, String accountNumber, Date expireDate, String branch, BankAccountType type, boolean status, Employee employee, String CVV) {
        super(ID, createDate, editDate);
        this.memberLevel = memberLevel;
        this.accountCode = accountCode;
        this.accountNumber = accountNumber;
        this.expireDate = expireDate;
        this.branch = branch;
        this.type = type;
        this.status = status;
        this.employee = employee;
        this.CVV = CVV;
    }

    public BankAccount(BankAccountDto bankAccountDto) {
        this.memberLevel = bankAccountDto.getMemberLevel();
        this.accountCode = bankAccountDto.getAccountCode();
        this.accountNumber = bankAccountDto.getAccountNumber();
        this.expireDate = bankAccountDto.getExpireDate();
        this.branch = bankAccountDto.getBranch();
        this.type = bankAccountDto.getType();
        this.status = bankAccountDto.isStatus();
        this.employee = bankAccountDto.getEmployee();
        this.ID = bankAccountDto.getID();
    }
}