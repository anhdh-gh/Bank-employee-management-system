package bank_management.service;

import bank_management.entity.*;
import bank_management.repository.*;
import bank_management.repository.BankAccountRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    PersonService personService;

    @Autowired
    EmployeeRepository employeeRepo;

    public List<BankAccount> getAllBankAccount() {
        List<BankAccount> bankAccountList = bankAccountRepo.findAll();
        return bankAccountList;
    }

    public Employee getEmployeeByID(String ID) {
        Optional<Employee> optEmployee = employeeRepo.findById(ID);
        if(optEmployee.isPresent()) return optEmployee.get();
        return null;
    }

    public BankAccount getById(String id) {
        Optional<BankAccount> optionalBankAccount = bankAccountRepo.findById(id);
        if(optionalBankAccount.isPresent()) {
            BankAccount bankAccount = optionalBankAccount.get();
            return bankAccount;
        }
        return null;
    }

    public boolean checkAccountNumberExist(String accountNumber) {
        BankAccount accountWithNumber = bankAccountRepo.getBankAccountByAccountNumber(accountNumber);
        return accountWithNumber != null;
    }

    public Customer getCustomerByCustomerCode(String customerCode) {
        Customer customer = customerRepo.getCustomerByCustomerCode(customerCode);
        return customer;
    }

    public boolean checkPaymentAccountExistByCustomer(Customer customer) {
        PaymentAccount paymentAccount = paymentAccountRepo.getPaymentAccountByCustomerID(customer.getID());
        return paymentAccount != null;
    }

    public boolean checkCreditAccountExistByCustomer(Customer customer) {
        CreditAccount creditAccount = creditAccountRepo.getCreditAccountByCustomerID(customer.getID());
        return creditAccount != null;
    }

    public boolean checkAccounCodeExist(String accountCode) {
        BankAccount accountWithCode = bankAccountRepo.getBankAccountByAccountCode(accountCode);
        return accountWithCode != null;
    }

    public String autogenousAccountNumber() {
        String accountNumber = RandomStringUtils.randomNumeric(12);
        while(checkAccountNumberExist(accountNumber)) {
            accountNumber = RandomStringUtils.randomNumeric(12);
        }
        return accountNumber;
    }

    public String autogenousAccountCode() {
        String accountCode = RandomStringUtils.randomNumeric(16);
        while(checkAccounCodeExist(accountCode)) {
            accountCode = RandomStringUtils.randomNumeric(12);
        }
        return accountCode;
    }

    public PaymentAccount insertPaymentAccount(PaymentAccount paymentAccount) {
        if(this.checkPaymentAccountExistByCustomer(paymentAccount.getCustomer())) return null;
        paymentAccount.setMinBalance(50000);
        paymentAccount.setAmount(50000);

        switch (paymentAccount.getMemberLevel()) {
            case Standard:
                paymentAccount.setInterestRate(6);
                break;
            case Gold:
                paymentAccount.setInterestRate(4);
                break;
            case Platinum:
                paymentAccount.setInterestRate(0);
                break;
        }

        return paymentAccountRepo.save(paymentAccount);
    }

    public CreditAccount insertCreditAccount(CreditAccount creditAccount) {
        if(this.checkCreditAccountExistByCustomer(creditAccount.getCustomer())) return null;

        creditAccount.setDebtAmount(0);

        switch (creditAccount.getMemberLevel()) {
            case Standard:
                creditAccount.setCreditLimit(10000000);
                creditAccount.setInterestRate(6);
                break;
            case Gold:
                creditAccount.setCreditLimit(50000000);
                creditAccount.setInterestRate(4);
                break;
            case Platinum:
                creditAccount.setCreditLimit(100000000);
                creditAccount.setInterestRate(0);
                break;
        }

        return creditAccountRepo.save(creditAccount);
    }

    public boolean delete(String ID) {
        Optional<BankAccount> optionalBankAccount = bankAccountRepo.findById(ID);
        if (optionalBankAccount.isPresent()) {
            bankAccountRepo.delete(optionalBankAccount.get());
            return true;
        }
        return false;
    }


//    public Customer getCustomerByBankAccountID(String id) {
//        Customer customer = customerRepo.findFirstCustomerByPaymentAccountID(id);
//        if(customer == null)
//            customer = customerRepo.findFirstCustomerByCreditAccountID(id);
//        return customer;
//        return null;
//    }

//    public List<BankAccountDto> getAllBankAccount() {


//    public Customer getCustomerByBankAccountID(String id) {
//        Customer customer = customerRepo.findFirstCustomerByPaymentAccountID(id);
//        if(customer == null)
//            customer = customerRepo.findFirstCustomerByCreditAccountID(id);
//        return customer;
//    }

    public List<BankAccount> processSearch(String accountCode, String customerCode, String type) {
        List<BankAccount> res = bankAccountRepo.findAll();

        if(type != null)
            res.removeIf(b -> !b.getType().name().contains(type));

        if(accountCode != null)
            res.removeIf(b -> !b.getAccountCode().contains(accountCode));

        if(customerCode != null)
            res.removeIf(b -> {
                Optional<PaymentAccount> opa = paymentAccountRepo.findById(b.getID());
                Optional<CreditAccount> oca = creditAccountRepo.findById(b.getID());
                return
                    (opa.isPresent() && !opa.get().getCustomer().getCustomerCode().contains(customerCode))
                    ||
                    (oca.isPresent() && !oca.get().getCustomer().getCustomerCode().contains(customerCode));
            });

        return res;
    }
}