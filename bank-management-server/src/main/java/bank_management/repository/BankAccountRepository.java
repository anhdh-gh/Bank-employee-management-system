package bank_management.repository;

import bank_management.entity.Account;
import bank_management.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String>{
    BankAccount getBankAccountByAccountNumber(String accountNumber);
    BankAccount getBankAccountByAccountCode(String accountCode);

    @Modifying (clearAutomatically = true) // Xóa trong bộ nhớ cache
    @Transactional
    @Query(value = "DELETE FROM bankaccount WHERE BankAccountID = ?1", nativeQuery = true)
    int deleteBankAccountByID(String bankAccountID);
}