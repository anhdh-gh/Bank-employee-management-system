package bank_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bank_management.entity.Account;
import bank_management.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, String>{
	Person findByAccount(Account account);
}