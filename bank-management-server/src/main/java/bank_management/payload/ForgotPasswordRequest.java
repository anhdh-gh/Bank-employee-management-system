package bank_management.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordRequest {

	@NotBlank(message = "IdentityNumber không được để trống")
	@Pattern(regexp="^\\d+$", message = "IdentityNumber chỉ chứa chữ số")
	@Size(min = 0, max = 15, message = "IdentityNumber tối đa 15 ký tự số")
	private String identityNumber; // CMND

	@NotBlank(message = "Email không được để trống")
	@Email (message = "Email không đúng định dạng")
	private String email;

	@NotBlank(message = "PhoneNumber không được để trống")
	@Size(min = 0, max = 15, message = "PhoneNumber tối đa 15 ký tự số")
	@Pattern(regexp="^\\d+$", message = "PhoneNumber chỉ chứa chữ số")
	private String phoneNumber;

	@NotBlank(message = "Username không được để trống")
	@Size(max = 30, message = "Username tối đa 30 ký tự")
	private String username;
}