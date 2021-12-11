const customerList = "#customer-list";
ApiClient.get("/customer", {})
  .then((resp) => {
    let data = resp.data.data;
    let html = "";
    data.forEach((customer) => {
      let firstLetter = customer.fullName.firstName.substring(0, 1);
      let customerRow = `<tr>
                        <td>
                          <h2>
                            <a href="profile.html?id=${
                              customer.id
                            }" class="avatar text-white"
                              >${firstLetter}</a
                            ><a href="profile.html?id=${customer.id}"
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
                          <button
                            type="submit"
                            data-toggle="modal"
                            data-target="#delete_customer"
                            class="btn btn-danger btn-sm mb-1" class="delete" value="${
                              customer.id
                            }"
                          >
                            <i class="far fa-trash-alt"></i>
                          </button>
                        </td>
                      </tr>`;
      html += customerRow;
    });
    $(customerList).append(html);
    (function ($) {
      "use strict";
      if ($(".datatable").length > 0) {
        $(".datatable").DataTable({
          bFilter: false,
        });
      }
    })(jQuery);
  })
  .catch((err) => {
    Notify.showError(err.response.data.data.message);
  });
