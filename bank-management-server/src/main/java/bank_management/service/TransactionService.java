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

    /**
     * Lấy tất cả transaction của customer đang đăng nhập
     * NVTOAN
     * @return
     */
    public List<Transaction> getAllTransactionByCustomer() {
        Person currentCustomer =  personService.getAuthPerson();

        return  transactionRepository.getTransactionsByCustomerID(currentCustomer.getID());
    }

    /**
     * Lấy tất cả transaction theo bankAccountID
     * NVTOAN
     * @return
     */
    public List<Transaction> getAllTransactionByBankAccountID(String bankAccountID) {
        return transactionRepository.getTransactionsByBankAccountID(bankAccountID);
    }

    /**
     * Tạo ra transaction code mới nhất
     * NVTOAN
     * @return
     */
    public String getNewTransactionCode() {
        String maxCode = transactionRepository.getMaxTransactionCode();
        String newCode = maxCode;

        if(maxCode == null) {
            return "GD-0000000001";
        }
        else {
            String[] part = maxCode.split("(?<=\\D)(?=\\d)");
            newCode = part[0] + (Integer.parseInt(part[1]) + 1);
        }

        return newCode;
    }
}
