package bank_management.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class BankAccount extends BaseEntity{
    @Column(name = "AccountCode")
    @Digits(message = "accountCode chỉ chứa chữ số.", fraction = 0, integer = 16)
    protected String accountCode;

    @Column(name = "AccountNumber")
    @Digits(message = "accountNumber chỉ chứa chữ số.", fraction = 0, integer = 16)
    protected String accountNumber;

    @Column(name = "ExprideDate")
    protected Date expireDate;

    @Column(name = "Branch")
    @NotBlank(message = "Branch không được trống!")
    protected String branch;

    @Column (name = "Type")
    @NotBlank(message = "Type không được trống!")
    protected int type;

    @Column (name = "Status")
    @NotBlank(message = "Status không được trống!")
    protected boolean status;

    @ManyToOne (targetEntity = Employee.class)
    @JoinColumn (name = "EmployeeID")
    protected Employee employee;

    @ManyToOne (targetEntity = MemberLevel.class)
    @JoinColumn (name = "MemberLevelID")
    protected MemberLevel memberLevel;
}
