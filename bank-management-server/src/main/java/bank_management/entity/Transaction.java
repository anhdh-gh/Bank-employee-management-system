package bank_management.entity;

import bank_management.enumeration.TransactionStatus;
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
@Table( name ="transaction")
public class Transaction extends BaseEntity{
    @ManyToOne(targetEntity = BankAccount.class)
    @JoinColumn(name = "BankAccountSendID")
    private BankAccount bankAccountSent;

    @ManyToOne(targetEntity = BankAccount.class)
    @JoinColumn(name = "BankAccountReceiveID")
    private BankAccount bankAccountReceive;

    @Column (name = "TransactionCode" ,unique = true)
    @NotBlank(message = "TransactionCode không được trông!")
    @Size(max = 30, message = "TransactionCode tối đa 30 ký tự")
    @Pattern(regexp="^\\d+$", message = "TransactionCode chỉ chứa chữ số")
    private String transactionCode;

    @Column (name = "Amount")
    @NotNull(message = "Amount không được để trống!")
    @Positive(message = "Amount phải là số dương")
    private double amount;

    @Column (name = "ExecuteDate")
    @NotNull(message = "ExecuteDate không được để trống!")
    private Date executeDate;

    @Column(name = "Content")
    @NotBlank(message = "Content không được để trống!")
    private String content;

    @Column (name = "Status")
    @NotBlank(message = "Status không được trống")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    public Transaction(String ID, Date createDate, Date editDate, BankAccount bankAccountSent, BankAccount bankAccountReceive, String transactionCode, double amount, Date executeDate, String content, TransactionStatus status) {
        super(ID, createDate, editDate);
        this.bankAccountSent = bankAccountSent;
        this.bankAccountReceive = bankAccountReceive;
        this.transactionCode = transactionCode;
        this.amount = amount;
        this.executeDate = executeDate;
        this.content = content;
        this.status = status;
    }
}
