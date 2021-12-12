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
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * Lấy tất cả transaction của customer đang đăng nhập
     * NVTOAN
     * @return
     */
    @GetMapping
    public ResponseResult getTransactionsByCustomer() {
        List<Transaction> transactions = transactionService.getAllTransactionByCustomer();

        return new ResponseResult(transactions,
                "Lấy tất cả transaction thành công",
                    ResponseStatus.Success
                );
    }

    /**
     * Lấy tất cả transaction theo bankAccountID
     * NVTOAN
     * @return
     */
    @GetMapping("/{bankAccountID}")
    public ResponseResult getTransactionsByBankAccountID(@PathVariable(value = "bankAccountID")String bankAccountID) {
        List<Transaction> transactions = transactionService.getAllTransactionByBankAccountID(bankAccountID);

        return new ResponseResult(transactions,
                "Lấy tất cả transaction từ bankAccountID thành công",
                ResponseStatus.Success
        );
    }

    /**
     * Lấy transaction code mới
     * NVTOAN
     * @return
     */
    @GetMapping("/newCode")
    public ResponseResult getNewTransactionCode(String bankAccountID) {
        String newCode = transactionService.getNewTransactionCode();

        return new ResponseResult(newCode,
                "Lấy tất cả transaction code",
                ResponseStatus.Success
        );
    }
}
