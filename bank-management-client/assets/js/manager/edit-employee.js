ApiClient.get("/person/info", {})
  .then((resp) => {
    const profile = resp.data.data.account.username;
    $("#profile").text(profile);
  })
  .catch((err) => {});
// get id
const currentURL = window.location.href;

function getUrlVars(url) {
  var vars = {};
  var parts = url.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
    vars[key] = value;
  });
  return vars;
}

var employeeID = getUrlVars(currentURL)["id"]; // / get employee theo id

ApiClient.get("/employee/" + employeeID, {}) //
  .then((resp) => {
    employee = resp.data.data;
    $("#employeeCode").val(employee.employeeCode);
    $("#fullName").val(
      employee.fullname.firstName + " " + employee.fullname.lastName
    );
    $("#email").val(employee.email);
    $("#phoneNumber").val(employee.phoneNumber);
    $("#username").val(employee.account.username);
    $("#identityNumber").val(employee.identityNumber);
    $("#gender").val(employee.gender).change();
    $("#position").val(employee.position).change();
    $("#baseSalary").val(employee.baseSalary);
    $("#seniority").val(employee.seniority);
    $("#dateOfBirth").val(employee.dateOfBirth);
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
