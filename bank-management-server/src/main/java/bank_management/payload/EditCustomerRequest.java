package bank_management.payload;

import bank_management.entity.Account;
import bank_management.entity.Address;
import bank_management.enumeration.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditCustomerRequest {
    @NotBlank(message = "ID không được để trống")
    private String id;

    @NotBlank(message = "IdentityNumber không được để trống")
    @Pattern(regexp="^\\d+$", message = "IdentityNumber chỉ chứa chữ số")
    @Size(min = 0, max = 15, message = "IdentityNumber tối đa 15 ký tự số")
    private String identityNumber; // CMND

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "PhoneNumber không được để trống")
    @Size(min = 0, max = 15, message = "PhoneNumber tối đa 15 ký tự số")
    @Pattern(regexp="^\\d+$", message = "PhoneNumber chỉ chứa chữ số")
    private String phoneNumber;

    @NotNull(message = "Gender không được để trống")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Address address;
}
