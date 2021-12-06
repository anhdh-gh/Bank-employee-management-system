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

    public boolean isExistEmail(String email) {
        Employee employee = employeeRepository.findEmployeeByEmail(email);
        return employee != null;
    }

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
}
