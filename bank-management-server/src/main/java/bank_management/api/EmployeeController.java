package bank_management.api;

import javax.validation.Valid;

import bank_management.dto.EmployeeDto;
import bank_management.entity.Manager;
import bank_management.entity.Person;
import bank_management.payload.ResponseResult;
import bank_management.enumeration.ResponseStatus;
import bank_management.payload.SearchEmployeeRequest;
import bank_management.service.EmployeeService;
import bank_management.service.PersonService;
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

    @Autowired
    PersonService personService;

    @PostMapping
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        if (employeeService.checkUsernameExist(employeeDto.getAccount().getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("Username đã tồn tại!", ResponseStatus.Error));
        }
        if (employeeService.checkIdentityNumberExist(employeeDto.getIdentityNumber())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("IdentityNumber đã tồn tại!", ResponseStatus.Error));
        }
        if (employeeService.checkEmailExist(employeeDto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("Email đã tồn tại!", ResponseStatus.Error));
        }
        if (employeeService.checkPhoneNumberExist(employeeDto.getPhoneNumber())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("Số điện thoại đã tồn tại!", ResponseStatus.Error));
        }
        employeeDto.setManager((Manager) personService.getAuthPerson());
        EmployeeDto employeeDtoSaved = employeeService.addEmployee(employeeDto);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseResult(employeeDtoSaved, "Tạo tài khoản nhân viên thành công.", ResponseStatus.Success ));

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable(value = "id") String ID) {
        EmployeeDto employee = employeeService.findEmployeeById(ID);
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
        List<EmployeeDto> employees = employeeService.findAllEmployee();
        return new ResponseResult(
                employees,
                "Lấy tất cả nhân viên thành công",
                ResponseStatus.Success
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable(value = "id") String employeeID) {
        EmployeeDto employeeIsEdited = employeeService.editEmployee(employeeDto, employeeID);
        if (employeeIsEdited == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("Có lỗi xảy ra khi sửa đổi thông tin nhân viên!", ResponseStatus.Error));
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
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") String employeeID) {
        boolean isDeleted = employeeService.deleteEmployee(employeeID);
        if (isDeleted) {
            return ResponseEntity
                    .ok()
                    .body(new ResponseResult("Xoá nhân viên có ID là " + employeeID + " thành công.", ResponseStatus.Success ));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("Không thể xóa do nhân viên có ID là " + employeeID + "không tồn tại!", ResponseStatus.Error));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchEmployee(SearchEmployeeRequest searchEmployeeRequest) {
        List<EmployeeDto> employeeDtoList = employeeService.searchEmployee(searchEmployeeRequest);
        return ResponseEntity
                .ok()
                .body(new ResponseResult(employeeDtoList ,"Lấy thông tin thành công.", ResponseStatus.Success ));
    }

}
