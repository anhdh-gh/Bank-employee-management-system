const idTableBankAccount = '#table-bank-account'
const idFomSearchBankAccount = '#form-search-bank-account'
const idBtSearch = '#bt-search'
const idBtDelete = '#bt-delete'

const renderData = data => {
    $(idTableBankAccount).empty();
    
    data.forEach((item, index) => {
        $(idTableBankAccount).append(`<tr>
            <td>
                <h2>
                    <span class="avatar text-white">${index}</span>
                    <span>${item.customerCode}<span></span></span>
                </h2>
            </td>
            <td>${item.accountCode}</td>
            <td>${item.type}</td>
            <td>${item.branch}</td>
            <td>${item.createDate}</td>
            <td>${item.expireDate}</td>
            <td>${item.memberLevel}</td>
            <td>${item.createBy}</td>
            <td class="text-right">
                <a href="bank-account-profile.html" class="btn btn-info btn-sm mb-1">
                    <i class="far fa-eye"></i>
                </a>
                <button type="submit" data-toggle="modal" data-target="#delete_employee"
                    class="btn btn-danger btn-sm mb-1">
                    <i class="far fa-trash-alt"></i>
                </button>
            </td>
        </tr>`)        
    });
}

const paging = () => {
    (function ($) {
        "use strict";
        if ($(".datatable").length > 0) {
          $(".datatable").DataTable({
            bFilter: false,
          });
        }
    })(jQuery);
}

const convertResponseToData = response => {
    return response.map(item => ({
        customerCode: item.customer.customerCode,
        accountCode: item.accountCode,
        type: item.type,
        branch: item.branch,
        createDate: DateUtils.convertDate(item.createDate, 1),
        expireDate: DateUtils.convertDate(item.expireDate, 1),
        memberLevel: item.memberLevel,
        createBy: item.employee.employeeCode  
    }))
} 

// Hiển thị List bank account
ApiClient.get("/bank_account")
.then(resp => {
    const data = convertResponseToData(resp.data.data)
    renderData(data);
    paging();
})
.catch(err => {
    Notify.showError(err.message)
})

// Xử lý search
$(idBtSearch).click(e => {
    e.preventDefault()
    const data = Form.getData(idFomSearchBankAccount)
    ApiClient.get('/bank_account/search', data)
    .then(resp => {
        const data = convertResponseToData(resp.data.data)
        if(data.length === 0)
            Notify.showError("Không tìm thấy bank account")
        else {
            renderData(data);
            paging();
        }
    })
    .catch(err => {
        Notify.showError(err.message)
    })
})

// Xử lý xóa
// $(idBtDelete).click(e => {
//     e.preventDefault()
//     ApiClient.delete(`bank_account/${}`)
//     .then(reps => {

//     })
//     .catch(err => {
//         Notify.showError(err.message)
//     })
// })