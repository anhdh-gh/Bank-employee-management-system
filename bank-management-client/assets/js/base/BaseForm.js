class BaseForm {
    constructor(formId){
        let me = this;

        me.form = $(`${formId}`);

        // Khởi tạo các sự kiện
        me.initEvents();   
    }

    /**
     * Khởi tạo sự kiện
     * NVTOAN
     */
    initEvents(){
        let me = this;

        me.initEventButtonClick();
    }

    initEventButtonClick(){
        let me = this;

        me.form.find(".btnForm").on("click", function(){
            let command = $(this).attr("Command");

            switch(command){
                case Resource.CommandForm.Save:
                    me.save();
                    break;
                case Resource.CommandForm.Cancel:
                    me.cancel();
                    break;
            }
        })
    }

    /**
     * Lưu dữ liệu
     * NVTOAN
     */
    save(){
        let me =this,
            isValid = me.validateForm();

        if(isValid){
            let data = me.getDataForm();

            me.saveData(data);
        }
    }

    /**
     * Lưu dữ liệu
     * NVTOAN
     */
    saveData(data){
        let me = this,
            url = me.Parent.urlAdd,
            method = Resource.Method.Post,
            urlFull = `${Constant.UrlPrefix}${url}`;
            
        // Nếu edit thì sửa lại
        if(me.FormMode == Enumeration.FormMode.Edit){
            url = `${me.Parent.urlEdit}/${data[me.ItemId]}`;
            method = Resource.Method.Put;
            urlFull = `${Constant.UrlPrefix}${url}`;
        }

        // Gọi lên server cất dữ liệu
        CommonFn.Ajax(urlFull, method, data, function(response){
            if(response){
                console.log("Cất dữ liệu thành công");

                me.cancel();
                me.Parent.getDataServer();
            }else{
                console.log("Có lỗi khi cất dữ liệu");
            }
        });
    }

    /**
     * Lấy dữ liệu form
     * NVTOAN
     */
    getDataForm(){
        let me = this,
            data = me.Record || {};

        me.form.find("[FieldName]").each(function(){
            let control = $(this),
                dataType = control.attr("DataType"),
                fieldName = control.attr("FieldName"),
                value = me.getValueControl(control, dataType);

            data[fieldName] = value;
        });

        return data;
    }

    /**
     * Lấy dữ liệu form dựa vào data type
     * NVTOAN
     */
    getValueControl(control, dataType){
        let me = this,
            value = control.val();

        switch(dataType){
            case Resource.DataTypeColumn.Date:
                value = new Date(value);
                break;
            case Resource.DataTypeColumn.Number:
                value = parseInt(value);
                break;
            case Resource.DataTypeColumn.Enum:
                value = parseInt(value);
                break;
        }

        return value;
    }

    /**
     * validate dữ liêụ
     * NVTOAN
     */
    validateForm(){
        let me = this,
            isValid = me.validateRequire();

        if(isValid){
            isValid = me.validateFieldNumber();
        }

        if(isValid){
            isValid = me.validateFieldDate();
        }

        if(isValid){
            isValid = me.validateCustom();
        }

        return isValid;
    }

    /**
     * Validate custom để cho override lại
     * NVTOAN
     * @returns 
     */
    validateCustom(){
        return true;
    }

    /**
     * Validate bắt buộc nhập
     * NVTOAN
     * @returns 
     */
    validateRequire(){
        let me = this,
            isValid = true;

        // Duyệt hết các trường require xem có trường nào bắt buộc mà ko có value ko
        me.form.find("[Require='true']").each(function(){
            let value = $(this).val();

            if(!value){
                isValid = false;

                $(this).addClass("notValidControl");
                $(this).attr("title", "Vui lòng không được để trống!");
            }else{
                $(this).removeClass("notValidControl");
            }
        });

        return isValid;
    }

    /**
     * Validate trường là số
     * NVTOAN
     * @returns 
     */
    validateFieldNumber(){
        let me = this,
            isValid = true;

        // Duyệt hết các Number
        me.form.find("[DataType='Number']").each(function(){
            let value = $(this).val();

            // is not a number
            if(isNaN(value)){
                isValid = false;

                $(this).addClass("notValidControl");
                $(this).attr("title", "Vui lòng nhập đúng định dạng!");
            }else{
                $(this).removeClass("notValidControl");
            }
        });

        return isValid;
    }

    /**
     * Validate trường ngày tháng
     * NVTOAN
     * @returns 
     */
    validateFieldDate(){
        let me = this,
            isValid = true;

        // Duyệt hết các trường date
        me.form.find("[DataType='Date']").each(function(){
            let value = $(this).val();

            // is not a number
            if(!CommonFn.isDateFormat(value)){
                isValid = false;

                $(this).addClass("notValidControl");
                $(this).attr("title", "Vui lòng nhập đúng định dạng!");
            }else{
                $(this).removeClass("notValidControl");
            }
        });

        return isValid;
    }

    /**
     * Mở form
     * NVTOAN
     * @param {*} param 
     */
    open(param){
        let me = this;

        Object.assign(me, param);

        me.show();

        if(me.FormMode == Enumeration.FormMode.Edit){
            me.bindingData(me.Record);
        }
    }

    /**
     * bind dữ liệu lên form
     * NVTOAN
     * @param {*} data 
     */
    bindingData(data){
        let me = this;

        me.form.find("[FieldName]").each(function(){
            let control = $(this),
                fieldName = control.attr("FieldName"),
                dataType = control.attr("DataType"),
                value = data[fieldName];

               me.setValueControl(control, value, dataType);
        });
    }

    /**
     * Set giá trị cho field
     * NVTOAN
     * @param {*} control 
     * @param {*} value 
     * @param {*} dataType 
     */
    setValueControl(control, value, dataType){
        let me = this;

        switch(dataType){
            case Resource.DataTypeColumn.Date:
                value = CommonFn.convertDate(value);
                break;
            /// 
        }

        control.val(value);
    }

    /**
     * Show Form
     * NVTOAN
     */
    show(){
        let me = this;

        me.form.show();

        // reset dữ liệu
        me.resetForm();
    }

    /**
     * Reset lại form
     * NVTOAN
     */
    resetForm(){
        let me = this;

        // reset dữ liệu về rỗng
        me.form.find("[FieldName]").val("");

        me.form.find(".notValidControl").removeClass("notValidControl");
    }

    /**
     * Đóng form
     * NVTOAN
     */
    cancel(){
        let me = this;

        me.form.hide();
    }
}