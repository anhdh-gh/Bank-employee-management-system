package bank_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bank_management.entity.People;

@Repository
public interface PeopleRepository extends JpaRepository<People, String>{

}