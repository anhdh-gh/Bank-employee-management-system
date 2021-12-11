package bank_management;

import bank_management.entity.Account;
import bank_management.entity.Address;
import bank_management.entity.FullName;
import bank_management.entity.Person;
import bank_management.enumeration.Gender;
import bank_management.enumeration.Position;
import bank_management.repository.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootTest
public class Test {
    @Autowired
    PersonRepository personRepo;
    
    @Autowired
    CustomerRepository customerRepo;
    
    @Autowired
    ManagerRepository managerRepo;
    
    @Autowired
    EmployeeRepository employeeRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountRepository accountRepo;

    @Autowired
    AddressRepository addressRepo;

    @Autowired
    FullNameRepository fullNameRepo;

//    @org.junit.jupiter.api.Test
//    void test() {
////        Account account = new Account("dohunganh", passwordEncoder.encode("dohunganh"))
////        Address address = new Address("ha noi", "ha dong", "vietnam", "123", "123456")
////        FullName fullName = new FullName("do hung", "anh");
//
//        Person person = new Person(
//            "0123456789",
//            new Date(),
//            "dohunganh@gmail.com",
//            "0962507172",
//            Gender.Male,
//            "dohunganh1", passwordEncoder.encode("dohunganh"),
//            "ha noi", "ha dong", "vietnam", "123", "123456",
//            "do hung", "anh"
//        );
//
//        accountRepo.save(person.getAccount());
//        addressRepo.save(person.getAddress());
//        fullNameRepo.save(person.getFullName());
//        personRepo.save(person);
//    }

    @org.junit.jupiter.api.Test
    void randomString() {
        System.out.println(employeeRepo.updateEmployee("0120f65e-37de-476e-adf3-4936311fa94f", 2000000, Position.Cashier));

    }
}