package bank_management.service;

import bank_management.entity.Customer;
import bank_management.entity.Person;
import bank_management.entity.Transaction;
import bank_management.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    PersonService personService;

    public List<Transaction> getAllTransactionByCustomer() {
        Person currentCustomer =  personService.getAuthPerson();

        return  transactionRepository.getTransactionsByCustomerID(currentCustomer.getID());
    }
    public List<Transaction> getAllTransactionByBankAccountID(String bankAccountID) {
        return transactionRepository.getTransactionsByBankAccountID(bankAccountID);
    }
}
