const APIClient = ApiClient;
class BaseAPI {
    constructor() {
        this.controller = null;
    }

    /**
     * Hàm lấy tất cả dữ liệu
     * CreatedBy: NVTOAN
     */
    getAll() {
        return APIClient.get(`${this.controller}`);
    }

    /**
     * Hàm lấy bản ghi theo Id
     * CreatedBy: NVTOAN
     * @param {*} id 
     */
    getById(id) {
        return APIClient.get(`${this.controller}/${id}`)
    }

    /**
     * Hàm thêm mới dữ liệu
     * CreatedBy: NVTOAN
     * @param {*} data 
     * @returns 
     */
    insert(data) {
        return APIClient.post(`${this.controller}`, data);
    }

    /**
     * Hàm sửa dữ liệu
     * CreatedBy: NVTOAN
     * @param {*} id 
     * @param {*} data 
     * @returns 
     */
    update(id, data) {
        return APIClient.put(`${this.controller}/${id}`, data);
    }

    /**
     * Hàm xóa dữ liệu
     * CreatedBy: NVTOAN
     * @param {*} id 
     * @returns 
     */
    delete(id) {
        return APIClient.delete(`${this.controller}/${id}`);
    }
    
    /**
     * Hàm xóa nhiều bản ghi
     * @param {array} listId Danh sách Id bản ghi cần xóa
     * @returns Trạng thái xóa các bản ghi
     */
    multipleDelete(listId) {
        return APIClient.delete(`${this.controller}`, {data: listId});
    }
    
    /**
     * Hàm kiểm tra xem dữ liệu có bị xung đột không để xoá
     * CreatedBy: NVTOAN
     * @param {string} id Id bản ghi cần kiểm tra
     * @returns Dữ liệu có bị xung đột không
     */
    checkConflict(id) {
        return APIClient.put(`${this.controller}/Conflict/${id}`);
    }
}
