package bank_management.payload;

import bank_management.enumeration.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchEmployeeRequest {
    private String employeeCode;
    private String employeeName;
    private String position;
}
