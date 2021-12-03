package bank_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bank_management.entity.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, String>{
	
	
}