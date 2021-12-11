function convertDate(dateInput, option){
    // Nếu convert Datetime (lấy từ server) sang date
    if(option == 1){
        dateInput = new Date(dateInput);
        dateInput = dateInput.toLocaleDateString();
    }
    return dateInput.split("-").reverse().join("-");
}

// let dateTest = "2000-08-05";
// console.log(convertDate(dateTest, 2)); 
// => 05/08/2000

// let dateTimeTest = "2011-07-14 11:23:00";
// console.log(convertDate(dateTimeTest, 1));
// => 14-07-2014

