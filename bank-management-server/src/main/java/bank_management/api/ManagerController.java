package bank_management.api;

import bank_management.dto.EmployeeDto;
import bank_management.entity.Manager;
import bank_management.enumeration.ResponseStatus;
import bank_management.payload.ResponseResult;
import bank_management.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    ManagerService managerService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getManager(@PathVariable(value = "id") String ID) {
        Manager manager = managerService.findManagerById(ID);
        if (manager == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("Không tìm thấy nhân viên có ID là " + ID + "!", ResponseStatus.Error));
        }
        return ResponseEntity
                .ok()
                .body(new ResponseResult(manager, "Lấy thông tin nhân viên có ID là " + ID + " thành công.", ResponseStatus.Success ));
    }
}
