// Các hàm dùng chung toàn chương trình
var CommonFn = CommonFn || {};

/**
 * Hàm format số tiền
 * NVTOAN
 * @param {Hàm} money 
 * @returns 
 */
CommonFn.formatMoney = money => {
    if(money && !isNaN(money)){
        return money.toString().replace(/(\d)(?=(\d{3})+(?:\.\d+)?$)/g, "$1.");
    }else{
        return money;
    }
}

/**
 * Hàm format ngày tháng 
 * NVTOAN
 * @param {*} dateSrc 
 * @returns 
 */
CommonFn.formatDate = dateSrc => {
    let date = new Date(dateSrc),
        year = date.getFullYear().toString(),
        month = (date.getMonth() + 1).toString().padStart(2, '0'),
        day = date.getDate().toString().padStart(2, '0');

    return `${day}/${month}/${year}`;
}

/**
 * Hàm kiểm tra có phải định dạng ngày tháng hay không
 * NVTOAN
 * @param {*} date 
 * @returns 
 */
CommonFn.isDateFormat = (date) => {
    let regex = new RegExp("([0-9]{4}[-](0[1-9]|1[0-2])[-]([0-2]{1}[0-9]{1}|3[0-1]{1})|([0-2]{1}[0-9]{1}|3[0-1]{1})[-](0[1-9]|1[0-2])[-][0-9]{4})");
    
    return regex.test(date);
}

/**
 * Hàm format ngày tháng 
 * NVTOAN
 * @param {*} dateSrc 
 * @returns 
 */
CommonFn.convertDate = dateSrc => {
    let date = new Date(dateSrc),
        year = date.getFullYear().toString(),
        month = (date.getMonth() + 1).toString().padStart(2, '0'),
        day = date.getDate().toString().padStart(2, '0');

    return `${year}-${month}-${day}`;
}

/**
 * Lấy giá trị enum từ data truyền vào
 * NVTOAN
 * @param {*} data 
 * @param {*} enumName 
 * @returns 
 */
CommonFn.getValueEnum = (data, enumName) => {
   let enumeration = Enumeration[enumName],
       resource = Resource[enumName];

    for(propName in enumeration){
        if(enumeration[propName] == data){
            data = resource[propName];
        }
    }

    return data;
}

/**
 * Hàm call api lên lấy hoặc truyền dữ liệu lên server 
 * NVTOAN
 * @param {*} url 
 * @param {*} method 
 * @param {*} data 
 * @param {*} fnCallBack 
 * @param {*} async 
 */
CommonFn.Ajax = (url, method, data, fnCallBack, async = true) => {
    $.ajax({
        url: url,
        method: method,
        async: async,
        data: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json"
        },
        crossDomain: true,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (response) {
            fnCallBack(response);
        },
        error: function (errorMsg) {
            console.log(errorMsg.responseText);
        }
    })
}

