//Form send money
const transactionAPI = new TransactionAPI();
const bankAccountAPI = new BankAccountAPI();
const personAPI = new PersonAPI();
var currentUser = personAPI.getCurrentUserInfo().then(res => {
    currentUser = res.data.data;

    let sendMoneyForm = new SendMoneyForm("#form-send-money");
});

class SendMoneyForm extends TransactionForm {
    constructor(formId) {
        super(formId);
    }

    /**
     * Hàm khởi tạo sự kiện custom
     */
    initCustomEvent() {
        let me = this;

        me.getDataWhenBlurBankNumberField();
    }

    validateCustom() {
        let me = this,
            control = me.form.find("[FieldName='bankAccountNumber']");

        return this.validateBankAccountNumber(control);
    }

    /**
     * Hàm tự lấy dữ liệu khi nhập xong bank account number
     * NVTOAN
     */
    getDataWhenBlurBankNumberField() {
        let me = this,
            control = me.form.find("[FieldName='bankAccountNumber']");
        
        control.blur(function() {
            me.validateBankAccountNumber($(this));
        });
    }

    /**
     * Validate bank account number
     * NVTOAN
     */
    validateBankAccountNumber(control) {
        let me = this,
            bankAccountNumber = control.val(),
            loader = me.form.find(".loader-container"),
            isValid = true;

            if(bankAccountNumber) {
                loader.removeClass("d-none");
                bankAccountAPI.getBankAccountByBankAccountNumber(bankAccountNumber).then(res => {
                    let bankAccount = res.data.data;

                    if(bankAccount.type == Resource.BankAccountType.Payment) {
                        me.form.find("[FieldName='customerName']").val(
                            bankAccount.customer.fullName.firstName + ' ' + bankAccount.customer.fullName.lastName
                        );
                    }
                    else {
                        isValid = false;
                        Notify.showError("Tài khoản gửi tiền phải là tài khoản payment!")
                    }

                    loader.addClass("d-none");
                })
                .catch(e => {
                    isValid = false;
                    Notify.showError("Không tìm thấy tài khoản tương ứng!")
                    loader.addClass("d-none");
                });
            }

            return isValid;
    }

    /**
     * Hàm override lại từ base để call API lưu dữ liệu
     * NVTOAN
     */
    saveData(data) {
        let me = this,
            param = {
                bankAccountNumber: data.bankAccountNumber,
                description: data.description,
                amount: data.amount
            };
            console.log(param)

        transactionAPI.sendMoney(param).then(res => {
            $(location).prop('href', 'http://stackoverflow.com');
        })
        .catch(e => {

        })
    }
}