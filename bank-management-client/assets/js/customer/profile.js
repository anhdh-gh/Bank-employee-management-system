ApiClient.get("/person/info")
  .then((resp) => {
    const data = resp.data.data;
    console.log(data);
    $("#fullName").text(
      data.info.fullName.firstName + " " + data.info.fullName.lastName
    );
    $("#fullNameHello").text(
      "Hello, " +
        data.info.fullName.firstName +
        " " +
        data.info.fullName.lastName
    );
    $("#firstNameEdit").val(data.info.fullName.firstName);
    $("#lastNameEdit").val(data.info.fullName.lastName);
    $("#customer-code").text("Customer Code: " + data.customerCode);
    $("#phoneNumber").text(data.info.phoneNumber);
    $("#email").text(data.info.email);
    $("#emailEdit").val(data.info.email);
    $("#dateOfBirth").text(DateUtils.convertDate(data.info.dateOfBirth, 1));
    $("#dateOfBirthEdit").val(DateUtils.convertDate(data.info.dateOfBirth, 1));
    //   console.log(DateUtils.convertDate(data.dateOfBirth,1));
    $("#address").text(
      data.info.address.houseNumber +
        ", " +
        data.info.address.district +
        ", " +
        data.info.address.city +
        ", " +
        data.info.address.country
    );
    $("#houseNumberEdit").val(data.info.address.houseNumber);
    $("#districtEdit").val(data.info.address.district);
    $("#cityEdit").val(data.info.address.city);
    $("#countryEdit").val(data.info.address.country);
    $("#zipCodeEdit").val(data.info.address.zipCode);
  })
  .catch((err) => {});

ApiClient.get("/bank_account/payment")
  .then((resp) => {
    const data = resp.data.data;
    console.log(data);
    $("#amount").text(data.amount);
  })
  .catch((err) => {});

// .split('').reverse().reduce((prev, next, index) => {
//     return ((index % 3) ? next : (next + ',')) + prev
// }

const idFormEditEmail = "#emailAddresses";
$(idFormEditEmail).validate({
  rules: {
    email: {
      notBlank: true,
      email: true,
    },
  },

  messages: {
    email: {
      required: "Email không được để trống",
      email: "Email không hợp lệ",
    },
  },

  submitHandler: (form) => {
    // Lấy dữ liệu
    const data = Form.getData(idFormEditEmail);
    const customer = {
      account: {},
      identityNumber: null,
      dateOfBirth: null,
      email: data.email,
      phoneNumber: null,
      gender: null,
      customerCode: null,
      fullName: {
        firstName: null,
        lastName: null,
      },
      address: {
        country: null,
        city: null,
        district: null,
        houseNumber: null,
        zipCode: null,
      },
      id: null,
    };

    //call api edit
    ApiClient.put("/customer/editEmail", customer)
      .then((resp) => {
        console.log(resp.data.message);
        if (resp.data.responseStatus == "Success") {
          Notify.showSuccess(resp.data.message);
          window.location.replace(
            `${window.location.origin}/view/customer/profile.html`
          );
        } else {
          Notify.showError(resp.data.message);
        }
      })
      .catch((err) => {
        Notify.showError("Edit không thành công");
      });

    // Không cho tự submit form
    return false;
  },
});

const idFormEditPersonal = "#personaldetails";
$(idFormEditPersonal).validate({
  rules: {
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
    const data = Form.getData(idFormEditPersonal);
    console.log(data);
    const addressEdit = {
      country: data.country,
      city: data.city,
      district: data.district,
      houseNumber: data.houseNumber,
      zipCode: data.zipCode,
    };
    console.log(addressEdit);
    //call api edit
    ApiClient.put("/customer/editAddress", addressEdit)
      .then((resp) => {
        console.log(resp.data.message);
        if (resp.data.responseStatus == "Success") {
          Notify.showSuccess(resp.data.message);
          window.location.replace(
            `${window.location.origin}/view/customer/profile.html`
          );
        } else {
          Notify.showError(resp.data.message);
        }
      })
      .catch((err) => {
        Notify.showError("Edit không thành công");
      });

    // Không cho tự submit form
    return false;
  },
});
