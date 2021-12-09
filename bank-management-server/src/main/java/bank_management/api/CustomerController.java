package bank_management.api;


import bank_management.dto.CustomerDto;
import bank_management.entity.Customer;
import bank_management.enumeration.ResponseStatus;
import bank_management.payload.ResponseResult;
import bank_management.service.CustomerService;
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
        return ResponseEntity.ok().body(new ResponseResult("Lấy thông tin khách hàng có ID là " + customerId + " thành công.", ResponseStatus.Success ));
    }

    @GetMapping
    public ResponseResult getAllCustomer(){
        List<CustomerDto> customerDtoList = customerService.findAllCustomer();
        return new ResponseResult(customerDtoList, "Lấy thông tin tất cả khách hàng thành công!", ResponseStatus.Success);
    }

    @PutMapping
    public ResponseEntity<?> editCustomer(@Valid @RequestBody CustomerDto customerDto){
        CustomerDto customerEdit = customerService.editCustomer(customerDto);
        if(customerEdit == null){
            return ResponseEntity.badRequest().body(new ResponseResult("Có lỗi xảy ra khi sửa đổi thông tin khách hàng!", ResponseStatus.Error));
        }
        else{
            return ResponseEntity.ok().body(new ResponseResult(
                    customerEdit,
                    "Chỉnh sửa thông tin khách hàng thành công!",
                    ResponseStatus.Success
            ));
        }
    }
}