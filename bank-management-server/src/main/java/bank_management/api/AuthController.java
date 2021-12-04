package bank_management.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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