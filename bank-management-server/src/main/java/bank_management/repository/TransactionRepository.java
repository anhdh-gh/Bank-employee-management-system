package bank_management.repository;

import bank_management.entity.BankAccount;
import bank_management.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String>{
    Transaction findTransactionByBankAccountReceiveOrderByCreateDate(BankAccount bankAccount);

    //List<Transaction> findAllByBankAccountReceiveIDOrBankAccountSentIDOrderByCreateDate(String bankAccountID);
    @Query(value = "CALL Proc_GetTransactionsByCustomerID(:CustomerID);", nativeQuery = true)
    List<Transaction> getTransactionsByCustomerID(@Param("CustomerID") String CustomerID);

    @Query("SELECT t FROM Transaction t WHERE t.bankAccountReceive.ID = ?1 OR t.bankAccountSent.ID = ?1")
    List<Transaction> getTransactionsByBankAccountID(String bankAccountID);
}