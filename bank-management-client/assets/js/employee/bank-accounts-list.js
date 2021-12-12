const idTableBankAccount = '#table-bank-account'
const idFomSearchBankAccount = '#form-search-bank-account'
const idBtSearch = '#bt-search'
const idBtDelete = '#bt-delete'

const deleteBankAccount = idBankAccount => {
    $(idBtDelete).on('click', e => {
        e.preventDefault();
        
        ApiClient.delete(`/bank_account/${idBankAccount}`)
        .then(resp => {
            Notify.showSuccess(resp.data.message)
            window.location.replace(`${window.location.origin}/view/employee/bank-accounts-list.html`) 
        })
        .catch(err => {
            Notify.showError(err.response.data.message)
        })
    })
}

$(document).ready(function() {

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
                    <a href="bank-account-profile.html?idBankAccount=${item.idBankAccount}" class="btn btn-info btn-sm mb-1">
                        <i class="far fa-eye"></i>
                    </a>
                    <button data-toggle="modal" data-target="#delete_employee" onclick="deleteBankAccount('${item.idBankAccount}')"
                        class="btn btn-danger btn-sm mb-1 bt-delete-ba">
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
                    bFilter: false
                });
            }
        })(jQuery);
    }

    const convertResponseToData = response => {
        return response.map((item, index) => {
            return {
                idBankAccount: item.id,
                customerCode: item.customer.customerCode,
                accountCode: item.accountCode,
                type: item.type,
                branch: item.branch,
                createDate: DateUtils.convertDate(item.createDate, 1),
                expireDate: DateUtils.convertDate(item.expireDate, 1),
                memberLevel: item.memberLevel,
                createBy: item.employee.employeeCode              
            }
        })
    } 

    // Hiển thị List bank account
    let urlApi = '/bank_account'

    // Get các tham số trên đường dẫn
    const paramsSearch = getUrlVars();
    if(paramsSearch) {
        if(paramsSearch.type) {
            $('#select2-type-container').text(paramsSearch.type)
            if(paramsSearch.type === 'All') 
                paramsSearch.type = ''
        }

        urlApi += '/search?' + encodeQueryData(paramsSearch)
        Object.keys(paramsSearch).forEach(att => {
            $(`#${att}`).val(paramsSearch[att])
        })
    }

    ApiClient.get(urlApi)
    .then(resp => {
        const data = convertResponseToData(resp.data.data)
        renderData(data);
        paging();
    })
    .catch(err => {
        Notify.showError(err.response.data.message)
    })
})
