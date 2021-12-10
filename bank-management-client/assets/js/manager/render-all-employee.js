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
      let employeeRow = `<div class="col-md-4 col-sm-6 col-12 col-lg-4 col-xl-3">
              <div class="profile-widget">
                <div class="profile-img">
                  <a href="profile.html?id=${
                    employee.id
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
                    <a class="dropdown-item" href="edit-employee.html?id=${
                      employee.id
                    }"
                      ><i class="fas fa-pencil-alt m-r-5"></i> Edit</a
                    >
                    <a
                      class="dropdown-item"
                      href="${employee.id}"
                      data-toggle="modal"
                      data-target="#delete_employee"
                      class="delete" onclick="fillModalDelete(${
                        employee.id
                      })" ><i class="fas fa-trash-alt m-r-5" ></i> Delete</a
                    >
                  </div>
                </div>
                <h4 class="user-name m-t-10 m-b-0 text-ellipsis">
                  <a href="profile.html?id=${employee.id}" >${
        employee.fullname.firstName + " " + employee.fullname.lastName
      }</a>
                </h4>
                <div class="small text-muted">${employee.position}</div>
              </div>
            </div>`;
      html += employeeRow;
    });
    $(employeeGrid).html(html);
  })
  .catch((err) => {
    Notify.showError(err.response.data.data.message);
  });

//fill id vao modal de xoa
function fillModalDelete(employeeID) {
  $("#delete-confirm").attr("value", employeeID);
}

//xoa
$("#delete-confirm").on("click", () => {
  let employeeID = $("#delete-confirm").attr("value");
  console.log(employeeID);
  ApiClient.delete("/employee/" + employeeID)
    .then((resp) => {
      let data = resp.data;
      console.log("object");
      console.log(data.message);
      if (data.responseStatus == "Success") {
        Notify.showSuccess(resp.data.message);
      } else {
        Notify.showError(resp.data.message);
      }
    })
    .catch((err) => {});
});
