package bank_management.service;

import bank_management.dto.BankAccountDto;
import bank_management.entity.BankAccount;
import bank_management.entity.Customer;
import bank_management.entity.Employee;
import bank_management.enumeration.BankAccountType;
import bank_management.repository.BankAccountRepository;
import bank_management.repository.BankAccountRepository;
import bank_management.repository.CreditAccountRepository;
import bank_management.repository.CustomerRepository;
import bank_management.repository.PaymentAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {
    @Autowired
    BankAccountRepository bankAccountRepo;

    @Autowired
    CustomerRepository customerRepo;
    
    @Autowired
    PaymentAccountRepository paymentAccountRepo;
    
    @Autowired
    CreditAccountRepository creditAccountRepo;

    public Customer getCustomerByBankAccountID(String id) {
        Customer customer = customerRepo.findFirstCustomerByPaymentAccountID(id);
        if(customer == null)
            customer = customerRepo.findFirstCustomerByCreditAccountID(id);
        return customer;
    }

    public List<BankAccountDto> getAllBankAccount() {
        List<BankAccount> bankAccountList = bankAccountRepo.findAll();
        List<BankAccountDto> bankAccountDtoList = new ArrayList<>();

        bankAccountList.forEach(b -> {
            Customer customer = this.getCustomerByBankAccountID(b.getID());
            bankAccountDtoList.add(new BankAccountDto(b, customer));
        });

        return bankAccountDtoList;
    }

    public BankAccountDto getById(String id) {
        Optional<BankAccount> optionalBankAccount = bankAccountRepo.findById(id);
        if(optionalBankAccount.isPresent()) {
            BankAccount bankAccount = optionalBankAccount.get();
            Customer customer = this.getCustomerByBankAccountID(bankAccount.getID());
            return new BankAccountDto(bankAccount, customer);
        }
        return null;
    }

    public BankAccount insert(BankAccount bankAccount) {
        BankAccount accountWithNumber = bankAccountRepo.getBankAccountByAccountNumber(bankAccount.getAccountNumber());
        if(accountWithNumber != null) return null;

        BankAccount accountWithCode = bankAccountRepo.getBankAccountByAccountCode(bankAccount.getAccountCode());
        if(accountWithCode  != null) return null;

        return bankAccountRepo.save(bankAccount);
    }

    public boolean delete(String ID) {
        Optional<BankAccount> optionalBankAccount = bankAccountRepo.findById(ID);
        if (optionalBankAccount.isPresent()) {
            bankAccountRepo.delete(optionalBankAccount.get());
            return true;
        }
        return false;
    }

    public List<BankAccountDto> processSearch(String accountCode, String customerCode, String type) {
        List<BankAccount> bankAccounts = bankAccountRepo.findAll();
        List<BankAccountDto> bankAccountDtos = new ArrayList<>();

        bankAccounts.forEach(b -> {
            Customer customer = this.getCustomerByBankAccountID(b.getID());
            if(customer == null) System.out.println(b.getID());
            if(
                (accountCode != null && b.getAccountCode().contains(accountCode)) ||
                (customerCode != null && customer != null && customer.getCustomerCode().contains(customerCode)) ||
                (type != null && Arrays.stream(BankAccountType.values()).anyMatch(bat -> bat.name().contains(type)))
            )
            bankAccountDtos.add(new BankAccountDto(b, customer));
        });

        return bankAccountDtos;
    }
}