var validator = {
    isFieldNotEmpty: function (selector) {
        return $(selector).val().trim() != "";
    },

    isValidEmail: function (selector) {
        return $(selector).val().indexOf("@") >= 0;
    },

    isValidPassword: function (selector) {

        var ruleOfPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[.#%ˆ\^$@$!%*?&+\\/':,)(}{\]\[~\-_`])[A-Za-z\d.#%ˆ\^$@$!%*?&+\\/':,)(}{\]\[~\-_`]{8,20}$/;

        return ruleOfPassword.test($(selector).val());
    },

    isValidPhoneNumber: function (selector) {
        var ruleOfPhoneNumber = /^[\S][0-9-*]*$/;
        return ruleOfPhoneNumber.test($(selector).val());
    },

    isValidStreetName: function (selector) {
        return isFieldLengthValid(selector, 255) && isFieldTextValid(selector);
    },

    isValidCity: function (selector) {
        return isFieldLengthValid(selector, 100) && isFieldTextValid(selector);
    },

    isValidState: function (selector) {

        return isFieldLengthValid(selector, 100) && isFieldTextValid(selector);
    },

    isValidPostCode: function (selector) {

        var ruleOfPostCode = /^[a-zA-Z0-9\d\-_\s]{4,10}$/;
        return ruleOfPostCode.test($(selector).val());
    },

    isMatchedPassword: function (selector) {
        return $(selector).val() == $('#fld_password').val();
    },

    isCheckedCheckbox: function (selector) {
        return $(selector).is(':checked');
    }

};

function isFieldLengthValid (selector, max) {
    return $(selector).val().length <= max;
}

function isFieldTextValid (selector) {
    var rule = /^[a-z\u00E0-\u00FCA-Z\u00E0-\u00FC0-9\d\\/#,:.&'\-_\s]*$/;
    return rule.test($(selector).val());
}
