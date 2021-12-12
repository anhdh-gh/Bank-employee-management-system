// ApiClient.get("/customer/profile", {})
//   .then((resp) => {
//     const profile = resp.data.data.account.username;
//     $("#profile").text(profile);
//   })
//   .catch((err) => {});

const customerGrid = "#customer-grid";
const idFormSearchCustomer = "#searchCustomer";
const idBtnSearch = "#btn-search-customer";

const renderData = (data) => {
  let html = "";
  data.forEach((customer) => {
    let firstLetter = customer.fullName.firstName.substring(0, 1);
    let customerRow = `<div class="col-md-4 col-sm-6 col-12 col-lg-4 col-xl-3">
            <div class="profile-widget">
              <div class="profile-img">
                <a href="customer-profile.html?id=${
                  customer.id
                }" class="avatar">${firstLetter}</a>
              </div>
              <div class="dropdown profile-action">
                <a
                  href="#"
                  class="action-icon dropdown-toggle"
                  data-toggle="dropdown"
                  aria-expanded="false"
                  ><i class="fas fa-ellipsis-v"></i
                ></a>
                <div class="dropdown-menu dropdown-menu-right">
                  <a class="dropdown-item" href="edit-customer.html?id=${
                    customer.id
                  }"
                    ><i class="fas fa-pencil-alt m-r-5"></i> Edit</a
                  >
                </div>
              </div>
              <h4 class="user-name m-t-10 m-b-0 text-ellipsis">
                <a href="profile.html?id=${customer.id}" >${
      customer.fullName.firstName + " " + customer.fullName.lastName
    }</a>
              </h4>
              <div class="small text-muted">Code: ${customer.customerCode}</div>
            </div>
          </div>`;
    html += customerRow;
  });
  $(customerGrid).html(html);
};
ApiClient.get("/customer", {})
  .then((resp) => {
    let data = resp.data.data;
    renderData(data);
  })
  .catch((err) => {
    Notify.showError(err.response.data.data.message);
  });

//fill id vao modal de xoa
function fillModalDelete(customerID) {
  $("#delete-confirm").attr("value", customerID);
}

$(idBtnSearch).click((e) => {
  e.preventDefault();
  const dataSearch = Form.getData(idFormSearchCustomer);
  // console.log(dataSearch);
  ApiClient.get("/customer/search", dataSearch)
    .then((resp) => {
      let dataResult = resp.data.data;
      if (dataResult.length === 0) Notify.showError("Không tìm thấy customer!");
      else {
        renderData(dataResult);
      }
    })
    .catch((err) => {
      Notify.showError(err.message);
    });
});

//xoa
$("#delete-confirm").on("click", () => {
  let customerID = $("#delete-confirm").attr("value");
  ApiClient.delete("/customer/" + customerID)
    .then((resp) => {
      let data = resp.data;
      console.log(data.message);
      if (data.responseStatus == "Success") {
        Notify.showSuccess(resp.data.message);
      } else {
        Notify.showError(resp.data.message);
      }
    })
    .catch((err) => {});
});
