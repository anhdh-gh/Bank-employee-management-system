ApiClient.get("/employee/profile", {})
  .then((resp) => {
    const profile = resp.data.data.account.username;
    $("#profile").text(profile);
  })
  .catch((err) => {});

const employeeGrid = "#employee-grid";
ApiClient.get("/employee", {})
  .then((resp) => {
    let data = resp.data.data;
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
                            href="edit-employee.html"
                            class="btn btn-primary btn-sm mb-1"
                          >
                            <i class="far fa-edit"></i>
                          </a>
                          <button
                            type="submit"
                            data-toggle="modal"
                            data-target="#delete_employee"
                            class="btn btn-danger btn-sm mb-1" class="delete" value="${
                              employee.id
                            }"
                          >
                            <i class="far fa-trash-alt"></i>
                          </button>
                        </td>
                      </tr>`;
      html += employeeRow;
    });
    $(employeeGrid).html(html);
  })
  .catch((err) => {
    Notify.showError(err.response.data.data.message);
  });

$(".delete").on("click", () => {
  console.log(12345);
  console.log($("#delete").attr("value"));
});
