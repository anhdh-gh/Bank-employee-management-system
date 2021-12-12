package bank_management.repository;

import bank_management.entity.BankAccount;
import bank_management.entity.CreditAccount;
import bank_management.entity.PaymentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditAccountRepository extends JpaRepository<CreditAccount, String>{
    CreditAccount getCreditAccountByCustomerID(String customerID);

    @Modifying
    @Query(value = "DELETE FROM creditaccount WHERE BankAccountID = ?1", nativeQuery = true)
    int deleteCreditAccountByID(String id);
}