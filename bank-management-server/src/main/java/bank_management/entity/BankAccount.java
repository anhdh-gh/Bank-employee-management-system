package bank_management.entity;

import bank_management.enumeration.BankAccountType;
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
public class BankAccount extends BaseEntity{
    @Column(name = "AccountCode", unique = true)
    @Size(max = 30, message = "AccountCode tối đa 30 ký tự")
    @Pattern(regexp="^\\d+$", message = "AccountCode chỉ chứa chữ số")
    protected String accountCode;

    @Column(name = "AccountNumber", unique = true)
    @Size(max = 30, message = "AccountNumber tối đa 30 ký tự")
    @Pattern(regexp="^\\d+$", message = "AccountNumber chỉ chứa chữ số")
    protected String accountNumber;

    @Column(name = "ExprideDate")
    @NotNull(message = "expireDate không được trống")
    protected Date expireDate;

    @Column(name = "Branch")
    @NotBlank(message = "Branch không được trống!")
    protected String branch;

    @Column (name = "Type")
    @NotBlank(message = "Type không được trống!")
    @Enumerated(EnumType.STRING)
    protected BankAccountType type;

    @Column (name = "Status")
    @NotBlank(message = "Status không được trống!")
    protected boolean status;

    @ManyToOne (targetEntity = Employee.class)
    @JoinColumn (name = "EmployeeID")
    protected Employee employee;

    @ManyToOne (targetEntity = MemberLevel.class)
    @JoinColumn (name = "MemberLevelID")
    protected MemberLevel memberLevel;

    public BankAccount(String ID, Date createDate, Date editDate, String accountCode, String accountNumber, Date expireDate, String branch, BankAccountType type, boolean status, Employee employee, MemberLevel memberLevel) {
        super(ID, createDate, editDate);
        this.accountCode = accountCode;
        this.accountNumber = accountNumber;
        this.expireDate = expireDate;
        this.branch = branch;
        this.type = type;
        this.status = status;
        this.employee = employee;
        this.memberLevel = memberLevel;
    }
}
