// ========================================================= Kiểm tra token =========================================================
if(!Cookies.get("token") && !sessionStorage.getItem('token')) { // Nếu không có token => Quay ra trang login
    window.location.replace(`${window.location.origin}/view/customer/login.html`)
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