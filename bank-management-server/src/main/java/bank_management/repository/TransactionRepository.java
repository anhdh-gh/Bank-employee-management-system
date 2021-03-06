package bank_management.repository;

import bank_management.entity.BankAccount;
import bank_management.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String>{
    @Query( nativeQuery = true, value = "SELECT t.* FROM Transaction t WHERE bankAccountReceiveID = :BankAccountReceiveID ORDER BY t.createDate LIMIT 1")
    Transaction getAmountOfFirstTransactionByPaymentAccount(@Param(value = "BankAccountReceiveID") String paymentAccountID);

    /**
     * Lấy tất cả transaction theo bankAccountID
     * NVTOAN
     * @return
     */
    @Query("SELECT t FROM Transaction t WHERE t.bankAccountReceive.ID = ?1 OR t.bankAccountSent.ID = ?1")
    List<Transaction> getTransactionsByBankAccountID(String bankAccountID);

    /**
     * Lấy tất cả transaction của customer đang đăng nhập
     * NVTOAN
     * @return
     */
    @Query(value = "CALL Proc_GetTransactionsByCustomerID(:CustomerID);", nativeQuery = true)
    List<Transaction> getTransactionsByCustomerID(@Param("CustomerID") String CustomerID);

    /**
     * Lấy tất cả transaction theo filter
     * NVTOAN
     * @return
     */
    @Query(value = "CALL Proc_GetTransactionsFilter(:CustomerID, :StartDate, :EndDate, :PageNum, :PageSize);", nativeQuery = true)
    List<Transaction> getTransactionsFilter(@Param("CustomerID") String customerID,
                                            @Param("StartDate") Date startDate,
                                            @Param("EndDate") Date endDate,
                                            @Param("PageNum") int pageNum,
                                            @Param("PageSize") int pageSize);

    /**
     * Lấy transaction code lớn nhất
     * NVTOAN
     * @return
     */
    @Query("SELECT MAX(t.transactionCode) FROM Transaction t")
    String getMaxTransactionCode();
}