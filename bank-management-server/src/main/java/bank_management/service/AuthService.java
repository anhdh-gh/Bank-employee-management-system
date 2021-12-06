package bank_management.service;

import java.util.ArrayList;
import java.util.List;

import bank_management.repository.AccountRepository;
import bank_management.repository.PersonRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bank_management.common.CustomUserDetails;
import bank_management.common.JwtTokenProvider;
import bank_management.entity.Account;
import bank_management.entity.Person;
import bank_management.enumeration.Role;

@Service
public class AuthService {

    @Autowired
    AccountRepository accountRepo;

    @Autowired
    PersonRepository personRepo;
	
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    // Lấy thông tin của người dùng đã đăng nhập
    public Person getAuthPeople() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
    	return customUserDetails.getPeople();
    }
    
    // Lấy các role (quyền) của người dùng đã đăng nhập
    public List<Role> getAuthRole() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	
    	List<Role> roles = new ArrayList<>();
    	auth.getAuthorities().forEach(item -> roles.add(Role.valueOf(item.getAuthority())));
    	return roles;
    }		
    
    // Thực hiện login
    public String processLogin(Account account) {
        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate (
            new UsernamePasswordAuthenticationToken(
        		account.getUsername(),
        		account.getPassword()
            )
        );       		
        
        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt token
        String jwtToken = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        
        return jwtToken;
    }

    // Get Account bu username
    public Account getAccountByUsername(String username) {
        Account account = accountRepo.findByUsername(username);
        return account;
    }

    // Get Person by Account
    public Person getPersonByAccount(Account account) {
        Person person = personRepo.findByAccount(account);
        return person;
    }

    // Reset password
    public String resetPassword(Person person) {
        // Tạo ra 1 chuỗi bất kỳ có 8 ký tự
        String newPassword = RandomStringUtils.randomAlphanumeric(8);

        // Mã hóa mật khẩu mới
        person.getAccount().setPassword(passwordEncoder.encode(newPassword));

        // Lưu vào database
        personRepo.save(person);

        // Trả về mật khẩu mới
        return newPassword;
    }
}

// https://stackoverflow.com/questions/31159075/how-to-find-out-the-currently-logged-in-user-in-spring-boot