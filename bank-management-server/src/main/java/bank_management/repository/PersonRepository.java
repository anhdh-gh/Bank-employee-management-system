package bank_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bank_management.entity.Account;
import bank_management.entity.Person;

import javax.transaction.Transactional;

@Repository
public interface PersonRepository extends JpaRepository<Person, String>{
	Person findByAccount(Account account);
	Person findByIdentityNumber(String identityNumber);
	Person findByEmail(String email);
	Person findByPhoneNumber(String phoneNumber);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "DELETE FROM person WHERE PersonID = ?1", nativeQuery = true)
	int deletePersonByID(String id);
}