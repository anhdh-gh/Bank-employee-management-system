package bank_management.repository;

import bank_management.enumeration.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bank_management.entity.Employee;

import javax.transaction.Transactional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>{
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE Employee e SET e.baseSalary = :baseSalary, e.position = :position WHERE e.ID = :ID")
	int updateEmployee(@Param("ID") String ID, @Param("baseSalary") double baseSalary, @Param("position") Position position);
}