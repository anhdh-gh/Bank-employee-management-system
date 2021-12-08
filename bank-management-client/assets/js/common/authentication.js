const urlsPageNotAuth = [
    '/view/customer/login.html',
    '/view/customer/forgotPassword.html',

    '/view/employee/login.html',
    '/view/employee/forgotPassword.html',
]

const privateManager = [
    '/view/employee/all-employees.html',
    '/view/employee/salary.html',
    '/view/employee/salary-view.html',
    '/view/employee/add-employee.html',
    '/view/employee/edit-employee.html',
    '/view/employee/index.html',
]

const resetToken = () => {
    Cookies.remove('token')
    sessionStorage.removeItem('token')
}

if (urlsPageNotAuth.some(url => url === pathname))
    resetToken()

if (urlsPageNotAuth.every(url => url !== pathname)) {
    if(!Cookies.get("token") && !sessionStorage.getItem('token'))
        backLogin()
    else {
        ApiClient.get('/person/getRole')
        .then(resp => {
            const role = resp.data.data.ROLE

            if(role.some(i => i === 'Customer')) {
                if(!pathname.includes('/customer'))
                    backLogin()                
            }
            else {
                if(!pathname.includes('/employee')) {
                    backLogin()
                }
                else {
                    if(role.some(i => i === 'Employee')) {
                        if(privateManager.some(i => pathname.includes(i)))
                            backLogin()
                        else {
                            const sidebar = $('#sidebar li')
                            sidebar.eq(1).remove()
                            sidebar.eq(2).remove()
                            sidebar.eq(6).remove()
                        }
                    }
                }
            }   
        })
        .catch(err => {
            console.log(err)
        })
    }
}


// ========================================================= Chú ý đối với các trang cần auth =========================================================
/*
    Trong này phải xử lý cả trường hợp khi fetch api để đổ dữ liện lên
    Khi fetch mà nó trả về lỗi
        "status": 403,
        "error": "Forbidden",
        "message": "Access Denied",
    => Thì có 2 trường hợp sảy ra
    - Một là người dùng đã tự F12 và xửa/xóa token, dẫn đến token bị sai/mất
    - Hai là token của người dùng đã bị hết hạn
    => Cả 2 trường hợp này đều phải bắt người dùng login lại tức là redirect sang trang login
*/ 