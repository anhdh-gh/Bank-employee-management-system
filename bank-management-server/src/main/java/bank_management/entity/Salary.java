package bank_management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Salary extends BaseEntity{
    @ManyToOne(targetEntity = User.class)
    @JoinColumn (name = "UserID")
    private User user;

    @ManyToOne(targetEntity = WorkingMonth.class)
    @JoinColumn (name = "WorkingMonthID")
    private WorkingMonth workingMonth;

    @Column(name = "Salary")
    @NotBlank(message = "Salary không được để trống!")
    private double salary;
}
