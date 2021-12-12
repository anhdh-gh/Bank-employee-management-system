package bank_management.repository;

import bank_management.entity.FullName;
import bank_management.entity.PaymentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bank_management.entity.Customer;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer getCustomerByCustomerCode(String customerCode);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "DELETE FROM customer WHERE PersonID= ?1", nativeQuery = true)
    int deleteCustomerById(String id);
}

// https://loda.me/articles/sb12-spring-jpa-method-query