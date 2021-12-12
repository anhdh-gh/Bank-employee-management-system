package bank_management.api;


import bank_management.entity.Transaction;
import bank_management.enumeration.ResponseStatus;
import bank_management.payload.ResponseResult;
import bank_management.service.PersonService;
import bank_management.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    ObjectMapper json;

    @GetMapping
    public ResponseResult getTransactionsByCustomer() {
        List<Transaction> transactions = transactionService.getAllTransactionByCustomer();

        return new ResponseResult(transactions,
                "Lấy tất cả transaction thành công",
                    ResponseStatus.Success
                );
    }

    @GetMapping("/{bankAccountID}")
    public ResponseResult getTransactionsByBankAccountID(String bankAccountID) {
        List<Transaction> transactions = transactionService.getAllTransactionByBankAccountID(bankAccountID);

        return new ResponseResult(transactions,
                "Lấy tất cả transaction từ bankAccountID thành công",
                ResponseStatus.Success
        );
    }
}
