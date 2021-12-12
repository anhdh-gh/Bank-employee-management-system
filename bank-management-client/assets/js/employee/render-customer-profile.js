// ApiClient.get("/person/info", {})
//   .then((resp) => {
//     const profile = resp.data.data.account.username;
//     $("#profile").text(profile);
//   })
//   .catch((err) => {});

  // get id
const currentURL = window.location.href;

function getUrlVars(url) {
  var vars = {};
  url.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
    vars[key] = value;
  });
  return vars;
}

const customerID = getUrlVars(currentURL)["id"];

ApiClient.get("/customer/" + customerID, {})
  .then((resp) => {
    const data = resp.data.data;
    $("#full-name").text(
      data.fullName.firstName + " " + data.fullName.lastName
    );
    // $("#position").text(data.position);
    $("#gender").text(data.gender);
    $("#customer-code").text("Customer Code: " + data.customerCode);
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
    $("#identityNumber").text(XXXXXXXX + data.createDate.substr(8));
  })
  .catch((err) => {});
