const pathname = window.location.pathname; // Returns path only (/path/example.html)
const url = window.location.href;     // Returns full URL (https://example.com/path/example.html)
const origin = window.location.origin;   // Returns base URL (https://example.com)

const urlCustomerLoginPage = '/view/customer/login.html'

const urlEmployeeLoginPage = '/view/employee/login.html'

const urlManagerLoginPage = ''

const urlsPageNotAuth = [
    '/view/customer/login.html',
    '/view/customer/forgotPassword.html',

    '/view/employee/login.html',
    '/view/employee/forgotPassword.html',
]

const resetToken = () => {
    Cookies.remove('token')
    sessionStorage.removeItem('token')
}

if (urlsPageNotAuth.some(url => url === pathname))
    resetToken()

if (urlsPageNotAuth.every(url => url !== pathname) && !Cookies.get("token") && !sessionStorage.getItem('token')) {
    if (pathname.includes('/customer'))
        window.location.replace(urlCustomerLoginPage)
    if (pathname.includes('/employee'))
        window.location.replace(urlEmployeeLoginPage)
    if (pathname.includes('/manager'))
        window.location.replace(urlManagerLoginPage)
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