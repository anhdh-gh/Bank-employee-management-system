// Resource dùng chung toàn chương trình
var Resource = Resource || {};

// Các kiểu dữ liệu của column trong grid
Resource.DataTypeColumn = {
    Number: "Number",
    Date: "Date",
    Enum: "Enum"
};

// Các method khi gọi ajax
Resource.Method = {
    Get: "Get",
    Post: "Post",
    Put: "Put",
    Delete: "Delete"
}

// Các commandType của toolbar
Resource.CommandType = {
    Add: "Add",
    Edit: "Edit",
    Delete: "Delete",
}

// Các commandType của Form
Resource.CommandForm = {
    Save: "Save",
    Cancel: "Cancel"
}

Resource.BankAccountType = {
    Payment: "Payment",
    Credit: "Credit"
}

Resource.TransactionStatus = {
    InProgress: "InProgress",
    Canceled: "Canceled",
    Completed: "Completed"
}