package bank_management.dto;

import bank_management.entity.Employee;
import bank_management.entity.Salary;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class SalaryDto {
    private String ID;

    @NotNull(message = "Salary không được để trống!")
    @Positive(message = "Salary phải là số dương")
    private double salary;

    @NotNull(message = "Month không được để trống!")
    @Positive(message = "Month phải là số dương")
    private int month;

    @NotNull(message = "Year không được để trống!")
    @Positive(message = "Year phải là số dương")
    private int year;

    private Employee employee;

    public SalaryDto(String ID, double salary, int month, int year) {
        this.ID = ID;
        this.salary = salary;
        this.month = month;
        this.year = year;
    }

    public SalaryDto(Salary salary) {
        this.ID = salary.getID();
        this.salary = salary.getSalary();
        this.month = salary.getMonth();
        this.year = salary.getYear();
    }
}
