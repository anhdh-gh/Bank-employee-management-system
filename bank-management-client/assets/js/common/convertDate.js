const DateUtils = {
  convertDate: (dateInput, option) => {
    // Nếu convert Datetime (lấy từ server) sang date
    if (option == 1) {
        var date = new Date(dateInput),
        month = ("0" + (date.getMonth() + 1)).slice(-2),
        day = ("0" + date.getDate()).slice(-2);
        return [day, month, date.getFullYear()].join("-");
    }
    return dateInput.split("-").reverse().join("-");
  },
};

// let dateTest = "2000-08-05";
// console.log(convertDate(dateTest, 2));
// => 05/08/2000

// let dateTimeTest = "2011-07-14 11:23:00";
// console.log(convertDate(dateTimeTest, 1));
// => 14-07-2014
