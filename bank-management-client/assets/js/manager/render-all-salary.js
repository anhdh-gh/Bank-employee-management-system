const idSalaryComponent = "#salary-component";
//render data
function renderData(data) {
  $(idSalaryComponent).empty();
  let html = "";
  data.forEach((salary, index) => {
    let firstLetter = salary.employee.fullName.firstName.substring(0, 1);

    let salaryRow = `<tr>
                        <td>${index + 1}</td>
                        <td>${salary.month + "/" + salary.year}</td>
                        <td>
                          <h2>
                            <a href="profile.html?id=${
                              salary.employee.id
                            }" class="avatar text-white"
                              >${firstLetter}</a
                            ><a href="profile.html?id=${salary.employee.id}"
                              >${
                                salary.employee.fullName.firstName +
                                " " +
                                salary.employee.fullName.lastName
                              } <span>${salary.employee.position} </span></a
                            >
                          </h2>
                        </td>
                        <td>${salary.employee.employeeCode}</td>
                        <td>
                          <a>${salary.employee.email}</a>
                        </td>
                        <td>${DateUtils.convertDate(
                          salary.employee.createDate,
                          1
                        )}</td>
                        <td>
                            ${salary.employee.position}
                        </td>
                        <td>${salary.salary}</td>
                        <td>
                          <a
                            class="btn btn-sm btn-primary"
                            href="salary-view.html?id=${salary.id}"
                            >Generate Slip</a
                          >
                        </td>
                        <td class="text-right">
                          <div class="dropdown dropdown-action">
                            <a
                              href="#"
                              class="action-icon dropdown-toggle"
                              data-toggle="dropdown"
                              aria-expanded="false"
                              ><i class="fas fa-ellipsis-v"></i
                            ></a>
                            <div class="dropdown-menu dropdown-menu-right">
                              <a
                                class="dropdown-item"
                                href="#"
                                data-toggle="modal"
                                data-target="#delete_salary"
                                title="Delete"
                                ><i class="fas fa-trash-alt m-r-5"></i>
                                Delete</a
                              >
                            </div>
                          </div>
                        </td>
                      </tr>`;
    html += salaryRow;
  });
  $(idSalaryComponent).html(html);
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

let urlApi = "/salary";
let data = {};
const paramsSearch = getUrlVars();
if (paramsSearch) {
  urlApi += "/search";
  Object.keys(paramsSearch).forEach((att) => {
    $(`#${att}`).val(paramsSearch[att]).change();
  });
  data = {
    position: $("#positionSearch").val(),
    startMonth: paramsSearch.fromDate
      ? paramsSearch.fromDate.substring(3, 5)
      : 0,
    startYear: paramsSearch.fromDate ? paramsSearch.fromDate.substring(6) : 0,
    endMonth: paramsSearch.fromDate ? paramsSearch.toDate.substring(3, 5) : 0,
    endYear: paramsSearch.fromDate ? paramsSearch.toDate.substring(6) : 0,
  };
}
console.log(urlApi);
ApiClient.get(urlApi, data)
  .then((resp) => {
    let data = resp.data.data;
    console.log(data);
    renderData(data);

    paging();
  })
  .catch((err) => {});
