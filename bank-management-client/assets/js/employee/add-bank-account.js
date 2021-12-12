$(document).ready(() => {
    
    const idFormCreate = '#create-bank-account-form'

    $(idFormCreate).validate({
        rules: {
            customerCode: {
                required: true,
                notBlank: true,
                maxlength: 30,
                customerCode: true
            },
    
            cvv: {
                required: true,
                notBlank: true,
                maxlength: 10,
            },

            expireDate: {
                required: true,
                notBlank: true,
                greaterThanCurrentDate: true
            },           
        },
    
        messages: {
            customerCode: {
                required: "CustomerCode không được để trống",
                notBlank: "CustomerCode không được để trống",
                maxlength: "CustomerCode tối đa 30 ký tự",
                customerCode: "CustomerCode không đúng định dạng",
            },
    
            cvv: {
                required: "CVV không được để trống",
                notBlank: "CVV không được để trống",
                maxlength: "CVV tối đa 10 ký tự",
            },

            expireDate: {
                required: "ExpireDate không được để trống",
                notBlank: "ExpireDate không được để trống",
                greaterThanCurrentDate: "ExpireDate phải lớn hơn ngày hiện tại"
            },    
        },
    
        submitHandler: form => {
            // Lấy dữ liệu
            const data = Form.getData(idFormCreate)
            data.expireDate = DateUtils.convertDate(data.expireDate, 2)

            ApiClient.post('/bank_account', data)
            .then(resp => {
                Notify.showSuccess(resp.data.message)
                window.location.replace(`${window.location.origin}/view/employee/bank-accounts-list.html`)        
            })
            .catch(err => {
                Notify.showError(err.response.data.message)
            })
            return false;
        }
    })   
})