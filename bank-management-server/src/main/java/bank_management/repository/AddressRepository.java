package bank_management.repository;

import bank_management.entity.Address;
import bank_management.entity.FullName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, String>{

}