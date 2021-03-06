// get id
const currentURL = window.location.href;

function getUrlVar(url) {
  var vars = {};
  var parts = url.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
    vars[key] = value;
  });
  return vars;
}

var employeeID = getUrlVar(currentURL)["id"]; // / get employee theo id
if (employeeID != null) {
  ApiClient.get("/employee/" + employeeID, {}) //
    .then((resp) => {
      employee = resp.data.data;
      $("#employeeCode").val(employee.employeeCode);
      $("#fullName").val(
        employee.fullName.firstName + " " + employee.fullName.lastName
      );
      $("#email").val(employee.email);
      $("#phoneNumber").val(employee.phoneNumber);
      $("#username").val(employee.account.username);
      $("#identityNumber").val(employee.identityNumber);
      $("#gender").val(employee.gender).change();
      $("#position").val(employee.position).change();
      $("#baseSalary").val(employee.baseSalary);
      $("#seniority").val(employee.seniority);
      $("#dateOfBirth").val(DateUtils.convertDate(employee.dateOfBirth, 1));
      $("#address").val(
        employee.address.houseNumber +
          " " +
          employee.address.district +
          " " +
          employee.address.city +
          " " +
          employee.address.country
      );
      return employee;
    })
    .catch((err) => {});
}

const idFormEditEmployee = "#editEmployee";
$(idFormEditEmployee).validate({
  rules: {
    seniority: {
      required: true,
    },
    baseSalary: {
      required: true,
    },
  },

  messages: {
    seniority: {
      required: "Seniority không được để trống",
    },
    baseSalary: {
      required: "BaseSalary không được để trống",
    },
  },

  submitHandler: (form) => {
    // Lấy dữ liệu
    const data = Form.getData(idFormEditEmployee);
    const employee = {
      account: {},
      identityNumber: null,
      dateOfBirth: null, // note
      email: null,
      phoneNumber: null,
      gender: null,
      employeeCode: null,
      role: "Employee",
      seniority: data.seniority,
      position: data.position,
      baseSalary: data.baseSalary,
      fullName: {},
      address: {},
      bankAccountList: [],
      salaryList: [],
      manager: null,
      id: null,
    };

    //call api edit
    ApiClient.put("/employee/" + employeeID, employee)
      .then((resp) => {
        console.log(resp.data.message);
        if (resp.data.responseStatus == "Success") {
          Notify.showSuccess(resp.data.message);
          window.location.replace(
            `${window.location.origin}/view/employee/all-employees.html`
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

$("#cancel").on("click", () => {
  window.location.replace(
    `${window.location.origin}/view/employee/all-employees.html`
  );
});
