package bank_management.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bank_management.entity.Account;
import bank_management.repository.AccountRepository;

@RestController
@RequestMapping(path = "/test", produces = "application/json")
@CrossOrigin(origins = "*")
public class TestApi {

	private AccountRepository accountRepo;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public TestApi(
			AccountRepository accountRepo,
			BCryptPasswordEncoder bCryptPasswordEncoder
		) {
		
		this.accountRepo = accountRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@GetMapping
	public Iterable<Account> getAllIAccount() {
		return accountRepo.findAll();
	}
}