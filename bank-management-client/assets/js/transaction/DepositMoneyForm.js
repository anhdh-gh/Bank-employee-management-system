//Form send money
const transactionAPI = new TransactionAPI();
const bankAccountAPI = new BankAccountAPI();
const personAPI = new PersonAPI();
var currentUser = personAPI.getCurrentUserInfo().then(res => {
    currentUser = res.data.data;

    let depositMoney = new DepositMoneyForm("#form-deposit-money");
});


class DepositMoneyForm extends TransactionForm {
    constructor(formId) {
        super(formId);
    }

    /**
     * Hàm override lại từ base để call API lưu dữ liệu
     * NVTOAN
     */
     saveData(data) {
        let me = this;
        console.log(data)

        transactionAPI.depositMoney(data.amount).then(res => {
            console.log(res);
            window.location.replace(`${window.location.origin}/view/customer/deposit-money-success.hml`); 
        })
        .catch(e => {

        })
    }
}