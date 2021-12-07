package bank_management.api;

import javax.validation.Valid;

import bank_management.dto.ResponseResult;
import bank_management.entity.Employee;
import bank_management.enumeration.ResponseStatus;
import bank_management.service.EmployeeService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getEmployee(@PathVariable(value = "id") String ID) {
        Employee employee = employeeService.findEmployeeById(ID);
        if (employee == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("Không tìm thấy nhân viên có ID là " + ID + "!", ResponseStatus.Error));
        }
        return ResponseEntity
                .ok()
                .body(new ResponseResult(employee, "Lấy thông tin nhân viên có ID là " + ID + " thành công.", ResponseStatus.Success ));
    }

    @GetMapping
    public ResponseResult getAllEmployee() {
        List<Employee> employees = employeeService.findAllEmployee();
        return new ResponseResult(
                employees,
                "Lấy tất cả nhân viên Thành công",
                ResponseStatus.Success
        );
    }

    @PutMapping
    public ResponseEntity<?> editEmployee(@Valid @RequestBody Employee employee) {
        Employee employeeIsEdited = employeeService.editEmployee(employee);
        if (employeeIsEdited == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("Không thể chỉnh sửa do nhân viên có ID là " + employee.getID() + "không tồn tại!", ResponseStatus.Error));
        }
        else {
            return ResponseEntity
                    .ok()
                    .body(new ResponseResult(
                            employeeIsEdited,
                            "Chỉnh sửa thông tin nhân viên thành công!",
                            ResponseStatus.Success
                            )
                    );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") String ID) {
        boolean isDeleted = employeeService.deleteEmployee(ID);
        if (isDeleted) {
            return ResponseEntity
                    .ok()
                    .body(new ResponseResult("Xoá nhân viên có ID là " + ID + " thành công.", ResponseStatus.Success ));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("Không thể xóa do nhân viên có ID là " + ID + "không tồn tại!", ResponseStatus.Error));
        }
    }

}
