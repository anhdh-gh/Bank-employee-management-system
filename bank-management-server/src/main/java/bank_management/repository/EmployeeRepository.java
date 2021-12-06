package bank_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bank_management.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>{
	Employee findEmployeeByEmail(String email);
	
}