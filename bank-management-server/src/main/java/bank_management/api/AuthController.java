package bank_management.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import bank_management.entity.Account;
import bank_management.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    AuthService authService;
    
    @Autowired
    ObjectMapper json;
    
    @PostMapping("/login")
    public ObjectNode authenticateUser(@Valid @RequestBody Account account) {
		String token = authService.processLogin(account);
		
		return json
				.createObjectNode()
				.putPOJO("accessToken", token)
				.putPOJO("tokenType", "Bearer");
    }
    
    @GetMapping("/test")
    public ObjectNode getInfo() {
		return json
				.createObjectNode()
				.putPOJO("people", authService.getAuthPeople())
				.putPOJO("role", authService.getAuthRole());    	
    }
}