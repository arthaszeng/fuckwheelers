function inputFieldSelector (field) {
    return '#fld_' + field;
}

function isAttentionField (field) {
    var fieldLabel = '#' + field + '_field label';
    return $(fieldLabel).hasClass('attention');
}

function errorSelector (field, type) {
    return '#' + field + '_field' + " ." + type;
}

function validateFieldsWhenBlur (fields) {
    $.each(fields, function (index, field) {
        var inputField = inputFieldSelector(field);
        $(inputField).change(function () {
            validateField(field);
        });
    })
}

function validateField (field) {
    var inputField = inputFieldSelector(field);
    var errorEmptyField = errorSelector(field, 'empty-error');
    var errorInvalidField = errorSelector(field, 'invalid-error');
    if (isAttentionField(field)) {
        $(errorSelector(field, 'invalid-error')).hide();
        handleMessage(validator.isFieldNotEmpty(inputField), errorEmptyField);
    }
    if ((!$(errorEmptyField).length) || $(errorEmptyField).is(':hidden')) {
        switch (inputField) {
            case '#fld_email': {
                handleMessage(validator.isValidEmail(inputField), errorInvalidField);
                break;
            }
            case '#fld_password': {
                handleMessage(validator.isValidPassword(inputField), errorInvalidField);
                break;
            }
            case '#fld_phone_number': {
                handleMessage(validator.isValidPhoneNumber(inputField), errorInvalidField);
                break;
            }
            case '#fld_name': {
                isUsernameExist(inputField);
                break;
            }
            case '#fld_street_one': {
                handleMessage(validator.isValidStreetName(inputField), errorInvalidField);
                break;
            }
            case '#fld_street_two': {
                handleMessage(validator.isValidStreetName(inputField), errorInvalidField);
                break;
            }
            case '#fld_city': {
                handleMessage(validator.isValidCity(inputField), errorInvalidField);
                break;
            }
            case '#fld_state': {
                handleMessage(validator.isValidState(inputField), errorInvalidField);
                break;
            }
            case '#fld_post_code': {
                handleMessage(validator.isValidPostCode(inputField), errorInvalidField);
                break;
            }
            case '#fld_confirmedPassword': {
                handleMessage(validator.isMatchedPassword(inputField), errorInvalidField);
                break;
            }
            case '#fld_agreement': {
                handleMessage(validator.isCheckedCheckbox(inputField), errorEmptyField);
                break;
            }
        }
    }
}

function handleMessage (flag, field) {
    flag ? $(field).hide() : $(field).show();
}

function isUsernameExist (selector) {
    $.ajax({
        url: '/account/userDuplicate',
        data: {name: $(selector).val()},
        type: 'GET',
        statusCode: {
            200: function () {
                $(errorSelector('name', 'invalid-error')).show();
            },
            404: function () {
                $(errorSelector('name', 'invalid-error')).hide();
            }
        }
    })
}

function validateStateMandatory () {
    var mandatoryCountry = ['USA', 'Italy', 'Canada'];
    $('#fld_country').change(function () {
        $('#state_field label').removeClass('attention');
        $('#state_field .text-error').hide();
        var country = $('#fld_country option:selected').html().trim();
        if (mandatoryCountry.indexOf(country) > -1) {
            $('#state_field label').addClass('attention');
        }
    })

}

function showRegisterErrorMessage () {
    fields.forEach(function (field) {
        validateField(field);
    });
}

