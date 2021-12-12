package bank_management.service;

import bank_management.dto.EmployeeDto;
import bank_management.entity.*;
import bank_management.enumeration.Position;
import bank_management.payload.SearchEmployeeRequest;
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
        Employee employee = new Employee(employeeDto);
        return new EmployeeDto(employeeRepository.save(employee));
    }

    public boolean deleteEmployee(String employeeID) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeID);
        if (optionalEmployee.isPresent()) {
            int row = employeeRepository.deleteEmployee(optionalEmployee.get().getID());
            return row > 0;
        }
        return false;
    }

    private String generateEmployeeCode() {
        String latestEmployeeCode = userRepository.getLatestEmployeeCode();
        int number = Integer.parseInt(latestEmployeeCode.substring(3));
        return "NV-" + (number + 1);
    }

    public EmployeeDto getEmployeeById(String employeeID) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeID);
        if (optionalEmployee.isPresent()) {
            return new EmployeeDto(optionalEmployee.get());
        } else return null;
    }

    public List<EmployeeDto> searchEmployee(SearchEmployeeRequest searchEmployeeRequest) {
        List<Employee> res = employeeRepository.findAll();
        String employeeCode = searchEmployeeRequest.getEmployeeCode();
        String employeeName = searchEmployeeRequest.getEmployeeName();
        String position = searchEmployeeRequest.getPosition();

        if(!position.equals("All"))
            res.removeIf(b -> !b.getPosition().name().contains(position));

        if(employeeCode != null)
            res.removeIf(b -> !b.getEmployeeCode().contains(employeeCode));

        if(employeeName != null)
            res.removeIf(b -> {
                Optional<Employee> optionalEmployee = employeeRepository.findById(b.getID());
                return
                        (optionalEmployee.isPresent() && !optionalEmployee.get().getFullName().toString().contains(employeeName));

            });
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        for (Employee employee : res) {
            employeeDtoList.add(new EmployeeDto(employee));
        }
        return employeeDtoList;
    }
}
