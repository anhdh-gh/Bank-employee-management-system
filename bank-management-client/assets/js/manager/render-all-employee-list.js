const employeeGrid = "#employee-grid";
//render data
function renderData(data) {
  $(employeeGrid).empty();
  let html = "";
  data.forEach((employee) => {
    let firstLetter = employee.fullName.firstName.substring(0, 1);
    let employeeRow = `<tr>
                        <td>
                          <h2>
                            <a href="profile.html?id=${
                              employee.id
                            }" class="avatar text-white"
                              >${firstLetter}</a
                            ><a href="profile.html?id=${employee.id}"
                              >${
                                employee.fullName.firstName +
                                " " +
                                employee.fullName.lastName
                              } <span>${employee.position}</span></a
                            >
                          </h2>
                        </td>
                        <td>${employee.employeeCode}</td>
                        <td class="__cf_email__">
                          ${employee.email}
                        </td>
                        <td>${employee.phoneNumber}</td>
                        <td>${DateUtils.convertDate(
                          employee.createDate,
                          1
                        )}</td>
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

let urlApi = "/employee";
let data = {};
const paramsSearch = getUrlVars();
if (paramsSearch) {
  urlApi += "/search?" + encodeQueryData(paramsSearch);
  Object.keys(paramsSearch).forEach((att) => {
    $(`#${att}`).val(paramsSearch[att]).change();
  });

  data = {
    employeeCode: $("#employeeCodeSearch").val(),
    employeeName: $("#employeeNameSearch").val(),
    position: $("#positionSearch").val(),
  };
}

//call api
ApiClient.get(urlApi, data)
  .then((resp) => {
    let data = resp.data.data;
    renderData(data);

    paging();
  })
  .catch((err) => {
    Notify.showError(err.response.data.data.message);
  });

//fill id vao modal de xoa
function fillModalDelete(employeeID) {
  $("#delete-confirm").attr("value", employeeID);
}

//xoa
$("#delete-confirm").on("click", (e) => {
  e.preventDefault();
  let employeeID = $("#delete-confirm").attr("value");
  ApiClient.delete("/employee/" + employeeID)
    .then((resp) => {
      let data = resp.data;
      console.log(data);
      if (data.responseStatus == "Success") {
        Notify.showSuccess(resp.data.message);
      } else {
        Notify.showError(resp.data.message);
      }
      $("#delete_employee").modal("hide");
      window.location.replace(
        `${window.location.origin}/view/employee/all-employees.html`
      );
    })
    .catch((err) => {
      $("#delete_employee").modal("hide");
      Notify.showError("Không thể xóa!");
    });
});
