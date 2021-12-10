package bank_management.api;

import bank_management.dto.BankAccountDto;

import bank_management.entity.BankAccount;
import bank_management.entity.Customer;
import bank_management.enumeration.BankAccountType;
import bank_management.enumeration.ResponseStatus;
import bank_management.payload.ResponseResult;
import bank_management.payload.SearchBankAccountRequest;
import bank_management.service.BankAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bank_account")
public class BankAccountController {

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    ObjectMapper json;

    @GetMapping
    public ResponseEntity<?> getAllBankAccount() {
        List<BankAccountDto> bankAccountDtoListList = bankAccountService.getAllBankAccount();

        return ResponseEntity.ok(
            new ResponseResult (
                bankAccountDtoListList,
                "Lấy thành công tất cả bank account",
                ResponseStatus.Success
            )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBankAccounts(@Valid SearchBankAccountRequest search) {
        List<BankAccountDto> bankAccountDtos = bankAccountService.processSearch(search.getAccountCode(), search.getCustomerCode(), search.getType());
        return ResponseEntity.ok(new ResponseResult(bankAccountDtos, "Tìm kiếm thành công", ResponseStatus.Success));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByID(@PathVariable(value = "id") String id) {
        BankAccountDto bankAccountDto = bankAccountService.getById(id);

        if(bankAccountDto == null)
            return ResponseEntity
                    .badRequest()
                    .body(
                        new ResponseResult(
                        "Không tìm thấy bank account với id là " + id,
                        ResponseStatus.Invalid
                    ));

        return ResponseEntity.ok(
                new ResponseResult (
                    bankAccountDto,
                    "Lấy bank account thành công",
                    ResponseStatus.Success
            )
        );
    }

    @PostMapping
    public ResponseEntity<?> insert(@Valid @RequestBody BankAccount bankAccount) {
        BankAccount res = bankAccountService.insert(bankAccount);

        if(res == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult(
                        bankAccount,
                        "AccountCode hoặc AccountNumber đã tồn tại",
                        ResponseStatus.Invalid)
                    );
        }

        return ResponseEntity.ok()
                .body(new ResponseResult(
                    bankAccount,
                    "",
                    ResponseStatus.Success)
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteByID(@PathVariable(value = "id") String id) {
        BankAccountDto bankAccountDto = bankAccountService.getById(id);

        if(bankAccountDto == null)
            return ResponseEntity
                    .badRequest()
                    .body(
                        new ResponseResult(
                            "Không tìm thấy bank account với id là " + id,
                            ResponseStatus.Invalid
                    ));

        bankAccountService.delete(bankAccountDto.getID());

        return ResponseEntity.ok(
                new ResponseResult (
                    bankAccountDto,
                    "Xóa bank account thành công",
                    ResponseStatus.Success
                )
        );
    }
}
