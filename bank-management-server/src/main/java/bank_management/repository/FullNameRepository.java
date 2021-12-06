package bank_management.repository;

import bank_management.entity.Account;
import bank_management.entity.FullName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FullNameRepository extends JpaRepository<FullName, String>{

}