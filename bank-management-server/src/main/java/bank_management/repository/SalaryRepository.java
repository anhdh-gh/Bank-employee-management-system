package bank_management.repository;

import bank_management.entity.BankAccount;
import bank_management.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, String>{
    List<Salary> findAllByEmployee_ID(String employeeID);
    List<Salary> findByMonthAndYear(int month, int year);
    List<Salary> findAllByMonthAndYearOrderByMonth(int month, int year);

    @Query("SELECT s FROM Salary s ORDER BY s.year, s.month")
    List<Salary> findAllOrderByMonthAndYear();

    @Query(nativeQuery = true,
            value = "SELECT s.* FROM Salary s INNER JOIN User u ON EmployeeID = PersonID WHERE (year > :startYear AND year < :endYear) OR (year = :startYear AND month >= :startMonth AND month <= :endMonth) OR (year = :endYear AND month >= :startMonth AND month <= :endMonth ) ORDER BY year, month")
    List<Salary> searchSalary(@Param(value = "startMonth") int startMonth, @Param(value = "startYear") int startYear,
                              @Param(value = "endMonth") int endMonth, @Param(value = "endYear") int endYear);

    @Query(nativeQuery = true, value = "SELECT s.* FROM Salary s INNER JOIN User u ON EmployeeID = PersonID WHERE u.position = :position ORDER BY year, month")
    List<Salary> searchSalaryByPosition(@Param(value = "position") String position);
}