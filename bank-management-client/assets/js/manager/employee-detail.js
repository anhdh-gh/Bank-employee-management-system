// get id
const currentURL = window.location.href;
function getUrlVar(url) {
  var vars = {};
  var parts = url.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
    vars[key] = value;
  });
  return vars;
}
const employeeID = getUrlVar(currentURL)["id"];
const role = getUrlVar(currentURL)["role"];

let urlApi = "/employee/" + employeeID;

if (role == "Manager") {
  urlApi = "/manager/" + employeeID;
  $("#title-by-role").text("Manager Profile");
}

ApiClient.get(urlApi, {})
  .then((resp) => {
    const data = resp.data.data;
    $("#full-name").text(
      data.fullName.firstName + " " + data.fullName.lastName
    );
    $("#position").text(data.position);
    $("#gender").text(data.gender);
    $("#employee-code").text(data.employeeCode);
    $("#phone-number").text(data.phoneNumber);
    $("#email").text(data.email);
    $("#date-of-birth").text(DateUtils.convertDate(data.dateOfBirth, 1));
    $("#address").text(
      data.address.houseNumber +
        ", " +
        data.address.district +
        ", " +
        data.address.city +
        ", " +
        data.address.country
    );
    $("#join-date").text(DateUtils.convertDate(data.createDate, 1));
  })
  .catch((err) => {});
