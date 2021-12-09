package bank_management.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import bank_management.common.CustomUserDetails;
import bank_management.entity.Account;
import bank_management.entity.Person;
import bank_management.repository.AccountRepository;
import bank_management.repository.CustomerRepository;
import bank_management.repository.EmployeeRepository;
import bank_management.repository.ManagerRepository;
import bank_management.repository.PersonRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
    PersonRepository peopleRepo;
	
	@Autowired
	AccountRepository accountRepo;
  
	@Autowired
	CustomerRepository customerRepo;
  
	@Autowired
	ManagerRepository managerRepo;
  
	@Autowired
  	EmployeeRepository employeeRepo;
	
    @Override
    public UserDetails loadUserByUsername(String username) {
        // Kiểm tra xem People có tồn tại trong database không?

		Account account = accountRepo.findByUsername(username);

    	if(account == null)
    		throw new UsernameNotFoundException(username);
    	
    	Person people = peopleRepo.findByAccount(account);
    	if(people == null)
    		throw new UsernameNotFoundException(username);

        return new CustomUserDetails(people, peopleRepo, customerRepo, managerRepo, employeeRepo);
    }
    
    // JWTAuthenticationFilter sẽ sử dụng hàm này
    @Transactional
    public UserDetails loadUserById(String id) {
        Person people = peopleRepo.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return new CustomUserDetails(people, peopleRepo, customerRepo, managerRepo, employeeRepo);
    }
}