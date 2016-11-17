var fields = ['email', 'name', 'password', 'phone_number', 'country', 'country_id', 'confirmedPassword', 'street_one', 'street_two', 'city', 'state', 'post_code', 'agreement'];

$(document).ready(function(){
    validateStateMandatory()
    validateFieldsWhenBlur(fields);
    $('#fld_agreement').change(function () {
        var errorEmptyField = errorSelector('agreement', 'empty-error');
        handleMessage($(this).is(':checked'), errorEmptyField);
    })
});

function validateRegistrationForm() {
    var validate = true;
    var errorFields = $('.text-error');

    $.each(errorFields, function (index, error) {
        if(!$(error).is(':hidden')){
            validate = false;
        }
    })

    return validate;
}
