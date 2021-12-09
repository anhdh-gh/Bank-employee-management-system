package bank_management.service;

import bank_management.dto.BankAccountDto;
import bank_management.entity.BankAccount;
import bank_management.entity.Employee;
import bank_management.repository.BankAccountRepository;
import bank_management.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {
    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    CustomerRepository customerRepository;

    public List<BankAccountDto> getAllBankAccount() {
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();

        List<BankAccountDto> bankAccountDtoList = new ArrayList<>();

        bankAccountList.forEach(b -> {
            BankAccountDto bankAccountDto = new BankAccountDto(b);

            bankAccountDto.setCustomer(customerRepository.getCustomerByAccount_ID(b.getID()));

            bankAccountDtoList.add(bankAccountDto);
        });

        return bankAccountDtoList;
    }

    public BankAccountDto getById(String id) {
        BankAccount bankAccount = bankAccountRepository.getById(id);

        BankAccountDto bankAccountDto = new BankAccountDto(bankAccount);

        bankAccountDto.setCustomer(customerRepository.getCustomerByAccount_ID(bankAccount.getID()));

        return bankAccountDto;
    }

    public BankAccount insert(BankAccount bankAccount) {
        BankAccount accountWithCode = bankAccountRepository.getBankAccountByAccountNumber(bankAccount.getAccountNumber());

        // Nếu không trùng mã thì save
        if(accountWithCode != null) {
            return null;
        }
        else return  bankAccountRepository.save(bankAccount);
    }

    public BankAccount update(BankAccount bankAccount) {
        BankAccount accountWithCode = bankAccountRepository.getBankAccountByAccountNumber(bankAccount.getAccountNumber());

        if(accountWithCode != null && accountWithCode.getID() == bankAccount.getID()) {
            return null;
        }
        else {
            return  bankAccountRepository.save(bankAccount);
        }
    }

    public boolean delete(String ID) {
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(ID);
        if (optionalBankAccount.isPresent()) {
            bankAccountRepository.delete(optionalBankAccount.get());
            return true;
        }
        return false;
    }
}
