package bank_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "salary")
public class Salary extends BaseEntity{

    @Column(name = "Salary")
    @NotNull(message = "Salary không được để trống!")
    @Positive(message = "Salary phải là số dương")
    private double salary;

    @Column(name = "Month")
    @NotNull(message = "Month không được để trống!")
    @Positive(message = "Month phải là số dương")
    private int month;

    @Column(name = "Year")
    @NotNull(message = "Year không được để trống!")
    @Positive(message = "Year phải là số dương")
    private int year;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EmployeeID")
    private Employee employee;

    public Salary(String ID, Date createDate, Date editDate, double salary, int month, int year, Employee employee) {
        super(ID, createDate, editDate);
        this.salary = salary;
        this.month = month;
        this.year = year;
        this.employee = employee;
    }


}
