jQuery.validator.addMethod("notBlank", function (value, element) {
    return value.indexOf(" ") < 0 && value != "";
}, "No space please and don't leave it empty")