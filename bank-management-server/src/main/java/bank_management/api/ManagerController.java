package bank_management.api;

import bank_management.entity.Manager;
import bank_management.entity.Person;
import bank_management.enumeration.ResponseStatus;
import bank_management.payload.ResponseResult;
import bank_management.service.ManagerService;
import bank_management.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    PersonService personService;
    @Autowired
    ManagerService managerService;

    @GetMapping("/profile")
    public ResponseEntity<?> getCurrentEmployee() {
        Person person = personService.getAuthPerson();
        Manager manager = managerService.getManagerByID(person.getID());
        if (manager == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("Không thể lấy thông tin nhân viên!", ResponseStatus.Error));
        }
        else {
            return ResponseEntity
                    .ok()
                    .body(new ResponseResult(manager ,"Lấy thông tin thành công.", ResponseStatus.Success ));
        }
    }
}
