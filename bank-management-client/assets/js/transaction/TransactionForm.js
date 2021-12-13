// Base Form của transaction
// NVTOAN
class TransactionForm {
    constructor(formId){
        let me = this;

        me.form = $(formId);

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

        me.initEventValidate();

        me.initCustomEvent();
    }

    /**
     * Hàm cho class con override lại để khởi tạo sự kiện
     * NVTOAN
     */
    initCustomEvent() {

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
        });
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
     * Lấy dữ liệu form
     * NVTOAN
     */
     getDataForm(){
        let me = this,
            data = {};

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
     * Hàm lưu dữ liệu
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
     * Hàm để override lại gọi API lưu dữ liệu
     * NVTOAN
     */
    saveData(data) {

    }

    /**
     * Khởi tạo các sự kiện liên quan đến validate
     */
    initEventValidate() {
        let me = this;

        me.form.find("[MustValidate='true']").each(function(){
            let control = $(this);

            
        });
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
        me.form.find("[Required='true']").each(function(){
            let value = $(this).val();

            if(!value){
                isValid = false;

                $(this).next().text("Please fill out this field!");
                $(this).next().show();
            }else{
                $(this).removeClass("control-invalid");
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

                $(this).addClass("control-invalid");
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

                $(this).addClass("control-invalid");
                $(this).attr("title", "Vui lòng nhập đúng định dạng!");
            }else{
                $(this).removeClass("control-invalid");
            }
        });

        return isValid;
    }

}