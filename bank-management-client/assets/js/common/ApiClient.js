const END_POINT = "http://localhost:8080"

const token = Cookies.get("token") || sessionStorage.getItem('token')

const createAxios = () => {
    let axiosInstant = axios.create();
    axiosInstant.defaults.baseURL = END_POINT;
    // axiosInstant.defaults.withCredentials = true;
    axiosInstant.defaults.timeout = 20000;
    axiosInstant.defaults.headers = { "Content-Type": "application/json" };
    // axiosInstant.defaults.headers = {"access-control-allow-origin": "*"};
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
        });
};

const getAxios = createAxios();

// Chuyển 1 đối tượng thành các object query trên url: 
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