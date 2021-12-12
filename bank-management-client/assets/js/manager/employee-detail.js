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

ApiClient.get("/employee/" + employeeID, {})
  .then((resp) => {
    const data = resp.data.data;
    $("#full-name").text(
      data.fullname.firstName + " " + data.fullname.lastName
    );
    $("#position").text(data.position);
    $("#gender").text(data.gender);
    $("#employee-code").text(data.employeeCode);
    $("#phone-number").text(data.phoneNumber);
    $("#email").text(data.email);
    $("#date-of-birth").text(data.dateOfBirth);
    $("#address").text(
      data.address.houseNumber +
        ", " +
        data.address.district +
        ", " +
        data.address.city +
        ", " +
        data.address.country
    );
    $("#join-date").text(data.createDate);
  })
  .catch((err) => {});
