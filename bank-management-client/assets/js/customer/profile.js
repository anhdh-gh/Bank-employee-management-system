ApiClient.get("/person/info")
.then((resp) => {
  const data = resp.data.data;
  console.log(data);
  $("#fullName").text(
    data.info.fullName.firstName + " " + data.info.fullName.lastName
  );
  $("#fullNameHello").text("Hello, " + data.info.fullName.firstName + " " + data.info.fullName.lastName)
  $("#firstNameEdit").val(data.info.fullName.firstName)
  $("#lastNameEdit").val(data.info.fullName.lastName)
  $("#customer-code").text("Customer Code: " + data.customerCode);
  $("#phoneNumber").text(data.info.phoneNumber);
  $("#email").text(data.info.email);
  $("#emailEdit").val(data.info.email);
  $("#dateOfBirth").text(DateUtils.convertDate(data.info.dateOfBirth,1));
  $("#dateOfBirthEdit").val(DateUtils.convertDate(data.info.dateOfBirth,1));
//   console.log(DateUtils.convertDate(data.dateOfBirth,1));
  $("#address").text(
    data.info.address.houseNumber +
      ", " +
      data.info.address.district +
      ", " +
      data.info.address.city +
      ", " +
      data.info.address.country
  );
  $("#numberHouseEdit").val(data.info.address.houseNumber);
  $("#districtEdit").val(data.info.address.district);
  $("#cityEdit").val(data.info.address.city);
  $("#countryEdit").val(data.info.address.country);
  $("#zipCodeEdit").val(data.info.address.zipCode);
})
.catch((err) => {});
