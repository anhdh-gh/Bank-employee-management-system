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
        let me = this;

        me.detail.find('[FieldName]').each(function() {
            let domElement = $(this),
                fieldName = domElement.attr("FieldName"),
                value = record[fieldName];
            
                domElement.text(value);
        });
    }
}