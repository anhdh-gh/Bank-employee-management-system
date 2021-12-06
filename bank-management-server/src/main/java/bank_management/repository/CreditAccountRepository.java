package bank_management.repository;

import bank_management.entity.BankAccount;
import bank_management.entity.CreditAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditAccountRepository extends JpaRepository<CreditAccount, String>{
}