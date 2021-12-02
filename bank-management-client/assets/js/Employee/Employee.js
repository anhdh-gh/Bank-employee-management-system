// Trang nhân viên
class EmployeePage extends BaseGrid {
   
    constructor(gridId){
        super(gridId);
 
        this.config();
     }
 
     // Cấu hình các url
     config(){
         let me = this,
             config = {
                 urlAdd: "v1/Employees",
                 urlEdit: "v1/Employees",
                 urlDelete: "v1/Employees"
             };
 
         Object.assign(me, config);
     }

     /**
     * 
     * @param {*} formId 
     */
    initFormDetail(formId){
        let me = this;

        me.formDetail = new EmployeeDetail(formId);
    }
}

// Khởi tạo đối tượng trang nhân viên
let employeePage = new EmployeePage("#gridEmployee");

// Khởi tạo một form detail
employeePage.initFormDetail("#formEmployeeDetail");