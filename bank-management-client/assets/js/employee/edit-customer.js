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
      required: "First name kh??ng ???????c ????? tr???ng",
      notBlank: "First name kh??ng ???????c ????? tr???ng",
      maxlength: "First name t???i ??a 30 k?? t???",
    },
    lastName: {
      required: "Last name kh??ng ???????c ????? tr???ng",
      notBlank: "Last name kh??ng ???????c ????? tr???ng",
      maxlength: "Last name t???i ??a 30 k?? t???",
    },
    email: {
      required: "Email kh??ng ???????c ????? tr???ng",
      notBlank: "Email kh??ng ???????c ????? tr???ng",
      email: "Email kh??ng h???p l???",
    },
    identityNumber: {
      required: "Identity number kh??ng ???????c ????? tr???ng",
      notBlank: "Identity number kh??ng ???????c ????? tr???ng",
      digits: "Identity number ch??? ch???a k?? t??? s???",
      maxlength: "Identity number t???i ??a 15 k?? s???",
    },
    phoneNumber: {
      required: "Phone number kh??ng ???????c ????? tr???ng",
      notBlank: "Phone number kh??ng ???????c ????? tr???ng",
      digits: "Phone number ch??? ch???a k?? t??? s???",
      maxlength: "Phone number kh??ng ????ng ?????nh d???ng",
    },
    dateOfBirth: {
      required: "Date of birth kh??ng ???????c ????? tr???ng",
    },
    country: {
      required: "Country kh??ng ???????c ????? tr???ng",
      notBlank: "Country kh??ng ???????c ????? tr???ng",
      maxlength: "Country t???i ??a 50 k?? t???",
    },
    city: {
      required: "City kh??ng ???????c ????? tr???ng",
      notBlank: "City kh??ng ???????c ????? tr???ng",
      maxlength: "City t???i ??a 50 k?? t???",
    },
    district: {
      required: "District kh??ng ???????c ????? tr???ng",
      notBlank: "District kh??ng ???????c ????? tr???ng",
      maxlength: "District t???i ??a 50 k?? t???",
    },
    houseNumber: {
      required: "Number house kh??ng ???????c ????? tr???ng",
      notBlank: "Number house kh??ng ???????c ????? tr???ng",
      maxlength: "Number house t???i ??a 50 k?? t???",
    },
    zipCode: {
      required: "Zip code kh??ng ???????c ????? tr???ng",
      notBlank: "Zip code kh??ng ???????c ????? tr???ng",
      maxlength: "Zip code t???i ??a 20 k?? t???",
    },
  },

  submitHandler: (form) => {
    console.log("hehe");
    // L???y d??? li???u
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

    // Kh??ng cho t??? submit form
    return false;
  },
});

$("#cancel").on("click", () => {
  window.location.replace(
    `${window.location.origin}/view/employee/all-customers.html`
  );
});