package bank_management.api;

import bank_management.dto.BankAccountDto;

import bank_management.entity.*;
import bank_management.enumeration.BankAccountType;
import bank_management.enumeration.ResponseStatus;
import bank_management.payload.AddBankAccountRequest;
import bank_management.payload.ResponseResult;
import bank_management.payload.SearchBankAccountRequest;
import bank_management.service.BankAccountService;
import bank_management.service.EmployeeService;
import bank_management.service.PersonService;
import bank_management.utils.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/bank_account")
public class BankAccountController {

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    PersonService personService;

    @Autowired
    ObjectMapper json;

    @GetMapping
    public ResponseEntity<?> getAllBankAccount() {
        List<BankAccount> bankAccounts = bankAccountService.getAllBankAccount();

        return ResponseEntity.ok(
            new ResponseResult (
                bankAccounts,
                "Lấy thành công tất cả bank account",
                ResponseStatus.Success
            )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBankAccounts(@Valid SearchBankAccountRequest search) {
        List<BankAccount> bankAccounts = bankAccountService.processSearch(search.getAccountCode(), search.getCustomerCode(), search.getType());
        return ResponseEntity.ok(new ResponseResult(bankAccounts, "Tìm kiếm thành công", ResponseStatus.Success));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByID(@PathVariable(value = "id") String id) {
        BankAccount bankAccount = bankAccountService.getById(id);

        if(bankAccount == null)
            return ResponseEntity
                .badRequest()
                .body(new ResponseResult(
                    "Không tìm thấy bank account với id là " + id,
                    ResponseStatus.Invalid
                ));

        return ResponseEntity.ok(
            new ResponseResult (
                bankAccount,
                "Lấy bank account thành công",
                ResponseStatus.Success
            )
        );
    }

    @PostMapping
    public ResponseEntity<?> insert(@Valid @RequestBody AddBankAccountRequest req) {
        if(DateUtils.isLessThanCurrentDate(req.getExpireDate()))
            return ResponseEntity
                .badRequest()
                .body(new ResponseResult(
                    "ExpireDate phải lớn hơn ngày hiện tại",
                    ResponseStatus.Invalid
                ));

        Customer customer = bankAccountService.getCustomerByCustomerCode(req.getCustomerCode());
        if(customer == null)
            return ResponseEntity
                .badRequest()
                .body(new ResponseResult(
                    "Customer không tồn tại",
                    ResponseStatus.Invalid
                ));

        Employee employee = bankAccountService.getEmployeeByID(personService.getAuthPerson().getID());
        if(employee == null)
            return ResponseEntity
                .badRequest()
                .body(new ResponseResult(
                    "Không tìm thấy employee đăng ký tài khoản",
                    ResponseStatus.Error
                ));

        BankAccount bankAccount = new BankAccount(
            req.getMemberLevel(),
            bankAccountService.autogenousAccountCode(),
            bankAccountService.autogenousAccountNumber(),
            req.getExpireDate(),
            req.getBranch(),
            req.getType(),
            false,
            (Employee) personService.getAuthPerson(),
            req.getCVV()
        );

        switch (bankAccount.getType()) {
            case Payment: {
                PaymentAccount paymentAccount = new PaymentAccount(bankAccount);
                paymentAccount.setCustomer(customer);
                paymentAccount = bankAccountService.insertPaymentAccount(paymentAccount);
                if(paymentAccount == null)
                    return ResponseEntity
                        .badRequest()
                        .body(new ResponseResult(
                            "Customer đã tạo payment account trước đó",
                            ResponseStatus.Invalid
                        ));
                return ResponseEntity
                    .ok()
                    .body(new ResponseResult(
                        paymentAccount,
                        "Tạo thành công payment account",
                        ResponseStatus.Success
                    ));
            }
            case Credit: {
                // Kiểm tra người dùng đã tạo payment account ?
                PaymentAccount paymentAccount = bankAccountService.getPaymentAccountByCustomerID(customer.getID());
                if(paymentAccount == null)
                    return ResponseEntity
                        .badRequest()
                        .body(new ResponseResult(
                            "Customer chưa tạo payment account",
                            ResponseStatus.Invalid
                        ));

                CreditAccount creditAccount = new CreditAccount(bankAccount);
                creditAccount.setCustomer(customer);

                creditAccount = bankAccountService.insertCreditAccount(creditAccount);
                if(creditAccount == null)
                    return ResponseEntity
                        .badRequest()
                        .body(new ResponseResult(
                            "Customer đã tạo credit account trước đó",
                            ResponseStatus.Invalid
                        ));
                return ResponseEntity
                    .ok()
                    .body(new ResponseResult(
                        creditAccount,
                        "Tạo thành công credit account",
                        ResponseStatus.Success
                    ));
            }
            default: {
                return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult(
                        "Có lỗi sảy ra",
                        ResponseStatus.Error
                    ));
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteByID(@PathVariable(value = "id") String id) throws Exception {
        BankAccount bankAccount = bankAccountService.getById(id);

        if(bankAccount == null)
            return ResponseEntity
                .badRequest()
                .body(
                    new ResponseResult(
                        "Không tìm thấy bank account với id là " + id,
                        ResponseStatus.Invalid
                ));

        bankAccountService.delete(bankAccount.getID());

        return ResponseEntity.ok(
            new ResponseResult (
                "Xóa bank account thành công",
                ResponseStatus.Success
            )
        );
    }
}
