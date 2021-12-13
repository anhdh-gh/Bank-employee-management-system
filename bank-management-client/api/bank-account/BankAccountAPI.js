class BankAccountAPI extends BaseAPI {
    constructor() {
        super();

        this.controller = "/bank_account";
    }
    
    /**
     * Hàm lấy thông tin bank account by bankAccountNumber
     * NVTOAN
     * @returns 
     */
     getBankAccountByBankAccountNumber(bankAccountNumber) {
        return ApiClient.get(`${this.controller}/account_number/${bankAccountNumber}`);
    }
}