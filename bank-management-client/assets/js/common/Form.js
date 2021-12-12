const Form = {
  getValueInput: (idInput) => {
    const input = $(idInput);
    const type = input.attr("type");
    switch (type) {
      case "text":
      case "password":
      case "email":
      case "number":
        return input.val();

      case "checkbox":
        return input.is(":checked");
    }
  },

  getData: (idForm) => {
    const data = {};
    $(`${idForm} input`).each(function () {
      const input = $(this);
      data[`${input.attr("name")}`] = Form.getValueInput(
        `#${input.attr("id")}`
      );
    });

    $(`${idForm} select`).each(function () {
      const select = $(this);
      data[`${select.attr("name")}`] = $(
        `#${select.attr("id")} :selected`
      ).text();
    });
    return data;
  },
};
