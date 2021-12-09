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
        console.log(resp.data);
      })
      .catch((err) => {
        Notify.showError(err.response.data.message);
      });

    // Không cho tự submit form
    return false;
  },
});
