const employeeGrid = "#employee-grid";
//render data
function renderData(data) {
  let html = "";
  data.forEach((employee) => {
    let firstLetter = employee.fullname.firstName.substring(0, 1);
    let employeeRow = `<tr>
                        <td>
                          <h2>
                            <a href="profile.html?id=${
                              employee.id
                            }" class="avatar text-white"
                              >${firstLetter}</a
                            ><a href="profile.html?id=${employee.id}"
                              >${
                                employee.fullname.firstName +
                                " " +
                                employee.fullname.lastName
                              } <span>${employee.position}</span></a
                            >
                          </h2>
                        </td>
                        <td>${employee.employeeCode}</td>
                        <td class="__cf_email__">
                          ${employee.email}
                        </td>
                        <td>${employee.phoneNumber}</td>
                        <td>${employee.createDate}</td>
                        <td>
                          ${employee.position}
                        </td>
                        <td class="text-right">
                          <a
                            href="edit-employee.html?id=${employee.id}"
                            class="btn btn-primary btn-sm mb-1"
                          >
                            <i class="far fa-edit"></i>
                          </a>
                          <button
                            type="submit"
                            data-toggle="modal"
                            data-target="#delete_employee"
                            class="btn btn-danger btn-sm mb-1" class="delete" onclick="fillModalDelete('${
                              employee.id
                            }')"
                          >
                            <i class="far fa-trash-alt"></i>
                          </button>
                        </td>
                      </tr>`;
    html += employeeRow;
  });
  $(employeeGrid).html(html);
}

//call api
ApiClient.get("/employee", {})
  .then((resp) => {
    let data = resp.data.data;
    renderData(data);

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

//search
const idFormSearchEmployee = "#search-form";
function handleSearch() {
  const data = Form.getData(idFormSearchEmployee);
  var employeeSearch = {
    employeeCode: data.employeeCodeSearch,
    employeeName: data.employeeNameSearch,
    position: data.positionSearch,
  };
  ApiClient.get("/employee/search", employeeSearch)
    .then((resp) => {
      const data = resp.data.data;
      if (data.length === 0) Notify.showError("Không tìm thấy employee");
      else {
        renderData(data);
      }
      (function ($) {
        "use strict";
        if ($(".datatable").length > 0) {
          $(".datatable").DataTable({
            retrieve: true,
            paging: true,
          });
        }
      })(jQuery);
    })
    .catch((err) => {
      Notify.showError(err.message);
    });
}

//fill id vao modal de xoa
function fillModalDelete(employeeID) {
  $("#delete-confirm").attr("value", employeeID);
}

//xoa
function deleteEmployee() {
  let employeeID = $("#delete-confirm").attr("value");
  console.log(employeeID);
  ApiClient.delete("/employee/" + employeeID)
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
}
