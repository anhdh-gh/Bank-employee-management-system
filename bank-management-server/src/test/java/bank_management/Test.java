package bank_management;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import bank_management.entity.Customer;
import bank_management.repository.CustomerRepository;
import bank_management.repository.EmployeeRepository;
import bank_management.repository.ManagerRepository;
import bank_management.repository.PeopleRepository;

@SpringBootTest
public class Test {
    @Autowired
    PeopleRepository peopleRepo;
    
    @Autowired
    CustomerRepository customerRepo;
    
    @Autowired
    ManagerRepository managerRepo;
    
    @Autowired
    EmployeeRepository employeeRepo;
    
    @org.junit.jupiter.api.Test
    void test() {
    	
    	Optional<Customer> optCustomer = customerRepo.findById("064500d1-0866-4391-b170-4b6295fd5bb3");
    	if(optCustomer.isPresent())
    		System.out.println(optCustomer.get());
    }
}