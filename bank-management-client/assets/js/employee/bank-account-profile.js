const idTableTransaction = '#table-transaction'

$(document).ready(() => {
    
    const renderDataTransaction = data => {
        $(idTableTransaction).empty()

        data.forEach(item => {
            $(idTableTransaction).append(`<tr>
                <td>${item.executeDate}</td>
                <td>${item.content}</td>
                <td>${item.status}</td>
                <td>${item.amount}</td>
            </tr>`)
        })
    }

    const params = getUrlVars();

    if(params.idBankAccount) {

        // Hiển thị thông tin customer và tài khoản ngân hàng
        ApiClient.get(`/bank_account/${params.idBankAccount}`)
        .then(resp => {
            const data = {
                username: resp.data.data.customer.account.username,
                memberLevel: resp.data.data.memberLevel,
                gender: resp.data.data.customer.gender,
                customerCode: resp.data.data.customer.customerCode,
                accountNumber: resp.data.data.accountNumber,
                type: resp.data.data.type,
                createDate: DateUtils.convertDate(resp.data.data.createDate, 1),
                expireDate: DateUtils.convertDate(resp.data.data.expireDate, 1),
                branch: resp.data.data.branch
            }
            Object.keys(data).forEach(att => $(`#${att}`).text(data[`${att}`]))
        })
        .catch(err => {
            Notify.showError(err.response.data.message)
        })

        // Hiển thị tất cả các TRANSACTIONS
        ApiClient.get(`/transaction/${params.idBankAccount}`)
        .then(resp => {
            const data = resp.data.data.map(item => ({
                executeDate: item.executeDate,
                amount: item.amount,
                content: item.content,
                status: item.status
            }))

            renderDataTransaction(data)
        })
        .catch(err => {
            Notify.showError(err.response.data.message)
        })
    }
    else Notify.showError("Không có id trong param của url")    
})