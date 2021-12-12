const customerList = "#customer-list";
const idFormSearchCustomer = "#searchCustomer";
const idBtnSearch = "#btn-search-customer";

const renderData = (data) => {
  let html = "";
  data.forEach((customer) => {
    let firstLetter = customer.fullName.firstName.substring(0, 1);
    let customerRow = `<tr>
                      <td>
                        <h2>
                          <a href="customer-profile.html?id=${
                            customer.id
                          }" class="avatar text-white"
                            >${firstLetter}</a
                          ><a href="customer-profile.html?id=${customer.id}"
                            >${
                              customer.fullName.firstName +
                              " " +
                              customer.fullName.lastName
                            } <span>${customer.gender}</span></a
                          >
                        </h2>
                      </td>
                      <td>${customer.customerCode}</td>
                      <td class="__cf_email__">
                        ${customer.email}
                      </td>
                      <td>${customer.phoneNumber}</td>
                      <td>${DateUtils.convertDate(customer.dateOfBirth, 1)}</td>
                      <td>
                        ${
                          customer.address.houseNumber +
                          " " +
                          customer.address.district +
                          " " +
                          customer.address.city +
                          " " +
                          customer.address.country
                        }
                      </td>
                      <td class="text-right">
                        <a
                          href="edit-customer.html"
                          class="btn btn-primary btn-sm mb-1"
                        >
                          <i class="far fa-edit"></i>
                        </a>
                        <a href="customer-profile.html?id=${
                          customer.id
                        }" class="btn btn-info btn-sm mb-1">
                        <i class="far fa-eye"></i>
                        </a>
                      </td>
                    </tr>`;
    html += customerRow;
  });
  $(customerList).html(html);
};

const paging = () => {
  (function ($) {
    "use strict";
    if ($(".datatable").length > 0) {
      $(".datatable").DataTable({
        bFilter: false,
      });
    }
  })(jQuery);
};

// ApiClient.get("/customer", {})
//   .then((resp) => {
//     let data = resp.data.data;
//     renderData(data);
//     paging();
//   })
//   .catch((err) => {
//     Notify.showError(err.response.data.data.message);
//   });

// Search Customer

// Hiển thị List bank account
let urlApi = "/customer";

// Get các tham số trên đường dẫn
const paramsSearch = getUrlVars();
if (paramsSearch) {
  if (paramsSearch.type) {
    $("#select2-type-container").text(paramsSearch.type);
    if (paramsSearch.type === "All") paramsSearch.type = "";
  }

  urlApi += "/search?" + encodeQueryData(paramsSearch);
  Object.keys(paramsSearch).forEach((att) => {
    $(`#${att}`).val(paramsSearch[att]);
  });
}

ApiClient.get(urlApi)
  .then((resp) => {
    const data = resp.data.data;
    renderData(data);
    paging();
    if (data.length == 0) {
      Notify.showError("Không tìm thấy dữ liệu!");
    }
  })
  .catch((err) => {
    Notify.showError(err.response.data.message);
  });

// $(idBtnSearch).click((e) => {
//   e.preventDefault();
//   const dataSearch = Form.getData(idFormSearchCustomer);
//   // console.log(dataSearch);
//   ApiClient.get("/customer/search", dataSearch)
//     .then((resp) => {
//       let dataResult = resp.data.data;
//       if (dataResult.length === 0) Notify.showError("Không tìm thấy customer!");
//       else {
//         renderData(dataResult);
//         // paging();
//       }
//     })
//     .catch((err) => {
//       Notify.showError(err.message);
//     });
// });
