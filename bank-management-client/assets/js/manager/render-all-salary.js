const idSalaryComponent = "#salary-component";
ApiClient.get("/salary", {})
  .then((resp) => {
    let data = resp.data.data;
    let html = "";
    data.forEach((salary) => {
      let firstLetter = salary.employee.fullName.firstName.substring(0, 1);
      let salaryRow = `<tr>
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
                        <td>${salary.employee.createDate}</td>
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

    (function ($) {
      "use strict";
      if ($(".datatable").length > 0) {
        $(".datatable").DataTable({
          bFilter: false,
        });
      }
    })(jQuery);
  })
  .catch((err) => {});
