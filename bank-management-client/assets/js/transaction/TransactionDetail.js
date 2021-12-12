// Popup detail của transaction
class TransactionDetail {

    constructor(detailId){
        let me = this;

        // Lưu lại grid
        me.detail = $(detailId);

       
    }

    
    /**
     * Hàm khởi tạo detail transaction
     * @param {*} param 
     */
    initDetail(record) {
        console.log(record)
    }
}