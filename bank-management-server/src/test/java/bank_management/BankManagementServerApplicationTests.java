package bank_management;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import bank_management.entity.Account;
import bank_management.entity.People;
import bank_management.repository.AccountRepository;
import bank_management.repository.PeopleRepository;

@SpringBootTest
class BankManagementServerApplicationTests {
	
	private AccountRepository accountRepo;
	
	private PeopleRepository peopleRepo;
	
	@Autowired
	public BankManagementServerApplicationTests(AccountRepository accountRepo, PeopleRepository peopleRepo) {
		super();
		this.accountRepo = accountRepo;
		this.peopleRepo = peopleRepo;
	}

	@Test
	void testRepository() {
		People people = new People(
			"1234567890",
			"Do Hung Anh",
			"abc",
			new Date(),
			"a@gmail.com"
		);
		
		Account account = new Account("do hung anh", "do hung anh", people);
		
		People people2 = peopleRepo.save(people);
		
		System.out.print(people2);
		
		Account account2 = accountRepo.save(account);
		
		System.out.print(account2);
		
		
//		Optional<Account> account2 = accountRepo.findById("b69dde1f-06a1-412c-828e-cf3e6b8006c0");
//		if(account2.isPresent()) {
//			System.out.print(account2);
//			Account a = account2.get();
//			a.setPassword("abcdefghijk");
//			accountRepo.save(a);
//		}
	}
}
