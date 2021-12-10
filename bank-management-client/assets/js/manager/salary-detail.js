ApiClient.get("/manager/profile", {})
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

const salaryID = getUrlVars(currentURL)["id"];
ApiClient.get("/salary/" + salaryID, {})
  .then((resp) => {
    data = resp.data.data;
  })
  .catch((err) => {});
