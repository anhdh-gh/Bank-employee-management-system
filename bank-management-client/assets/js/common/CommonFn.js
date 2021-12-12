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
        return '$' + money.toString().replace(/(\d)(?=(\d{3})+(?:\.\d+)?$)/g, "$1.");
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
 * Lấy ra giá trị ngày
 * NVTOAN
 * @param {} dateSrc 
 * @returns 
 */
CommonFn.getDate = dateSrc => {
    let date = new Date(dateSrc);

    return date.getDate().toString().padStart(2, '0');
}

/**
 * Lấy ra giá trị năm
 * NVTOAN
 * @param {} dateSrc 
 * @returns 
 */
 CommonFn.getYear = dateSrc => {
    let date = new Date(dateSrc);

    return date.getFullYear().toString();
}

/**
 * Lấy ra giá trị tháng viết tắt
 * NVTOAN
 * @param {*} dateSrc 
 * @returns 
 */
CommonFn.getMonthNameSort = dateSrc => {
    let date = new Date(dateSrc),
        month_names_short = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

    return month_names_short[date.getMonth()];
}


/**
 * Lấy ra giá trị tháng chữ
 * NVTOAN
 * @param {*} dateSrc 
 * @returns 
 */
CommonFn.getMonthName= dateSrc => {
    let date = new Date(dateSrc),
    month_names = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];

    return month_names[date.getMonth()];
}


/**
 * Lấy ra giá trị ngày tháng năm đầy đủ
 * NVTOAN
 * @param {*} dateSrc 
 * @returns 
 */
CommonFn.getFullDate = dateSrc => {
    return CommonFn.getDate(dateSrc) + ' ' + CommonFn.getMonthName(dateSrc) + ' ' + CommonFn.getYear(dateSrc);
}


