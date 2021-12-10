package bank_management.payload;

import lombok.Data;

import java.util.List;

@Data
public class DetailSalary {
    private String employeeCode;
    private int month;
    private int year;
    private double baseSalary;
    private List<BankAccountAndCommission> list;

    public DetailSalary(String employeeCode, int month, int year, double baseSalary, List<BankAccountAndCommission> list) {
        this.baseSalary = baseSalary;
        this.list = list;
        this.employeeCode = employeeCode;
        this.month = month;
        this.year = year;
    }
}
