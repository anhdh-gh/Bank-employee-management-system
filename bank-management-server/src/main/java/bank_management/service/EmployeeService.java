package bank_management.service;

import bank_management.entity.Employee;
import bank_management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee findEmployeeById(String ID) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(ID);
        if (optionalEmployee.isPresent()) {
            return optionalEmployee.get();
        }
        return null;
    }

    public List<Employee> findAllEmployee() {
        return employeeRepository.findAll();
    }

    public Employee editEmployee(Employee employee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getID());
        if (optionalEmployee.isPresent()) {
            return employeeRepository.save(employee);
        }
        return null;
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
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
