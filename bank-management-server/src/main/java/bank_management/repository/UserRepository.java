package bank_management.repository;

import bank_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    @Query(nativeQuery = true, value = "SELECT u.employeeCode FROM User u ORDER BY u.employeeCode DESC LIMIT 1")
    String getLatestEmployeeCode();

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "DELETE FROM user WHERE PersonID = ?1", nativeQuery = true)
    int deleteUserByID(String id);
}
