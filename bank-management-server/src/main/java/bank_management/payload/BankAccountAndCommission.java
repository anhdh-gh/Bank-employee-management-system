package bank_management.payload;

import bank_management.dto.BankAccountDto;
import lombok.Data;

//class chứa thông tin tài khoản và hoa hồng được hưởng
@Data
public class BankAccountAndCommission {
    private BankAccountDto bankAccountDto;
    private double commission;

    public BankAccountAndCommission(BankAccountDto bankAccountDto, double commission) {
        this.bankAccountDto = bankAccountDto;
        this.commission = commission;
    }
}
