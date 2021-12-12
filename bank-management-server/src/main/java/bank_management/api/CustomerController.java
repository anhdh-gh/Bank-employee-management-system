package bank_management.api;


import bank_management.dto.CustomerDto;
import bank_management.dto.EmployeeDto;
import bank_management.entity.Address;
import bank_management.entity.BankAccount;
import bank_management.entity.Customer;
import bank_management.entity.Person;
import bank_management.enumeration.ResponseStatus;
import bank_management.payload.EditCustomerRequest;
import bank_management.payload.ResponseResult;
import bank_management.payload.SearchBankAccountRequest;
import bank_management.payload.SearchCustomerRequest;
import bank_management.service.BankAccountService;
import bank_management.service.CustomerService;
import bank_management.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @Autowired
    PersonService personService;

    @Autowired
    BankAccountService bankAccountService;

    @PostMapping
    public ResponseEntity<?> addCustomer(@Valid @RequestBody CustomerDto customerDto) {

        if (customerService.checkUsernameExist(customerDto.getAccount().getUsername())) {
            return ResponseEntity.badRequest().body(new ResponseResult("Username đã tồn tại!", ResponseStatus.Error));
        }

        if (customerService.checkIdentityNumberExist(customerDto.getIdentityNumber())) {
            return ResponseEntity.badRequest().body(new ResponseResult("IdentityNumber đã tồn tại!", ResponseStatus.Error));
        }

        if (customerService.checkEmailExist(customerDto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("Email đã tồn tại!", ResponseStatus.Error));
        }
        if (customerService.checkPhoneNumberExist(customerDto.getPhoneNumber())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("Số điện thoại đã tồn tại!", ResponseStatus.Error));
        }
        CustomerDto customerDtoSaved = customerService.addCustomer(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseResult(customerDtoSaved, "Tạo tài khoản khách hàng thành công.", ResponseStatus.Success));
    }
    
    @GetMapping("/{id}")
    public  ResponseEntity<?> getCustomer(@PathVariable(value = "id") String customerId){
        CustomerDto customer = customerService.findCustomerById(customerId);
        if(customer == null){
            return ResponseEntity.badRequest().body(new ResponseResult("Không tìm thấy khách hàng có ID là " + customerId + "!", ResponseStatus.Error));
        }
        return ResponseEntity.ok().body(new ResponseResult(customer, "Lấy thông tin khách hàng có ID là " + customerId + " thành công.", ResponseStatus.Success ));
    }

    @GetMapping
    public ResponseResult getAllCustomer(){
        List<CustomerDto> customerDtoList = customerService.findAllCustomer();
        return new ResponseResult(customerDtoList, "Lấy thông tin tất cả khách hàng thành công!", ResponseStatus.Success);
    }

    @PutMapping("/editByEmployee")
    public ResponseEntity<?> editCustomer(@Valid @RequestBody EditCustomerRequest editCustomerRequest){
//        Person person = personService.getAuthPerson();
        Customer customerEdit = customerService.getCustomerById(editCustomerRequest.getId());
        if(customerEdit == null){
            return ResponseEntity.badRequest().body(new ResponseResult("Có lỗi xảy ra khi sửa đổi thông tin khách hàng!", ResponseStatus.Error));
        }
        else{
            if(!editCustomerRequest.getEmail().equals(customerEdit.getEmail()) && personService.checkEmailExist(editCustomerRequest.getEmail())){
                return ResponseEntity.badRequest().body(new ResponseResult(
                        "Email đã tồn tại!",
                        ResponseStatus.Invalid));
            }
            if(!editCustomerRequest.getPhoneNumber().equals(customerEdit.getPhoneNumber()) && personService.checkPhoneNumberExist(editCustomerRequest.getPhoneNumber())){
                return ResponseEntity.badRequest().body(new ResponseResult(
                        "Số điện thoại đã tồn tại!",
                        ResponseStatus.Invalid));
            }
            if(!editCustomerRequest.getIdentityNumber().equals(customerEdit.getIdentityNumber())&& personService.checkIdentityNumberExist(editCustomerRequest.getIdentityNumber())){
                return ResponseEntity.badRequest().body(new ResponseResult(
                        "IdentityNumber đã tồn tại!",
                        ResponseStatus.Invalid));
            }
            customerEdit.setEmail(editCustomerRequest.getEmail());
            customerEdit.setPhoneNumber(editCustomerRequest.getPhoneNumber());
            customerEdit.setGender(editCustomerRequest.getGender());
            customerEdit.setIdentityNumber(editCustomerRequest.getIdentityNumber());
            customerEdit.setAddress(editCustomerRequest.getAddress());
            Customer customerRes = customerService.editCustomer(customerEdit);
            if(customerRes == null){
                return ResponseEntity.badRequest().body(new ResponseResult(
                        "Chỉnh sửa thông tin không thành công!",
                        ResponseStatus.Error));
            }
            return ResponseEntity.ok().body(new ResponseResult(
                    customerEdit,
                    "Chỉnh sửa thông tin khách hàng thành công!",
                    ResponseStatus.Success
            ));
        }
    }

    @PutMapping("/editAddress")
    public ResponseEntity<?> editAddressCustomer(@Valid @RequestBody Address address){
        Person person = personService.getAuthPerson();
        Customer customerEdit = customerService.getCustomerById(person.getID());
        if(customerEdit == null){
            return ResponseEntity.badRequest().body(new ResponseResult("Có lỗi xảy ra khi sửa đổi thông tin khách hàng!", ResponseStatus.Error));
        }
        else{
            customerEdit.setAddress(address);
            Customer customerRes = customerService.editCustomer(customerEdit);
            if(customerRes == null){
                return ResponseEntity.badRequest().body(new ResponseResult(
                        "Chỉnh sửa thông tin không thành công!",
                        ResponseStatus.Error));
            }
            return ResponseEntity.ok().body(new ResponseResult(
                    customerEdit,
                    "Chỉnh sửa thông tin thành công!",
                    ResponseStatus.Success
            ));
        }
    }

    @PutMapping("/editEmail")
    public ResponseEntity<?> editEmailCustomer(@Valid @RequestBody String email){
        Person person = personService.getAuthPerson();
        Customer customerEdit = customerService.getCustomerById(person.getID());
        if(customerEdit == null){
            return ResponseEntity.badRequest().body(new ResponseResult("Có lỗi xảy ra khi sửa đổi thông tin khách hàng!", ResponseStatus.Error));
        }
        else{
            if(email!= null){
                if(!email.trim().isEmpty()){
                    customerEdit.setEmail(email);
                }
                else{
                    return ResponseEntity.badRequest().body(new ResponseResult(
                            "Chỉnh sửa thông tin không thành công!",
                            ResponseStatus.Error));
                }
            }
            else{
                return ResponseEntity.badRequest().body(new ResponseResult(
                        "Chỉnh sửa thông tin không thành công!",
                        ResponseStatus.Error));
            }
            Customer customerRes = customerService.editCustomer(customerEdit);
            if(customerRes == null){
                return ResponseEntity.badRequest().body(new ResponseResult(
                        "Chỉnh sửa thông tin không thành công!",
                        ResponseStatus.Error));
            }
            return ResponseEntity.ok().body(new ResponseResult(
                    customerEdit,
                    "Chỉnh sửa thông tin thành công!",
                    ResponseStatus.Success
            ));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getCurrentEmployee() {
        Person person = personService.getAuthPerson();
        CustomerDto customerDto = customerService.getCustomerDtoById(person.getID());
        if (customerDto == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("Không thể lấy thông tin khách hàng!", ResponseStatus.Error));
        }
        else {
            return ResponseEntity
                    .ok()
                    .body(new ResponseResult(customerDto ,"Lấy thông tin thành công.", ResponseStatus.Success ));
        }
    }



    @GetMapping("/search")
    public ResponseEntity<?> searchCustomers(@Valid SearchCustomerRequest search) {
        List<Customer> customerList = customerService.processSearch(search.getCustomerCode(), search.getCustomerName(), search.getGender());
        return ResponseEntity.ok(new ResponseResult(customerList, "Tìm kiếm thành công", ResponseStatus.Success));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteByID(@PathVariable(value = "id") String id) throws Exception {
        Customer customer = customerService.getById(id);
        if(customer == null)
            return ResponseEntity
                    .badRequest()
                    .body(
                            new ResponseResult(
                                    "Không tìm thấy customer với id là " + id,
                                    ResponseStatus.Invalid
                            ));

        customerService.delete(customer.getID());

        return ResponseEntity.ok(
                new ResponseResult (
                        "Xóa customer thành công",
                        ResponseStatus.Success
                )
        );
    }
}
