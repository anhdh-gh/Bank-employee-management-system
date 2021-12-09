ApiClient.get("/employee", {})
  .then((resp) => {
    const data = resp.data;
    console.log(data);
  })
  .catch((err) => {
    Notify.showError(err.response.data.message);
  });
