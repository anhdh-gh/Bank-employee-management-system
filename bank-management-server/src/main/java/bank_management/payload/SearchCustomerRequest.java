package bank_management.payload;

import bank_management.enumeration.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCustomerRequest {
    private String customerCode;
    private String customerName;
    private String gender;
}
