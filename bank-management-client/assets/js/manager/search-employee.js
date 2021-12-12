const idBtnSearch = "#bt-search";
const idFormSearchEmployee = "#search-form";
$(idBtnSearch).click((e) => {
  e.preventDefault();
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
            bFilter: false,
          });
        }
      })(jQuery);
    })
    .catch((err) => {
      Notify.showError(err.message);
    });
});
