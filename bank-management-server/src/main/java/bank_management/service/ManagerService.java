package bank_management.service;

import bank_management.dto.EmployeeDto;
import bank_management.entity.Employee;
import bank_management.entity.Manager;
import bank_management.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManagerService {
    @Autowired
    ManagerRepository managerRepository;

    public Manager getManagerByID(String managerID) {
        Optional<Manager> optionalManager = managerRepository.findById(managerID);
        if (optionalManager.isPresent()) {
            return optionalManager.get();
        }
        else return null;
    }
}
