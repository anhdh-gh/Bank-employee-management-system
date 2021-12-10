package bank_management.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchBankAccountRequest {
    private String accountCode;
    private String customerCode;
    private String type;
}
