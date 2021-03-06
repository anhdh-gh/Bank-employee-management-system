const pathname = window.location.pathname; // Returns path only (/path/example.html)
const url = window.location.href;     // Returns full URL (https://example.com/path/example.html)
const origin = window.location.origin;   // Returns base URL (https://example.com)

const urlCustomerLoginPage = '/view/customer/login.html'

const urlEmployeeLoginPage = '/view/employee/login.html'

const urlManagerLoginPage = '/view/employee/login.html'

const backLogin = () => {
    if (pathname.includes('/customer'))
        window.location.replace(urlCustomerLoginPage)
    if (pathname.includes('/employee'))
        window.location.replace(urlEmployeeLoginPage)
    if (pathname.includes('/manager'))
        window.location.replace(urlManagerLoginPage)
}

const getUrlVars = () => {
    var vars = {}, hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        if(hash[0] === undefined || hash[1] === undefined) return undefined
        vars[hash[0]] = hash[1];
    }
    return vars;
}

// =============================================================== Axios =============================================================

const END_POINT = "http://localhost:8080"

const token = Cookies.get("token") || sessionStorage.getItem('token')

const createAxios = () => {
    let axiosInstant = axios.create();
    axiosInstant.defaults.baseURL = END_POINT;
    // axiosInstant.defaults.withCredentials = true;
    axiosInstant.defaults.timeout = 20000;
    axiosInstant.defaults.headers = { "Content-Type": "application/json" };
    axiosInstant.defaults.headers = {"access-control-allow-origin": "*"};
    axiosInstant.defaults.headers = token && { Authorization: `Bearer ${token}` };
    axiosInstant.interceptors.response.use(
        (response) => {
            return response;
        },
        (error) => {
            throw error;
        }
    );
    return axiosInstant;
};

const handleResult = (api) => {
    return api
        .then((res) => {
            return Promise.resolve(res);
        })
        .catch((err) => {
            if (err.msg) return Promise.reject({ ...err });
            return Promise.reject({ ...err, msg: err });
        })
        .catch(err => {
            if(err.response.status === 403) {
                backLogin()
            }

            if (err.msg) return Promise.reject({ ...err });
            return Promise.reject({ ...err, msg: err });
        });
};

const getAxios = createAxios();

// Chuy???n 1 ?????i t?????ng th??nh c??c object query tr??n url: 
// const data = {
//   var1: 'value1',
//   var2: 'value2'
// };
// ==> 'var1=value1&var2=value2'
function encodeQueryData(data) {
    const ret = [];
    for (let d in data)
        ret.push(encodeURIComponent(d) + '=' + encodeURIComponent(data[d]));
    return ret.join('&');
}

const handleUrl = (url, query) => {
    return query ? `${url}?${encodeQueryData(query)}` : url;
};

const ApiClient = {
    get: (url, payload) => handleResult(getAxios.get(handleUrl(END_POINT + url, payload))),
    post: (url, payload) => handleResult(getAxios.post(END_POINT + url, payload)),
    put: (url, payload) => handleResult(getAxios.put(END_POINT + url, payload)),
    patch: (url, payload) => handleResult(getAxios.patch(END_POINT + url, payload)),
    delete: (url, payload) => handleResult(getAxios.delete(END_POINT + url, { data: payload })),
};