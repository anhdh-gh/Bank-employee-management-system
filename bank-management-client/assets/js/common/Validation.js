jQuery.validator.addMethod("notBlank", function (value, element) {
    return value.trim().length > 0;
}, "No space please and don't leave it empty")