package bank_management.api;


import bank_management.entity.Transaction;
import bank_management.enumeration.ResponseStatus;
import bank_management.payload.ResponseResult;
import bank_management.payload.SendMoneyRequestPayload;
import bank_management.payload.TransactionsFilterRequest;
import bank_management.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
     * Lấy tất cả transaction theo filter
     * NVTOAN
     * @return
     */
    @GetMapping("/filter")
    public ResponseResult getTransactionsFilter(@Valid @RequestBody TransactionsFilterRequest transactionsFilterRequest) {
        List<Transaction> transactions = transactionService.getAllTransactionFilter(transactionsFilterRequest);
        int allRecord = transactionService.getAllTransactionByCustomer().size();

        Object data = new Object() {
          List<Transaction> records = transactions;
          int recordNum = allRecord;
        };

        return new ResponseResult(data,
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
                "Lấy transaction code mới nhất thành công",
                ResponseStatus.Success
        );
    }

    /**
     * Xử lý send money
     * NVTOAN
     * @return
     */
    @PostMapping("/sendMoney")
    public ResponseEntity sendMoney(@Valid @RequestBody SendMoneyRequestPayload sendMoneyRequestPayload) throws Exception {
        ResponseResult responseResult = transactionService.sendMoney(sendMoneyRequestPayload);

        if(responseResult.getResponseStatus() == ResponseStatus.Invalid) {
            return ResponseEntity.badRequest()
                                .body(responseResult);
        }

        return ResponseEntity.ok()
                .body(responseResult);
    }

    /**
     * Xử lý deposit money
     * NVTOAN
     * @return
     */
    @PostMapping("/deposit/{amount}")
    public ResponseEntity deposit(@PathVariable(value = "amount") double amount) throws Exception {
        ResponseResult responseResult = transactionService.depositMoney(amount);

        if(responseResult.getResponseStatus() == ResponseStatus.Invalid) {
            return ResponseEntity.badRequest()
                    .body(responseResult);
        }

        return ResponseEntity.ok()
                .body(responseResult);
    }

    /**
     * Thanh toán credit card
     * NVTOAN
     * @return
     */
    @PostMapping("/payCreditCard/{amount}")
    public ResponseEntity payCreditCard(@PathVariable(value = "amount") double amount) throws Exception {
        ResponseResult responseResult = transactionService.payCreditCard(amount);

        if(responseResult.getResponseStatus() == ResponseStatus.Invalid) {
            return ResponseEntity.badRequest()
                    .body(responseResult);
        }

        return ResponseEntity.ok()
                .body(responseResult);
    }
}
