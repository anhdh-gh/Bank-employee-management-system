// get id
const currentURL = window.location.href;

function getUrlVar(url) {
  var vars = {};
  var parts = url.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
    vars[key] = value;
  });
  return vars;
}

var customerID = getUrlVar(currentURL)["id"]; // / get customer theo id
if (customerID != null) {
  ApiClient.get("/customer/" + customerID, {}) //
    .then((resp) => {
      customer = resp.data.data;
      $("#firstName").val(customer.fullName.firstName);
      $("#lastName").val(customer.fullName.lastName);
      $("#email").val(customer.email);
      $("#phoneNumber").val(customer.phoneNumber);
      $("#username").val(customer.account.username);
      $("#identityNumber").val(customer.identityNumber);
      $("#gender").val(customer.gender).change();
      $("#dateOfBirth").val(DateUtils.convertDate(customer.dateOfBirth, 1));
      $("#zipCode").val(
        customer.address.zipCode
      );
      $("#district").val(
        customer.address.district
      );
      $("#city").val(
        customer.address.city
      );
      $("#country").val(
        customer.address.country
      );
      $("#houseNumber").val(
        customer.address.houseNumber
      );
      return customer;
    })
    .catch((err) => {});
}

const idFormEditCustomer = "#editCustomer";
$(idFormEditCustomer).validate({
  rules: {
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
      identityNumber: {
        required: true,
        notBlank: true,
        digits: true,
        maxlength: 15,
      },
      phoneNumber: {
        required: true,
        notBlank: true,
        digits: true,
        maxlength: 11,
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
  },

  messages: {
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
    identityNumber: {
      required: "Identity number không được để trống",
      notBlank: "Identity number không được để trống",
      digits: "Identity number chỉ chứa kí tự số",
      maxlength: "Identity number tối đa 15 kí số",
    },
    phoneNumber: {
      required: "Phone number không được để trống",
      notBlank: "Phone number không được để trống",
      digits: "Phone number chỉ chứa kí tự số",
      maxlength: "Phone number không đúng định dạng",
    },
    dateOfBirth: {
      required: "Date of birth không được để trống",
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
      required: "Zip code không được để trống",
      notBlank: "Zip code không được để trống",
      maxlength: "Zip code tối đa 20 kí tự",
    },
  },

  submitHandler: (form) => {
    console.log("hehe");
    // Lấy dữ liệu
    const data = Form.getData(idFormEditCustomer);
    console.log(data);
    const customer = {
      account: {},
      identityNumber: data.identityNumber,
      dateOfBirth: null, // note
      email: data.email,
      phoneNumber: data.phoneNumber,
      gender: data.gender,
      customerCode: null,
      fullName: {
          firstName: data.firstName,
          lastName: data.lastName
      },
      address: {
        country: data.country,
        city: data.city,
        district: data.district,
        houseNumber: data.houseNumber,
        zipCode: data.zipCode,
      },
      id: customerID,
    };

    //call api edit
    ApiClient.put("/customer/editByEmployee", customer)
      .then((resp) => {
        console.log(resp.data.message);
        if (resp.data.responseStatus == "Success") {
          Notify.showSuccess(resp.data.message);
          window.location.replace(
            `${window.location.origin}/view/employee/customers-list.html`
          );
        } else {
          Notify.showError(resp.data.message);
        }
      })
      .catch((err) => {
        Notify.showError(err.response.data.message);
      });

    // Không cho tự submit form
    return false;
  },
});

$("#cancel").on("click", () => {
  window.location.replace(
    `${window.location.origin}/view/employee/all-customers.html`
  );
});