package bank_management.repository;

import bank_management.entity.BankAccount;
import bank_management.entity.Customer;
import bank_management.entity.PaymentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentAccountRepository extends JpaRepository<PaymentAccount, String> {
    PaymentAccount getPaymentAccountByCustomerID(String customerID);

    @Modifying
    @Query(value = "DELETE FROM paymentaccount WHERE BankAccountID = ?1", nativeQuery = true)
    int deletePaymentAccountByID(String id);
}