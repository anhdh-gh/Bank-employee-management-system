package bank_management.service;

import bank_management.dto.EmployeeDto;
import bank_management.entity.Employee;
import bank_management.entity.User;
import bank_management.repository.EmployeeRepository;
import bank_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService extends PersonService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    UserRepository userRepository;

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

    public EmployeeDto editEmployee(EmployeeDto employeeDto, String employeeID) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeID);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setSeniority(employeeDto.getSeniority());
            employee.setBaseSalary(employeeDto.getBaseSalary());
            employee.setPosition(employeeDto.getPosition());
            return new EmployeeDto(employeeRepository.save(employee));
        }
        return null;
    }

    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        employeeDto.setEmployeeCode(generateEmployeeCode());
        employeeDto.setBaseSalary(3000000);
        employeeDto.setSeniority(0);
        Employee employee = new Employee(employeeDto);
        return new EmployeeDto(employeeRepository.save(employee));
    }

    public boolean deleteEmployee(String employeeID) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeID);
        if (optionalEmployee.isPresent()) {
            employeeRepository.delete(optionalEmployee.get());
            return true;
        }
        return false;
    }

    private String generateEmployeeCode() {
        List<User> list = userRepository.findAll();
        int latest = 0;
        for (User user : list) {
            String employeeCode = user.getEmployeeCode();
            int number = Integer.parseInt(employeeCode.substring(3));
            latest = (number > latest) ? number : latest;
        }
        return "NV-" + (latest + 1);
    }

    public EmployeeDto getEmployeeById(String employeeID) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeID);
        if (optionalEmployee.isPresent()) {
            return new EmployeeDto(optionalEmployee.get());
        } else return null;
    }
}
