package bank_management.repository;

import bank_management.entity.BankAccount;
import bank_management.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, String>{
    List<Salary> findAllByEmployee_ID(String employeeID);
    List<Salary> findByMonthAndYear(int month, int year);

}