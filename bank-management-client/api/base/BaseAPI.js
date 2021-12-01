export default class BaseAPI {
    constructor() {
        this.controller = null;
    }

    /**
     * Hàm lấy tất cả dữ liệu
     * CreatedBy: NVTOAN
     */
    getAll() {
        return $.get(`${this.controller}`);
    }

    /**
     * Hàm lấy bản ghi theo Id
     * CreatedBy: NVTOAN
     * @param {*} id 
     */
    getById(id) {
        return $.get(`${this.controller}/${id}`)
    }

    /**
     * Hàm thêm mới dữ liệu
     * CreatedBy: NVTOAN
     * @param {*} data 
     * @returns 
     */
    insert(data) {
        return $.post(`${this.controller}`, data);
    }

    /**
     * Hàm sửa dữ liệu
     * CreatedBy: NVTOAN
     * @param {*} id 
     * @param {*} data 
     * @returns 
     */
    update(id, data) {
        return $.put(`${this.controller}/${id}`, data);
    }

    /**
     * Hàm xóa dữ liệu
     * CreatedBy: NVTOAN
     * @param {*} id 
     * @returns 
     */
    delete(id) {
        return $.delete(`${this.controller}/${id}`);
    }
}