package bank_management.api;

import bank_management.entity.Employee;
import bank_management.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    ObjectMapper json;

    @PostMapping
    public ObjectNode addEmployee(@Valid @RequestBody Employee employee) {
        return null;
    }

    @GetMapping("/{id}")
    public ObjectNode getEmployee(@PathVariable(value = "id") String ID) {
        Employee employee = employeeService.findEmployeeById(ID);
        return json
                .createObjectNode()
                .putPOJO("employee", employee)
                .putPOJO("status", "Suscess!");
    }

    @GetMapping
    public ObjectNode getAllEmployee() {
        List<Employee> employees = employeeService.findAllEmployee();
        return json
                .createObjectNode()
                .putPOJO("employees", employees);
    }

}
