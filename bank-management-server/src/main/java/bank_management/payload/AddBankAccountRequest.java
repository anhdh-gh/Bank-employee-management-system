package bank_management.payload;

import bank_management.enumeration.BankAccountType;
import bank_management.enumeration.MemberLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddBankAccountRequest {

    @NotBlank(message = "CustomerCode không được để trống")
    @Size(max = 30, message = "customerCode tối đa 30 ký tự")
    @Pattern(regexp = "^KH-\\d{5}+$", message = "CustomerCode không đúng định dạng")
    private String customerCode;

    @NotNull(message = "Type không được trống!")
    @Enumerated(EnumType.STRING)
    protected BankAccountType type;

    @NotBlank(message = "Branch không được trống!")
    private String branch;

    @NotNull(message = "MemberLevel không được trống!")
    @Enumerated(EnumType.STRING)
    private MemberLevel memberLevel;

    @NotNull(message = "ExpireDate không được trống")
    private Date expireDate;

    @NotBlank(message = "CVV không được để trống")
    @Size(max = 10, message = "CVV tối đa 10 ký tự")
    private String CVV;
}
