package bank_management.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "bankaccount")
@AttributeOverride(name = "ID", column = @Column(name = "BankAccountID"))
public class BankAccount extends BaseEntity{
    @Column(name = "AccountCode", unique = true)
    @Digits(message = "accountCode chỉ chứa chữ số.", fraction = 0, integer = 30)
    protected String accountCode;

    @Column(name = "AccountNumber", unique = true)
    @Digits(message = "accountNumber chỉ chứa chữ số.", fraction = 0, integer = 30)
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
    protected String type;

    @Column (name = "Status")
    @NotBlank(message = "Status không được trống!")
    protected boolean status;

    @ManyToOne (targetEntity = Employee.class)
    @JoinColumn (name = "EmployeeID")
    protected Employee employee;

    @ManyToOne (targetEntity = MemberLevel.class)
    @JoinColumn (name = "MemberLevelID")
    protected MemberLevel memberLevel;

    public BankAccount(String ID, Date createDate, Date editDate, String accountCode, String accountNumber, Date expireDate, String branch, String type, boolean status, Employee employee, MemberLevel memberLevel) {
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
