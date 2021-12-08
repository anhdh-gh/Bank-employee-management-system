package bank_management.api;

import javax.validation.Valid;

import bank_management.dto.ResponseResult;
import bank_management.entity.Employee;
import bank_management.enumeration.ResponseStatus;
import bank_management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<?> addEmployee(@Valid @RequestBody Employee employee) {
        if (employeeService.checkUsernameExist(employee.getAccount().getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("Username đã tồn tại!", ResponseStatus.Error));
        }
        if (employeeService.checkIdentityNumberExist(employee.getIdentityNumber())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("IdentityNumber đã tồn tại!", ResponseStatus.Error));
        }
        if (employeeService.checkEmailExist(employee.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("Email đã tồn tại!", ResponseStatus.Error));
        }
        if (employeeService.checkPhoneNumberExist(employee.getPhoneNumber())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("Số điện thoại đã tồn tại!", ResponseStatus.Error));
        }
        Employee employeeSaved = employeeService.addEmployee(employee);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseResult(employeeSaved, "Tạo tài khoản nhân viên thành công.", ResponseStatus.Success ));

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
                "Lấy tất cả nhân viên thành công",
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
