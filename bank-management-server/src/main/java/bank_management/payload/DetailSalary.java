package bank_management.payload;

import bank_management.dto.EmployeeDto;
import bank_management.dto.SalaryDto;
import bank_management.entity.Employee;
import bank_management.entity.Salary;
import lombok.Data;

import java.util.List;

@Data
public class DetailSalary {
    private EmployeeDto employeeDto;
    private SalaryDto salaryDto;
    private List<BankAccountAndCommission> list;

    public DetailSalary(EmployeeDto employeeDto, SalaryDto salaryDto, List<BankAccountAndCommission> list) {
        this.list = list;
        this.employeeDto = employeeDto;
        this.salaryDto = salaryDto;
    }
}
