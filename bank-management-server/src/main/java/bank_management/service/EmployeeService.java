package bank_management.service;

import bank_management.dto.EmployeeDto;
import bank_management.entity.Employee;
import bank_management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService extends PersonService{
    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeDto findEmployeeById(String ID) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(ID);
        if (optionalEmployee.isPresent()) {
            return new EmployeeDto(optionalEmployee.get());
        }
        return null;
    }

    public List<EmployeeDto> findAllEmployee() {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        List<Employee> employeeList = employeeRepository.findAll();
        for (Employee employee : employeeList) {
            employeeDtoList.add(new EmployeeDto(employee));
        }
        return employeeDtoList;
    }

    public Employee editEmployee(Employee employee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getID());
        if (optionalEmployee.isPresent()) {
            return employeeRepository.save(employee);
        }
        return null;
    }

    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        employeeDto.setEmployeeCode("NV21");
        employeeDto.setBaseSalary(3300000);
        employeeDto.setSeniority(0);
        Employee employee = new Employee(employeeDto);
        return new EmployeeDto(employeeRepository.save(employee));
    }

    public boolean deleteEmployee(String ID) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(ID);
        if (optionalEmployee.isPresent()) {
            employeeRepository.delete(optionalEmployee.get());
            return true;
        }
        return false;
    }

}
