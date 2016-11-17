var fields = ['user', 'password'];

function inputFieldSelector(field) {
    return '#fld_' + field;
}

function errorSelector(field) {
    return '#' + field + '_field' + " .text-error";
}

function validateLoginForm() {
    var validate = true;
    var errorFields = $('.text-error');
    showLoginErrorMessage();

    $.each(errorFields, function (index, error) {
        if(!$(error).is(':hidden')){
            validate = false;
        }
    })
    return validate;
}

function showLoginErrorMessage() {
    fields.forEach(function(field) {
        validator.isFieldNotEmpty(inputFieldSelector(field))? $(errorSelector(field)).hide(): $(errorSelector(field)).show();
    });
}