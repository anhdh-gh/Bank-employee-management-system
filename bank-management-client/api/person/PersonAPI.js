class PersonAPI extends BaseAPI {
    constructor() {
        super();

        this.controller = "/person";
    }
    
    /**
     * Hàm lấy thông tin user đang đăng nhập
     * NVTOAN
     * @returns 
     */
     getCurrentUserInfo() {
        return ApiClient.get(`${this.controller}/info`);
    }
}