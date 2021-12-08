package bank_management.api;

import javax.validation.Valid;

import bank_management.dto.PersonDto;
import bank_management.dto.ResponseResult;
import bank_management.entity.Person;
import bank_management.enumeration.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

import bank_management.entity.Account;
import bank_management.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    AuthService authService;
    
    @Autowired
    ObjectMapper json;
    
    @PostMapping("/login")
    public ResponseResult authenticateUser(@Valid @RequestBody Account account) {
		String token = authService.processLogin(account);
		
		return new ResponseResult (
            json
                .createObjectNode()
                .putPOJO("accessToken", token)
                .putPOJO("tokenType", "Bearer")
                .putPOJO("ROLE", authService.getAuthRole()),
            "Đăng nhập thành công",
            ResponseStatus.Success
        );
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<?> processForgotPassword(@Valid @RequestBody PersonDto personDto) {
        Account account = authService.getAccountByUsername(personDto.getUsername());

        // Check account có tồn tại hay không
        if(account == null)
            return ResponseEntity
                .badRequest()
                .body(new ResponseResult("Username không tồn tại", ResponseStatus.Invalid));

        Person person = authService.getPersonByAccount(account);

        // Check Person có tồn tại hay không
        if(person == null)
            return ResponseEntity
                .badRequest()
                .body(new ResponseResult("Person không tồn tại", ResponseStatus.Invalid));

        // Check IdentityNumber có giống không
        if(!person.getIdentityNumber().equals(personDto.getIdentityNumber()))
            return ResponseEntity
                .badRequest()
                .body(new ResponseResult("IdentityNumber sai", ResponseStatus.Invalid));

        // Check Email có giống không
        if(!person.getEmail().equals(personDto.getEmail()))
            return ResponseEntity
                .badRequest()
                .body(new ResponseResult("Email sai", ResponseStatus.Invalid));

        // Check PhoneNumber có giống không
        if(!person.getPhoneNumber().equals(personDto.getPhoneNumber()))
            return ResponseEntity
                .badRequest()
                .body(new ResponseResult("PhoneNumber sai", ResponseStatus.Invalid));

        // Nếu check thành công thì reset lại mật khẩu
        String newPassword = authService.resetPassword(person);

        return ResponseEntity
            .ok(new ResponseResult(
                newPassword,
                "Lấy lại mật khẩi thành công",
                ResponseStatus.Success)
            );
    }
}