package bank_management.repository;

import bank_management.entity.BankAccount;
import bank_management.entity.Customer;
import bank_management.entity.PaymentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentAccountRepository extends JpaRepository<PaymentAccount, String> {
    PaymentAccount getPaymentAccountByCustomerID(String customerID);
}