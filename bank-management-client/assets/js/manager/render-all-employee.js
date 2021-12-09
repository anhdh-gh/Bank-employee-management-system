ApiClient.get("/employee/profile", {})
  .then((resp) => {
    const profile = resp.data.data.account.username;
    $("#profile").text(profile);
  })
  .catch((err) => {});

ApiClient.get("/employee", {})
  .then((resp) => {
    const data = resp.data.data;
    console.log(data);
  })
  .catch((err) => {
    Notify.showError(err.response.data.message);
  });
