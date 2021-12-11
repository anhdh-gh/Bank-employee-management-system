// get id
const currentURL = window.location.href;

function getUrlVars(url) {
  var vars = {};
  var parts = url.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
    vars[key] = value;
  });
  return vars;
}

const salaryID = getUrlVars(currentURL)["id"];
ApiClient.get("/salary/detail/" + salaryID, {})
  .then((resp) => {
    data = resp.data.data;
    $("#title-salary").text(
      "Payslip for " + data.salaryDto.month + "/" + data.salaryDto.year
    );
    $("#id-salary").text("Payslip #" + data.salaryDto.id);
    $("#month-year").text(data.salaryDto.month + "/" + data.salaryDto.year);

    $("#fullname").text(
      data.employeeDto.fullname.firstName +
        " " +
        data.employeeDto.fullname.lastName
    );
    $("#position").text(data.employeeDto.position);
    $("#employee-code").text(data.employeeDto.employeeCode);
    $("#join-date").text("Joining Date: " + data.employeeDto.createDate);

    //render detail salary
    let html = `<div class="col-sm-6">
                      <div>
                        <h4 class="m-b-10"><strong>Earnings</strong></h4>
                        <table class="table table-bordered">
                          <tbody>
                            <tr>
                              <td>
                                <strong>Basic Salary</strong>
                                <span class="float-right">${
                                  data.employeeDto.baseSalary
                                }</span>
                              </td>
                            </tr>
                            <tr>
                              <td>
                                <strong>Meal allowance</strong>
                                <span class="float-right">0</span>
                              </td>
                            </tr>
                            <tr>
                              <td>
                                <strong>Sales salary</strong>
                                <span class="float-right">${
                                  data.salaryDto.salary -
                                  data.employeeDto.baseSalary
                                }</span>
                              </td>
                            </tr>
                            <tr>
                              <td>
                                <strong>Other Allowance</strong>
                                <span class="float-right">0</span>
                              </td>
                            </tr>
                            <tr>
                              <td>
                                <strong>Total Earnings</strong>
                                <span class="float-right"
                                  ><strong>${
                                    data.salaryDto.salary
                                  }</strong></span
                                >
                              </td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </div>
                    <div class="col-sm-6">
                      <div>
                        <h4 class="m-b-10"><strong>Deductions</strong></h4>
                        <table class="table table-bordered">
                          <tbody>
                            <tr>
                              <td>
                                <strong>Tax Deducted at Source (T.D.S.)</strong>
                                <span class="float-right">0</span>
                              </td>
                            </tr>
                            <tr>
                              <td>
                                <strong>Provident Fund</strong>
                                <span class="float-right">0</span>
                              </td>
                            </tr>
                            <tr>
                              <td>
                                <strong>ESI</strong>
                                <span class="float-right">0</span>
                              </td>
                            </tr>
                            <tr>
                              <td>
                                <strong>Loan</strong>
                                <span class="float-right">0</span>
                              </td>
                            </tr>
                            <tr>
                              <td>
                                <strong>Total Deductions</strong>
                                <span class="float-right"
                                  ><strong>0</strong></span
                                >
                              </td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </div>
                    <div class="col-sm-12 mt-2">
                      <p>
                        <strong>Net Salary: ${data.salaryDto.salary}</strong>
                      </p>
                    </div>`;
    $("#detail-salary").html(html);

    //render bank account
    const idBankAccountGrid = "#bank-account-grid";
    var index = 1;
    var htmlBankAccountList = "";
    data.list.forEach((item) => {
      let bankAccountRow = `<tr>
                                    <td>${index++}</td>
                                    <td>${item.bankAccountDto.expireDate}</td>
                                    <td>${item.bankAccountDto.accountCode}</td>
                                    <td>${item.bankAccountDto.type}</td>
                                    <td>+ ${item.commission}</td>
                                  </tr>`;
      htmlBankAccountList += bankAccountRow;
    });

    $(idBankAccountGrid).html(htmlBankAccountList);
  })
  .catch((err) => {});
