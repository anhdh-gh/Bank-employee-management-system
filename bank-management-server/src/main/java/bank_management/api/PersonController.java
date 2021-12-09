package bank_management.api;

import javax.validation.Valid;

import bank_management.payload.ChangePasswordRequest;
import bank_management.payload.ForgotPasswordRequest;
import bank_management.payload.ResponseResult;
import bank_management.entity.Person;
import bank_management.enumeration.ResponseStatus;
import bank_management.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

import bank_management.entity.Account;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {
    
    @Autowired
    PersonService personService;
    
    @Autowired
    ObjectMapper json;

    @GetMapping("/getRole")
    public ResponseEntity<?> getRole() {
        return ResponseEntity
            .ok()
            .body(new ResponseResult(
                json.createObjectNode()
                    .putPOJO("ROLE", personService.getAuthRole()),
                "Lấy role thành công",
                ResponseStatus.Success
        ));
    }

    @PostMapping("/change_password")
    public ResponseEntity<?> processChangePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        if(!personService.comparePassword(changePasswordRequest.getCurrentPassword()))
            return ResponseEntity
                .badRequest()
                .body(new ResponseResult(
                    "Mật khẩu hiện tại không đúng",
                    ResponseStatus.Invalid
                ));

        if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword()))
            return ResponseEntity
                .badRequest()
                .body(new ResponseResult(
                    "Mật khẩu mới và xác nhận mật khẩu mới không khớp",
                    ResponseStatus.Invalid
                ));

        Person currentPerson = personService.getAuthPerson();
        currentPerson.getAccount().setPassword(personService.encode(changePasswordRequest.getNewPassword()));
        personService.editPerson(currentPerson);

        return ResponseEntity.ok(new ResponseResult(
            "Thay đổi mật khẩu thành công",
            ResponseStatus.Success
        ));
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody Account account) {
		String token = personService.processLogin(account);
		
		return ResponseEntity.ok(
            new ResponseResult (
                json
                    .createObjectNode()
                    .putPOJO("accessToken", token)
                    .putPOJO("tokenType", "Bearer")
                    .putPOJO("ROLE", personService.getAuthRole()),
                "Đăng nhập thành công",
                ResponseStatus.Success
            )
        );
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<?> processForgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        Account account = personService.getAccountByUsername(forgotPasswordRequest.getUsername());

        // Check account có tồn tại hay không
        if(account == null)
            return ResponseEntity
                .badRequest()
                .body(new ResponseResult("Username không tồn tại", ResponseStatus.Invalid));

        Person person = personService.getPersonByAccount(account);

        // Check Person có tồn tại hay không
        if(person == null)
            return ResponseEntity
                .badRequest()
                .body(new ResponseResult("Person không tồn tại", ResponseStatus.Invalid));

        // Check IdentityNumber có giống không
        if(!person.getIdentityNumber().equals(forgotPasswordRequest.getIdentityNumber()))
            return ResponseEntity
                .badRequest()
                .body(new ResponseResult("IdentityNumber sai", ResponseStatus.Invalid));

        // Check Email có giống không
        if(!person.getEmail().equals(forgotPasswordRequest.getEmail()))
            return ResponseEntity
                .badRequest()
                .body(new ResponseResult("Email sai", ResponseStatus.Invalid));

        // Check PhoneNumber có giống không
        if(!person.getPhoneNumber().equals(forgotPasswordRequest.getPhoneNumber()))
            return ResponseEntity
                .badRequest()
                .body(new ResponseResult("PhoneNumber sai", ResponseStatus.Invalid));

        // Nếu check thành công thì reset lại mật khẩu
        String newPassword = personService.resetPassword(person);

        return ResponseEntity
            .ok(new ResponseResult(
                newPassword,
                "Lấy lại mật khẩi thành công",
                ResponseStatus.Success)
            );
    }
}