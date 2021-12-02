// Base grid dùng chung
class BaseGrid{
    constructor(gridId){
        let me = this;

        // Lưu lại grid
        me.grid = $(gridId);

        // Biến lưu form detail thêm sửa
        me.formDetail = null;

        // Lấy dữ liệu từ server
        me.getDataServer();

        // Khởi tạo các sự kiện
        me.initEvents();
    }

    /**
     * 
     * @param {*} formId 
     */
    initFormDetail(formId){
        let me = this;

        me.formDetail = new BaseForm(formId);
    }

    /**
     * 
     */
    initEvents(){
        let me = this;

        // Khởi tạo sự kiện cho toolbar
        me.initEventToolbar();
       
        // Khởi tạo sự kiện click vào row sẽ đổi background
        me.eventClickRow();
    }

    /**
     * 
     */
    initEventToolbar(){
        let me = this,
            toolbarId = me.grid.attr("Toolbar"),
            toolbar = $(`#${toolbarId}`);

        if(toolbar.length > 0){
            toolbar.find(".buttonItem").on("click", function(){
                let commandType = $(this).attr("CommandType"),
                    fireEvent = null;

                switch(commandType){
                    case Resource.CommandType.Add:  // Thêm mới
                        fireEvent = me.add;
                        break;
                    case Resource.CommandType.Edit: // Sửa
                        fireEvent = me.edit;
                        break;  
                    case Resource.CommandType.Delete: // Xóa
                        fireEvent = me.delete;
                        break;
                    case Resource.CommandType.Refresh: // refresh
                        fireEvent = me.refresh;
                        break;
                }

                // // Kiểm tra nếu có hàm thì gọi
                if(typeof(fireEvent) == 'function'){
                    fireEvent = fireEvent.bind(me);

                    fireEvent();
                }
            });
        }
    }

    /**
     * 
     */
    eventClickRow(){
        let me = this;

        // Khởi tạo sự kiện click vào row sẽ đổi background
        me.grid.on("click", "tbody tr", function(){
            me.grid.find(".selected-row").removeClass("selected-row");

            $(this).addClass("selected-row");
        });
    }

    /**
     * 
     */
    getDataServer(){
        let me = this,
            url = me.grid.attr("Url"),
            urlFull = `${Constant.UrlPrefix}${url}`;

        // Lên server lấy dữ liệu
        CommonFn.Ajax(urlFull, Resource.Method.Get, {}, function(response){
            if(response){
                me.loadData(response);
            }else{
                console.log("Có lỗi lấy dữ liệu từ server");
            }
        });
    }

     /**
     * Hàm dùng để render dữ liệu
     */
    loadData(data){
        let me = this,
            table = $("<table></table>"),
            thead = me.renderHeader(),
            tbody = me.renderTbody(data);

        table.append(thead);
        table.append(tbody);

        me.grid.find("table").remove();
        me.grid.append(table);

        // Làm một số thứ sau khi binding xong
        me.afterBinding();
    }

     /**
     * Xử lý một số thứ sau khi binding xong
     */
    afterBinding(){
        let me = this;

        // Lấy Id để phân biệt các bản ghi
        me.ItemId = me.grid.attr("ItemId");

        // Mặc định chọn dòng đầu tiên
        me.grid.find("tbody tr").eq(0).addClass("selected-row");
    }

    /**
     * Hàm dùng để render header table
     */
    renderHeader(){
        let me = this,
            thead = $("<thead></thead>"),
            row = $("<tr></tr>");

        // Dyệt các cột để build header
        me.grid.find(".col").each(function(){
            let text = $(this).text(),
                th = $("<th></th>");

            th.text(text);
            row.append(th);
        });

        // Append row vào header
        thead.append(row);

        return thead;
    }

    /**
     * Hàm dùng để render ra tbody
     * @param {} data 
     */
    renderTbody(data){
        let me = this,
            tbody = $("<tbody></tbody>");

        if(data && data.length > 0){
            data.filter(function(item){
                let row = $("<tr></tr>");

                // Duyệt config từng cột
                me.grid.find(".col").each(function(){
                    let column = $(this),
                        fieldName = column.attr("FieldName"),
                        dataType = column.attr("DataType"),
                        data = item[fieldName],
                        cell = $("<td></td>"),
                        className = me.getClassFormat(dataType),
                        value = me.getValue(data, dataType, column);

                    cell.text(value);
                    cell.addClass(className);
                    row.append(cell);
                });

                // Lưu dữ liệu bản ghi vào tr để sau lấy ra
                row.data("value", item);

                tbody.append(row);
            });
        }

        return tbody;
    }

    // Lấy dữ liệu bản ghi được select
    getSelectedRecord(){
        let me = this,
            data = {},
            selected = me.grid.find(".selected-row");

        if(selected.length > 0){
            data = selected.eq(0).data("value");
        }

        return data;
    }

    /**
     * Hàm lấy class format cho từng kiểu dữ liệu
     * @param {Hàm} dataType 
     */
    getClassFormat(dataType){
        let me = this,
            className = "";

        switch(dataType){
            case Resource.DataTypeColumn.Number:
                className = "align-right";
                break;
            case Resource.DataTypeColumn.Date:
                className = "align-center";
                break;
        }

        return className;
    }

     /**
     * Hàm lấy dữ liệu chuẩn hóa
     * @param {Hàm} dataType 
     */
    getValue(data, dataType, column){
        let me = this;

        switch(dataType){
            case Resource.DataTypeColumn.Number:
                data = CommonFn.formatMoney(data);
                break;
            case Resource.DataTypeColumn.Date:
                data = CommonFn.formatDate(data);
                break;
            case Resource.DataTypeColumn.Enum:
                let enumName = column.attr("EnumName");

                data = CommonFn.getValueEnum(data, enumName);
                break;
        }

        return data;
    }

    /**
     * Hàm thêm dữ liệu vào grid
     */
    add(){
        let me = this,
            param = {
                Parent: me,
                FormMode: Enumeration.FormMode.Add,
                Record: {}
            };
        
        if(me.formDetail){
            me.formDetail.open(param);
        }
    }

    /**
     * Sửa dữ liệu 
     * 
     */
    edit(){
        let me = this,
            param = {
                Parent: me,
                FormMode: Enumeration.FormMode.Edit,
                Record: {...me.getSelectedRecord()},
                ItemId: me.ItemId
            };
        
        if(me.formDetail){
            me.formDetail.open(param);
        }
    }

    /**
     * Làm mới dữ liệu
     */
    refresh(){
        let me = this;

        me.getDataServer();
    }

    delete() {
        let me = this,
            param = {
                Parent: me,
                FormMode: Enumeration.FormMode.Delete,
                Record: {...me.getSelectedRecord()},
                ItemId: me.ItemId
            };
        
    }
}