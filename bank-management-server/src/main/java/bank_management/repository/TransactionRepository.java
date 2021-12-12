package bank_management.repository;

import bank_management.entity.BankAccount;
import bank_management.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String>{
    @Query( nativeQuery = true, value = "SELECT t.* FROM Transaction t WHERE bankAccountReceiveID = :BankAccountReceiveID ORDER BY t.createDate LIMIT 1")
    Transaction getAmountOfFirstTransactionByPaymentAccount(@Param(value = "BankAccountReceiveID") String paymentAccountID);

    @Query("SELECT t FROM Transaction t WHERE t.bankAccountReceive.ID = ?1 OR t.bankAccountSent.ID = ?1")
    List<Transaction> getTransactionsByBankAccountID(String bankAccountID);
}