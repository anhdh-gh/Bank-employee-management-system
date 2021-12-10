package bank_management.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import bank_management.entity.Customer;
import bank_management.entity.Employee;
import bank_management.entity.Manager;
import bank_management.entity.Person;
import bank_management.enumeration.Role;
import bank_management.repository.CustomerRepository;
import bank_management.repository.EmployeeRepository;
import bank_management.repository.ManagerRepository;
import bank_management.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	Person person;

    PersonRepository personRepo;

    CustomerRepository customerRepo;

    ManagerRepository managerRepo;
    
    EmployeeRepository employeeRepo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	// Add ROLE của person
    	List<GrantedAuthority> roles = new ArrayList<>();
    	
    	Optional<Customer> optCustomer = customerRepo.findById(person.getID());
    	if(optCustomer.isPresent())
    		roles.add(new SimpleGrantedAuthority(Role.Customer.name()));

    	Optional<Employee> optEmployee = employeeRepo.findById(person.getID());
    	if(optEmployee.isPresent())
    		roles.add(new SimpleGrantedAuthority(Role.Employee.name()));

    	Optional<Manager> optManager = managerRepo.findById(person.getID());
    	if(optManager.isPresent())
    		roles.add(new SimpleGrantedAuthority(Role.Manager.name()));

        return roles;
    }

    @Override
    public String getPassword() {
        return person.getAccount().getPassword();
    }

    @Override
    public String getUsername() {
        return person.getAccount().getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}