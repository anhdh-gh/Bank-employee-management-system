// ========================================================= Xử lý form =========================================================
const idForgotPasswordForm = '#forgotPasswordForm'

// Validate form login (Khi bàn phím được nhấn và thả ra thì sẽ chạy phương thức này)
// https://viblo.asia/p/tim-hieu-ve-jquery-validation-phan-1-E375zEqRlGW
$(idForgotPasswordForm).validate({
    rules: {
        username: {
            required: true,
            notBlank: true,
            maxlength: 30
        },

        identityNumber: {
            required: true,
            notBlank: true,
            maxlength: 15,
            digits: true
        },

        phoneNumber: {
            required: true,
            notBlank: true,
            maxlength: 15,
            digits: true
        },

        email: {
            required: true,
            notBlank: true,
            email: true
        }
    },

    messages: {
        username: {
            required: "Username không được để trống",
            notBlank: "Username không được để trống",
            maxlength: "Username tối đa 30 ký tự"
        },

        identityNumber: {
            required: "IdentityNumber không được để trống",
            notBlank: "IdentityNumber không được để trống",
            maxlength: "IdentityNumber tối đa 15 ký tự",
            digits: "IdentityNumber chỉ chữa chữ số"
        },

        phoneNumber: {
            required: "PhoneNumber không được để trống",
            notBlank: "PhoneNumber không được để trống",
            maxlength: "PhoneNumber tối đa 15 ký tự",
            digits: "PhoneNumber chỉ chữa chữ số"
        },

        email: {
            required: "Email không được để trống",
            notBlank: "Email không được để trống",
            email: 'Email không đúng định dạng'
        }
    },

    submitHandler: form => {
        // Lấy dữ liệu
        const data = Form.getData(idForgotPasswordForm)

        ApiClient.post('/person/forgot_password', data)
            .then(resp => {
                Notify.showSuccess(`Mật khẩu mới của bạn là: ${resp.data.data}`)
            })
            .catch(err => {
                Notify.showError(err.response.data.message)
            })

        // Không cho tự submit form
        return false;
    }
})