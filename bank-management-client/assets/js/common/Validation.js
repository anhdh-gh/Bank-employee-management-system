jQuery.validator.addMethod("notBlank", function (value, element) {
    return value.trim().length > 0;
}, "No space please and don't leave it empty")

jQuery.validator.addMethod("customerCode", function (value, element) {
    return value.match(/^KH-[0-9]{5,}$/)
}, "Customer code không đúng định dạng")

jQuery.validator.addMethod("greaterThanCurrentDate", function (value, element) {
    const date = value.split("-").reverse().join("-");
    const today = DateUtils.convertDate(new Date(), 1).split("-").reverse().join("-");
    return date > today
}, "Yêu cầu thời gian phải lớn hơn ngày hiện tại")