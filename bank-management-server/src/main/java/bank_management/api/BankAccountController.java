package bank_management.api;

import bank_management.dto.BankAccountDto;
import bank_management.dto.ResponseResult;
import bank_management.dto.SalaryDto;
import bank_management.entity.Account;
import bank_management.entity.BankAccount;
import bank_management.enumeration.ResponseStatus;
import bank_management.service.BankAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bankAccount")
public class BankAccountController {

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    ObjectMapper json;

    @GetMapping
    public ResponseResult getAllSalary() {
        List<BankAccountDto> bankAccountDtoListList = bankAccountService.getAllBankAccount();

        return new ResponseResult(
                bankAccountDtoListList,
                "",
                ResponseStatus.Success
        );
    }

    @GetMapping("/{id}")
    public ResponseResult getByID(@PathVariable(value = "id")String id) {
        return new ResponseResult(
                bankAccountService.getById(id),
                "",
                ResponseStatus.Success
        );
    }

    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody BankAccount bankAccount) {
        BankAccount res = bankAccountService.insert(bankAccount);

        if(res == null) {
            return ResponseEntity.badRequest()
                    .body(new ResponseResult(bankAccount, "Số tài khoản đã tồn tại", ResponseStatus.Invalid));
        }
        else {
            return ResponseEntity.ok()
                    .body(new ResponseResult(bankAccount, "", ResponseStatus.Invalid));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity edit(@Valid @RequestBody BankAccount bankAccount, @RequestHeader String id) {
        BankAccount res = bankAccountService.update(bankAccount);

        if(res == null) {
            return ResponseEntity.badRequest()
                    .body(new ResponseResult(bankAccount, "Số tài khoản đã tồn tại", ResponseStatus.Invalid));
        }
        else {
            return ResponseEntity.ok()
                    .body(new ResponseResult(bankAccount, "", ResponseStatus.Invalid));
        }
    }

}
