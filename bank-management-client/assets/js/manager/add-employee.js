const idFormAddCustomer = "#addEmployee";
$(idFormAddCustomer).validate({
  rules: {
    username: {
      required: true,
      notBlank: true,
      maxlength: 30,
    },

    password: {
      required: true,
      notBlank: true,
      minlength: 8,
    },
    confirmPassword: {
      equalTo: "#password",
    },
    firstName: {
      required: true,
      notBlank: true,
      maxlength: 30,
    },
    lastName: {
      required: true,
      notBlank: true,
      maxlength: 30,
    },
    email: {
      required: true,
      notBlank: true,
      email: true,
    },
    indentityNumber: {
      required: true,
      notBlank: true,
      digits: true,
      maxlength: 15,
    },
    phoneNumber: {
      required: true,
      notBlank: true,
      digits: true,
      maxlength: 10,
      minlength: 10,
    },
    dateOfBirth: {
      required: true,
    },
    country: {
      required: true,
      notBlank: true,
      maxlength: 50,
    },
    city: {
      required: true,
      notBlank: true,
      maxlength: 50,
    },
    district: {
      required: true,
      notBlank: true,
      maxlength: 50,
    },
    houseNumber: {
      required: true,
      notBlank: true,
      maxlength: 50,
    },
    zipCode: {
      required: true,
      notBlank: true,
      maxlength: 20,
    },
  },

  messages: {
    username: {
      required: "Username không được để trống",
      notBlank: "Username không được để trống",
      maxlength: "Username tối đa 30 ký tự",
    },

    password: {
      required: "Password không được để trống",
      notBlank: "Password không được để trống",
      minlength: "Password tối thiểu 8 ký tự",
    },
    confirmPassword: {
      equalTo: "Xác nhận mật khẩu không chính xác",
    },
    firstName: {
      required: "First name không được để trống",
      notBlank: "First name không được để trống",
      maxlength: "First name tối đa 30 kí tự",
    },
    lastName: {
      required: "Last name không được để trống",
      notBlank: "Last name không được để trống",
      maxlength: "Last name tối đa 30 kí tự",
    },
    email: {
      required: "Email không được để trống",
      notBlank: "Email không được để trống",
      email: "Email không hợp lệ",
    },
    indentityNumber: {
      required: "Identity number không được để trống",
      notBlank: "Identity number không được để trống",
      digits: "Identity number chỉ chứa kí tự số",
      maxlength: "Identity number tối đa 15 kí số",
    },
    phoneNumber: {
      required: "Phone number không được để trống",
      notBlank: "Phone number không được để trống",
      digits: "Phone number chỉ chứa kí tự số",
      maxlength: "Phone number chứa đúng 10 kí số",
    },
    dateOfBirth: {
      required: "date of birth không được để trống",
    },
    country: {
      required: "Country không được để trống",
      notBlank: "Country không được để trống",
      maxlength: "Country tối đa 50 kí tự",
    },
    city: {
      required: "City không được để trống",
      notBlank: "City không được để trống",
      maxlength: "City tối đa 50 kí tự",
    },
    district: {
      required: "District không được để trống",
      notBlank: "District không được để trống",
      maxlength: "District tối đa 50 kí tự",
    },
    houseNumber: {
      required: "Number house không được để trống",
      notBlank: "Number house không được để trống",
      maxlength: "Number house tối đa 50 kí tự",
    },
    zipCode: {
      required: "zipCode không được để trống",
      notBlank: "zipCode không được để trống",
      maxlength: "zipCode tối đa 20 kí tự",
    },
  },

  submitHandler: (form) => {
    // Lấy dữ liệu
    const data = Form.getData(idFormAddCustomer);
    console.log(data);
    const employee = {
      account: {
        username: data.username,
        password: data.password,
        id: null,
      },
      identityNumber: data.identityNumber,
      dateOfBirth: "2000-12-12", // note
      email: data.email,
      phoneNumber: data.phoneNumber,
      gender: data.gender,
      employeeCode: null,
      role: "Employee",
      seniority: 0,
      position: data.position,
      baseSalary: 0,
      fullname: {
        firstName: data.firstName,
        lastName: data.lastName,
      },
      address: {
        country: data.country,
        city: data.city,
        district: data.district,
        houseNumber: data.houseNumber,
        zipCode: data.zipCode,
      },
      bankAccountList: [],
      salaryList: [],
      manager: null,
      id: null,
    };

    ApiClient.post("/employee", employee)
      .then((resp) => {
        Notify.showSuccess(resp.message);
        if (resp.responseStatus == "Success") {
          window.location.replace(
            `${window.location.origin}/view/employee/all-employee.html`
          );
        }
        console.log(resp.data);
      })
      .catch((err) => {
        Notify.showError(err.response.data.message);
      });

    // Không cho tự submit form
    return false;
  },
});
