const transactionAPI = new TransactionAPI();
const personAPI = new PersonAPI();
var currentUser = personAPI.getCurrentUserInfo().then(res => {
    currentUser = res.data.data;

    let transactionGrid = new TransactionGrid('#transactionGrid');
    transactionGrid.initFormDetail("#transaction-detail");
})