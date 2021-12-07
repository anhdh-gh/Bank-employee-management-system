package bank_management.service;

import bank_management.entity.Account;
import bank_management.entity.Person;
import bank_management.repository.AccountRepository;
import bank_management.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @Autowired
    AccountRepository accountRepo;

    @Autowired
    PersonRepository personRepo;

    // Check username đã tồn tại
    public boolean checkUsernameExist(String username) {
        Account account = accountRepo.findByUsername(username);
        return account != null;
    }

    // Check IdentityNumber đã tồn tại
    public boolean checkIdentityNumberExist(String identityNumber) {
        Person person = personRepo.findByIdentityNumber(identityNumber);
        return person != null;
    }

    // Check Email đã tồn tại
    public boolean checkEmailExist(String email) {
        Person person = personRepo.findByEmail(email);
        return person != null;
    }

    // Check PhoneNumber đã tồn tại
    public boolean checkPhoneNumberExist(String phoneNumber) {
        Person person = personRepo.findByPhoneNumber(phoneNumber);
        return person != null;
    }
}
