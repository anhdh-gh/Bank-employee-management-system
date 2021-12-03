package bank_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bank_management.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>{
	
	
}