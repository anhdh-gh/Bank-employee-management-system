class TransactionAPI extends BaseAPI {
    constructor() {
        super();

        this.controller = "/transaction";
    }
    
    /**
     * Gửi tiền cho tài khoản khác
     * NVTOAN
     * @param {*} data 
     * @returns 
     */
    sendMoney(data) {
        return APIClient.post(`${this.controller}/sendMoney`, data);
    }

    /**
     * Gửi tiền cho tài khoản khác
     * NVTOAN
     * @param {*} amount 
     * @returns 
     */
     depositMoney(amount) {
        return APIClient.post(`${this.controller}/deposit/${amount}`);
    }

    /**
     * Trả tiền credit
     * NVTOAN
     * @param {*} amount 
     * @returns 
     */
     payCredit(amount) {
        return APIClient.post(`${this.controller}/payCreditCard/${amount}`);
    }

    /**
     * Hàm lấy tất cả dữ liệu theo filter
     * CreatedBy: NVTOAN
     */
     getDataFilter(data) {
        return APIClient.get(`${this.controller}/filter`, data);
    }
}