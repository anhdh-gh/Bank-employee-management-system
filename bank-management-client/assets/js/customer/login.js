// ========================================================= Xử lý form =========================================================
const idLoginForm = '#loginForm'

// Validate form login (Khi bàn phím được nhấn và thả ra thì sẽ chạy phương thức này)
// https://viblo.asia/p/tim-hieu-ve-jquery-validation-phan-1-E375zEqRlGW
$(idLoginForm).validate({
    rules: {
        username: {
            required: true,
            maxlength: 30
        },

        password: {
            required: true,
            minlength: 8
        }
    },

    messages: {
        username: {
            required: "Username không được để trống",
            maxlength: "Username tối đa 30 ký tự"
        },

        password: {
            required: "Password không được để trống",
            minlength: "Password tối thiểu 8 ký tự"
        }
    },

    submitHandler: form => {
        // Lấy dữ liệu
        const data = Form.getData(idLoginForm)

        ApiClient.post('/auth/login', { username: data.username, password: data.password })
            .then(resp => {
                Notify.showSuccess(resp.data.message)
                if (data.remember)
                    Cookies.set('token', resp.data.data.accessToken)
                else
                    sessionStorage.setItem('token', resp.data.data.accessToken)
                window.location.replace(`${window.location.origin}/view/customer/dashboard.html`)
            })
            .catch(err => {
                Notify.showError(err.response.data.message)
            })

        // Không cho tự submit form
        return false;
    }
})