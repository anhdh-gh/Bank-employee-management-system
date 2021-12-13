//JS transaction chung
const transactionAPI = new TransactionAPI();
const personAPI = new PersonAPI();
var currentUser = personAPI.getCurrentUserInfo().then(res => {
    currentUser = res.data.data;

    let transactionGrid = new TransactionGrid('#transactionGrid');
    transactionGrid.initFormDetail("#transaction-detail");
});
// Trang Transaction
class TransactionGrid {

    constructor(gridId){
        let me = this;

        // Lưu lại grid
        me.grid = $(gridId);

        // Biến lưu form detail
        me.formDetail = null;

        // Biến lưu dữ liệu grid
        me.cacheDataGrid = [];

        me.dateRange = $("#dateRange");

        // Lấy dữ liệu từ server
        me.getDataServer();
        me.getDateRange();

        // Khởi tạo sự kiện
        me.initEvent();
    }

    /**
     * Hàm khởi tạo form detail
     */
    initFormDetail(formID) {
        let me = this;

        me.formDetail = new TransactionDetail(formID);
    }

    /**
     * Khởi tạo sự kiện grid
     */
    initEvent() {   
        let me = this;
        
        me.eventClickRow();
    }

    /***
     * Sự kiện click row
     */
    eventClickRow() {
        let me = this;

        me.grid.on("click", "div.transaction-item", function() {
            let transactionID = $(this).find("[FieldName='transactionID']").html(),
                recordSelected = me.cacheDataGrid.find(t => t.transactionID == transactionID);

            me.formDetail.initDetail(recordSelected);
        })
    }

    getDateRange() {
        let me = this;

        console.log(me.dateRange);
    }

    /**
     * Hàm lấy dữ liệu từ server 
     */
    getDataServer() {
        let me = this;

        transactionAPI.getAll().then(res => {
                me.renderDataToGrid(res.data.data);
        });
            
    }

    /**
     * Hàm render dữ liệu lên grid
     * @param {*} data 
     */
    renderDataToGrid(data) {
        let me = this,
            row = me.grid.find(".row-transaction");
        
        // Chạy trên từng dữ liệu
        data.filter((item) => {
            let dataRender = me.transformData(item);

            me.cacheDataGrid.push(dataRender);

            row.find('[FieldName]').each(function() {
                let domElement = $(this),
                    fieldName = domElement.attr("FieldName"),
                    value = dataRender[fieldName];
                
                if(fieldName == "status") {
                    me.renderStatus(domElement, value);
                }
                else {
                    domElement.text(value);
                }
            });

            me.grid.append(row.html());
        })
    }

    /**
     * Hàm thay đổi dữ liệu để có thể bind lên grid và detail
     * @param {} data 
     */
    transformData(data) {
        let me = this,
            executeDate = data.executeDate,
            dataRender = {}; // Đối tượng data để render

        dataRender.transactionID = data.id;dataRender.transactionID = data.id;

        dataRender.date = CommonFn.getDate(executeDate);
        dataRender.monthNameSort = CommonFn.getMonthNameSort(executeDate);
        dataRender.fullDate = CommonFn.getFullDate(executeDate);

        dataRender.userNameDisplay = me.renderUserNameDisplay(data);
        dataRender.userSendName = data.bankAccountSent.customer.fullName.firstName + ' ' +
                            data.bankAccountSent.customer.fullName.lastName;
        dataRender.userReceiveName = data.bankAccountReceive.customer.fullName.firstName + ' ' +
                                    data.bankAccountReceive.customer.fullName.lastName;

        dataRender.description = data.content;
        dataRender.bankAccountType = data.bankAccountReceive.type;
        dataRender.amount = me.formatMoneyTransaction(data, dataRender.bankAccountType);  

        dataRender.status = data.status;
        dataRender.statusString = data.status == Resource.TransactionStatus.InProgress ? 'In Progress' : data.status;

        dataRender.branch = me.renderBranch(data);
        dataRender.transactionCode = data.transactionCode;
        
        if(dataRender.bankAccountType == Resource.BankAccountType.Credit) {
            dataRender.amount = '-' + dataRender.amount;
        }

        return dataRender;
    }

    renderUserNameDisplay(data) {
        let me = this;
        if(currentUser.info.id == data.bankAccountSent.customer.id) {
            return data.bankAccountReceive.customer.fullName.firstName + ' ' +
                    data.bankAccountReceive.customer.fullName.lastName;
        }
        else {
            return data.bankAccountSent.customer.fullName.firstName + ' ' +
                    data.bankAccountSent.customer.fullName.lastName;
        }
    }

    renderBranch(data) {
        let me = this;
        if(currentUser.info.id == data.bankAccountSent.customer.id) {
            return data.bankAccountReceive.branch;
        }
        else {
            return data.bankAccountSent.branch;
        }
    }
    
    /**
     * Render tiền transaction
     * @param {*} data 
     * @param {*} bankAccountType 
     */
    formatMoneyTransaction(data, bankAccountType) {
        let me = this,
            amount = data.amount,
            money = CommonFn.formatMoney(amount);

        // Nếu là người gửi hoặc là tài khoản credit
        if(bankAccountType == Resource.BankAccountType.Credit ||
            currentUser.info.id == data.bankAccountSent.customer.id) {
            money =  money;
        }
        else {
            money = '+' + money;
        }
        return money;
    }

    /**
     * Hàm render cột status
     * @param {*} domElement 
     * @param {*} value 
     */
    renderStatus(domElement, value) {
        let me = this,
            iconElement = domElement.find("i"),
            toolTip, iconClass, domElementClass;

        switch(value) {
            case Resource.TransactionStatus.Canceled:
                toolTip = "Canceled";
                iconClass = "fas fa-times-circle";
                domElementClass = "text-danger";
                break;
            case Resource.TransactionStatus.Completed:
                toolTip = "Completed";
                iconClass = "fas fa-check-circle";
                domElementClass = "text-success";
                break;
            case Resource.TransactionStatus.InProgress:
                toolTip = "In Progress";
                iconClass = "fas fa-ellipsis-h";
                domElementClass = "text-warning";
                break;
        }

        //  domElement.attr("data-original-title", toolTip);
        domElement.removeClass();
        domElement.addClass(domElementClass);
        iconElement.removeClass();
        iconElement.addClass(iconClass);
    }

    

}