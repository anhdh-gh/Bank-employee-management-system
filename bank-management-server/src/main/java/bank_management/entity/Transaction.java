package bank_management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @Digits(message = "Transaction Code phải là chữ số!", fraction = 0, integer = 30)
    private String transactionCode;

    @Column (name = "Amount")
    @NotBlank(message = "Amount không được để trống!")
    private double amount;

    @Column (name = "ExecuteDate")
    @NotBlank(message = "ExecuteDate không được để trống!")
    private Date executeDate;

    @Column(name = "Content")
    @NotBlank(message = "Content không được để trống!")
    private String content;

    @Column (name = "Status")
    @NotBlank(message = "Status không được trống")
    private String status;
}
