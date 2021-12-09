package bank_management.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "Current password không được để trống")
    @Size(min = 8, message = "Current password tối thiểu 8 ký tự")
    private String currentPassword;

    @NotBlank(message = "New password không được để trống")
    @Size(min = 8, message = "New password tối thiểu 8 ký tự")
    private String newPassword;

    @NotBlank(message = "Confirm new password không được để trống")
    @Size(min = 8, message = "Confirm new password tối thiểu 8 ký tự")
    private String confirmNewPassword;
}
