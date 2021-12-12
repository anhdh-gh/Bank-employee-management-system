package bank_management.service;

import bank_management.dto.CustomerDto;
import bank_management.entity.Customer;
import bank_management.repository.CustomerRepository;
import bank_management.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService extends PersonService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public CustomerDto findCustomerById(String ID) {
        Optional<Customer> optionalCustomer = customerRepository.findById(ID);
        if (optionalCustomer.isPresent()) {
            return new CustomerDto(optionalCustomer.get());
        }
        return null;
    }

    public List<CustomerDto> findAllCustomer() {
        List<CustomerDto> customerDtoList = new ArrayList<>();
        List<Customer> customerList = customerRepository.findAll();
        for(Customer customer : customerList){
            customerDtoList.add(new CustomerDto(customer));
        }
        return customerDtoList;
    }

    public Customer editCustomer(Customer customer)
    {
        return customerRepository.save(customer);
    }

    public CustomerDto addCustomer(CustomerDto customerDto) {
        customerDto.setCustomerCode(generateCustomerCode());
        Customer customer = new Customer(customerDto);
        customer.getAccount().setPassword(passwordEncoder.encode(customer.getAccount().getPassword()));
        return new CustomerDto(customerRepository.save(customer));
    }

    public boolean deleteCustomer(String customerID) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerID);
        if (optionalCustomer.isPresent()) {
            customerRepository.delete(optionalCustomer.get());
            Optional<Customer> optionalCustomer_check = customerRepository.findById(customerID);
            if(!optionalCustomer_check.isPresent()){
                return true;
            }
        }
        return false;
    }

    public List<Customer> processSearch(String customerCode, String customerName, String gender){
        System.out.println(customerCode + " " + customerName + " " + gender);
        List<Customer> resultSearch = customerRepository.findAll();

        if(!gender.equals("Select gender")){
            resultSearch.removeIf(customer -> !customer.getGender().name().equals(gender));
        }

        if(customerCode != null){
            resultSearch.removeIf(customer -> !customer.getCustomerCode().contains(customerCode));
        }

        if(customerName!= null){
            resultSearch.removeIf(customer -> !(customer.getFullName().getFirstName() + " " + customer.getFullName().getLastName()).contains(customerName));
        }
        return resultSearch;
    }

    private String generateCustomerCode(){
        List<Customer> customerList = customerRepository.findAll();
        int lastest = 0;
        for(Customer customer: customerList){
            String customerCode = customer.getCustomerCode();
            int number = Integer.parseInt(customerCode.substring(3));
            lastest = (number > lastest) ? number : lastest;
        }
        return "KH-" + (lastest+1);
    }

    public CustomerDto getCustomerDtoById(String customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if(optionalCustomer.isPresent()){
            return new CustomerDto((optionalCustomer.get()));
        }
        return null;
    }

    public Customer getCustomerById(String customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if(optionalCustomer.isPresent()){
            return optionalCustomer.get();
        }
        return null;
    }

    public Customer getById(String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(optionalCustomer.isPresent()){
            Customer customer = optionalCustomer.get();
            return customer;
        }
        return null;
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void delete(String id) throws Exception {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(optionalCustomer.isPresent()){
            Customer customer = optionalCustomer.get();
            int rowCustomer = customerRepository.deleteCustomerById(customer.getID());
            if(rowCustomer < 1){
                throw new Exception("Xóa customer không thành công!");
            }
            int rowPerson = personRepository.deletePersonByID(customer.getID());
            if(rowPerson < 1){
                throw new Exception("Xóa customer không thành công!");
            }
        }
    }
}
