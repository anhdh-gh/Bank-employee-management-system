package bank_management.service;

import bank_management.entity.*;
import bank_management.enumeration.ResponseStatus;
import bank_management.enumeration.TransactionStatus;
import bank_management.payload.ResponseResult;
import bank_management.payload.SendMoneyRequestPayload;
import bank_management.payload.TransactionsFilterRequest;
import bank_management.repository.BankAccountRepository;
import bank_management.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    PersonService personService;

    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    BankAccountService bankAccountService;

    /**
     * Lấy tất cả transaction của customer đang đăng nhập
     * NVTOAN
     * @return
     */
    public List<Transaction> getAllTransactionByCustomer() {
        Person currentCustomer =  personService.getAuthPerson();

        return  transactionRepository.getTransactionsByCustomerID(currentCustomer.getID());
    }

    /**
     * Lấy tất cả transaction theo bankAccountID
     * NVTOAN
     * @return
     */
    public List<Transaction> getAllTransactionByBankAccountID(String bankAccountID) {
        return transactionRepository.getTransactionsByBankAccountID(bankAccountID);
    }

    /**
     * Lấy tất cả transaction theo filter
     * NVTOAN
     * @return
     */
    public List<Transaction> getAllTransactionFilter(TransactionsFilterRequest transactionsFilterRequest) {
        return transactionRepository.getTransactionsFilter(personService.getAuthPerson().getID(),
                transactionsFilterRequest.getStartDate(),
                transactionsFilterRequest.getEndDate(),
                transactionsFilterRequest.getPageNum(),
                transactionsFilterRequest.getPageSize());
    }

    /**
     * Tạo ra transaction code mới nhất
     * NVTOAN
     * @return
     */
    public String getNewTransactionCode() {
        String maxCode = transactionRepository.getMaxTransactionCode();
        String newCode = maxCode;

        if(maxCode == null) {
            return "GD-0000000001";
        }
        else {
            String[] part = maxCode.split("(?<=\\D)(?=\\d)");
            newCode = part[0] + (Integer.parseInt(part[1]) + 1);
        }

        return newCode;
    }

    /**
     * Xử lý send money
     * NVTOAN
     * @return
     */
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseResult sendMoney(SendMoneyRequestPayload sendMoneyRequestPayload) throws Exception {
        // Lấy ra send và receive account
        PaymentAccount sendAccount = bankAccountService.getPaymentAccountByCustomerID(personService.getAuthPerson().getID());
        PaymentAccount receiveAccount = (PaymentAccount) bankAccountService.getBankAccountByAccountNumber(sendMoneyRequestPayload.getBankAccountNumber());
        ResponseResult responseResult = new ResponseResult();

        // Nếu không đủ tiền trong tài khoản
        if(sendAccount.getAmount() - sendMoneyRequestPayload.getAmount() < 50000) {
            responseResult.setResponseStatus(ResponseStatus.Invalid);
            responseResult.setMessage("Giao dịch không thành công");
            responseResult.setData("Số dư không đủ để thực hiện giao dịch này");
        }
        else {
            sendAccount.setAmount(sendAccount.getAmount() - sendMoneyRequestPayload.getAmount());
            receiveAccount.setAmount(receiveAccount.getAmount() + sendMoneyRequestPayload.getAmount());
            Transaction transaction = new Transaction(sendAccount,
                    receiveAccount,
                    this.getNewTransactionCode(),
                    sendMoneyRequestPayload.getAmount(),
                    new Date(),  sendMoneyRequestPayload.getDescription(), TransactionStatus.Completed);

            if(bankAccountRepository.save(sendAccount) == null) {
                throw new Exception("Không thể lưu bank account send");
            };
            if(bankAccountRepository.save(receiveAccount) == null) {
                throw new Exception("Không thể lưu bank account receive");
            };
            if(transactionRepository.save(transaction) == null) {
                throw new Exception("Không thể lưu transaction");
            };

            responseResult.setResponseStatus(ResponseStatus.Success);
            responseResult.setMessage("Giao dịch thành công");
        }
        return responseResult;
    }

    /**
     * Xử lý send money
     * NVTOAN
     * @return
     */
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseResult depositMoney(double amount) throws Exception {
        // Lấy ra send và receive account
        PaymentAccount paymentAccount = bankAccountService.getPaymentAccountByCustomerID(personService.getAuthPerson().getID());
        ResponseResult responseResult = new ResponseResult();

        paymentAccount.setAmount(paymentAccount.getAmount() + amount);

        if(bankAccountRepository.save(paymentAccount) == null) {
            throw new Exception("Không thể lưu bank account send");
        };

        responseResult.setResponseStatus(ResponseStatus.Success);
        responseResult.setMessage("Giao dịch thành công");

        return responseResult;
    }

    /**
     * Xử lý trả nợ credit card
     * NVTOAN
     * @return
     */
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseResult payCreditCard(double amount) throws Exception {
        // Lấy ra send và receive account
        PaymentAccount paymentAccount = bankAccountService.getPaymentAccountByCustomerID(personService.getAuthPerson().getID());
        CreditAccount creditAccount = bankAccountService.getCreditAccountByCustomerID(personService.getAuthPerson().getID());
        ResponseResult responseResult = new ResponseResult();
        double realAmount = amount;

        if(creditAccount == null) {
            responseResult.setResponseStatus(ResponseStatus.Invalid);
            responseResult.setMessage("Giao dịch không thành công");
            responseResult.setData("Khách hàng không có tài khoản credit");

            return  responseResult;
        }

        // Nếu không đủ tiền trong tài khoản
        if(paymentAccount.getAmount() - amount < 50000) {
            responseResult.setResponseStatus(ResponseStatus.Invalid);
            responseResult.setMessage("Giao dịch không thành công");
            responseResult.setData("Số dư không đủ để thực hiện giao dịch này");

            return responseResult;
        }

        if(amount > creditAccount.getDebtAmount()) {
            realAmount = creditAccount.getDebtAmount();
            paymentAccount.setAmount(paymentAccount.getAmount() - creditAccount.getDebtAmount());
            creditAccount.setDebtAmount(0);
        }
        else  {
            paymentAccount.setAmount(paymentAccount.getAmount() - amount);
            creditAccount.setDebtAmount(creditAccount.getDebtAmount() - amount);
        }

        Transaction transaction = new Transaction(paymentAccount,
                creditAccount,
                this.getNewTransactionCode(),
                amount,
                new Date(),  "Thanh toán thẻ credit", TransactionStatus.Completed);

        if(bankAccountRepository.save(paymentAccount) == null) {
            throw new Exception("Không thể lưu bank account send");
        };
        if(bankAccountRepository.save(creditAccount) == null) {
            throw new Exception("Không thể lưu bank account receive");
        };
        if(transactionRepository.save(transaction) == null) {
            throw new Exception("Không thể lưu transaction");
        };

        responseResult.setData(realAmount);
        responseResult.setResponseStatus(ResponseStatus.Success);
        responseResult.setMessage("Giao dịch thành công");

        return responseResult;
    }


}

//    Transaction t1 = new Transaction(bankAccountRepository.getById("06ac9ed2-977d-4703-aa18-7ac52ff8032d"),
//                bankAccountRepository.getById("040032a1-d707-4e9e-8001-f7fc65976aee"),
//                "GD-1234567894", 300000, new Date(), "Chuyen khoan 300k",
//                TransactionStatus.Completed);
