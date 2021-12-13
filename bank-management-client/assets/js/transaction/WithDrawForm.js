//Form send money
const transactionAPI = new TransactionAPI();
const bankAccountAPI = new BankAccountAPI();
const personAPI = new PersonAPI();
var currentUser = personAPI.getCurrentUserInfo().then(res => {
    currentUser = res.data.data;

    let payCreditForm = new PayCreditForm("#form-withdraw-money");
});


class PayCreditForm extends TransactionForm {
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

        transactionAPI.payCredit(data.amount).then(res => {
            window.location.replace(`${window.location.origin}/view/customer/withdraw-money-success.html`); 
        })
        .catch(e => {

        })
    }
}