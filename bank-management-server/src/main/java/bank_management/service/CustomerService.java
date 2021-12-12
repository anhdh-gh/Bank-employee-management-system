package bank_management.service;

import bank_management.dto.CustomerDto;
import bank_management.entity.Customer;
import bank_management.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService extends PersonService {
    @Autowired
    CustomerRepository customerRepository;

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

    public CustomerDto editCustomer(CustomerDto customerDto) {
        Customer customer = new Customer(customerDto);
        Optional<Customer> optionalCustomer = customerRepository.findById(customerDto.getID());
        if (optionalCustomer.isPresent()) {
            return new CustomerDto(customerRepository.save(customer));
        }
        return null;
    }

    public CustomerDto addCustomer(CustomerDto customerDto) {
        customerDto.setCustomerCode(generateCustomerCode());
        Customer customer = new Customer(customerDto);
        return new CustomerDto(customerRepository.save(customer));
    }

    public boolean deleteCustomer(String customerID) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerID);
        if (optionalCustomer.isPresent()) {
            customerRepository.delete(optionalCustomer.get());
            return true;
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

    public CustomerDto getCustomerById(String customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if(optionalCustomer.isPresent()){
            return new CustomerDto((optionalCustomer.get()));
        }
        return null;
    }
}
