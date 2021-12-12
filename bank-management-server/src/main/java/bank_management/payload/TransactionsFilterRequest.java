package bank_management.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsFilterRequest {
    private String customerID;
    private Date startDate;
    private Date endDate;
    private int pageNum;
    private int pageSize;
}
