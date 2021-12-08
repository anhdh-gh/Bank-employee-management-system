package bank_management.dto;

import lombok.Data;

//class chứa thông tin tài khoản và hoa hồng được hưởng
@Data
public class BankAccountAndCommission {
    private BankAccountDto bankAccountDto;
    private double commission;
    public final double COMMISSION_CREDIT = 500000.0;
    public final double COMMISSION_PAYMENT = 0.2;

    public BankAccountAndCommission(BankAccountDto bankAccountDto, double commission) {
        this.bankAccountDto = bankAccountDto;
        this.commission = commission;
    }
}
