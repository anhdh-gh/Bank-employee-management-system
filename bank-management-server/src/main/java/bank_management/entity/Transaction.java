package bank_management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Transaction extends BaseEntity{
    @ManyToOne(targetEntity = BankAccount.class)
    @JoinColumn(name = "BankAccountSendID")
    private BankAccount bankAccountSent;

    @ManyToOne(targetEntity = BankAccount.class)
    @JoinColumn(name = "BankAccountReceiveID")
    private BankAccount bankAccountReceive;

    @Column (name = "Amount")
    @NotBlank(message = "Amount không được để trống!")
    private double amount;

    @Column (name = "ExecuteDate")
    @NotBlank(message = "ExecuteDate không được để trống!")
    private Date executeDate;

    @Column(name = "Content")
    private String content;
}
