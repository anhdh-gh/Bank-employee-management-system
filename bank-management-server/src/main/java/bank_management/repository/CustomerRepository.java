package bank_management.repository;

import bank_management.entity.PaymentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bank_management.entity.Customer;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer getCustomerByCustomerCode(String customerCode);
}

// https://loda.me/articles/sb12-spring-jpa-method-query