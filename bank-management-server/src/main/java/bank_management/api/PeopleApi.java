package bank_management.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bank_management.entity.Account;
import bank_management.repository.AccountRepository;
import bank_management.repository.PeopleRepository;

@RestController
@RequestMapping(path = "/people", produces = "application/json")
@CrossOrigin(origins = "*")
public class PeopleApi {

	private PeopleRepository peopleRepo;
	private AccountRepository accountRepo;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public PeopleApi(
			PeopleRepository peopleRepo,
			AccountRepository accountRepo,
			BCryptPasswordEncoder bCryptPasswordEncoder
		) {
		
		this.peopleRepo = peopleRepo;
		this.accountRepo = accountRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
//	@GetMapping
//	public Iterable<People> getAllIPeople() {
//		return peopleRepo.findAll();
//	}
	
	@GetMapping
	public Iterable<Account> getAllIPeople() {
		return accountRepo.findAll();
	}
}