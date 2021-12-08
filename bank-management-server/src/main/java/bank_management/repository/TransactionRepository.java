package bank_management.repository;

import bank_management.entity.BankAccount;
import bank_management.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String>{
    Transaction findTransactionByBankAccountReceiveOrderByCreateDate(BankAccount bankAccount);
}