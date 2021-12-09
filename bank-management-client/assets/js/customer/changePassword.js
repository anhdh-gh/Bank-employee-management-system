// ========================================================= Xử lý form =========================================================
const idChangePasswordForm = '#changePassword'

// Validate form login (Khi bàn phím được nhấn và thả ra thì sẽ chạy phương thức này)
// https://viblo.asia/p/tim-hieu-ve-jquery-validation-phan-1-E375zEqRlGW
$(idChangePasswordForm).validate({
    rules: {
        currentPassword: {
            required: true,
            notBlank: true,
            minlength: 8
        },

        newPassword: {
            required: true,
            notBlank: true,
            minlength: 8
        },

        confirmNewPassword: {
            required: true,
            notBlank: true,
            minlength: 8
        }
    },

    messages: {
        currentPassword: {
            required: "Current password không được để trống",
            notBlank: "Current password không được để trống",
            minlength: "Current password không tối thiểu 8 ký tự"
        },

        newPassword: {
            required: "New password không được để trống",
            notBlank: "New password không được để trống",
            minlength: "New password không tối thiểu 8 ký tự"
        },

        confirmNewPassword: {
            required: "Confirm new password không được để trống",
            notBlank: "Confirm new password không được để trống",
            minlength: "Confirm new password không tối thiểu 8 ký tự"
        }        
    },

    submitHandler: form => {
        // Lấy dữ liệu
        const data = Form.getData(idChangePasswordForm)

        ApiClient.post('/person/change_password', data)
            .then(resp => {
                Notify.showSuccess(resp.data.message)
                backLogin()
            })
            .catch(err => {
                Notify.showError(err.response.data.message)
            })

        // Không cho tự submit form
        return false;
    }
})