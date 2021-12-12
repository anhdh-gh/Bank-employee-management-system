package bank_management.payload;

import bank_management.entity.PaymentAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMoneyRequestPayload {
    private String bankAccountNumber;
    private double amount;
    private String description;
}
