package bank_management.repository;

import bank_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
//    List<User> findAllBy;
    @Query(nativeQuery = true, value = "SELECT u.employeeCode FROM User u ORDER BY u.employeeCode DESC LIMIT 1")
    String getLatestEmployeeCode();
}
