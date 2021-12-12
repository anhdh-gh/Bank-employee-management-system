package bank_management.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchSalaryRequest {
    private String position;
    private int startMonth;
    private int startYear;
    private int endMonth;
    private int endYear;
}
