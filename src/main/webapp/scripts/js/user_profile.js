var fields = ['phone_number', 'country', 'country_id', 'street_one', 'street_two', 'city', 'state', 'post_code'];

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

$(function(){
    validateStateMandatory()
    validateRegistrationForm();
    validateFieldsWhenBlur(fields);

    $('#edit-detail').on('click',onClickEdit);
    $('#save-detail').hide().on('click',onClickSave);
    $('#cancel-edit').hide().on('click',onClickCancel);


    function onClickEdit() {
        toggleContent();
        validateStateMandatory();
    }

    function onClickCancel() {
        toggleContent();
    }

    function onClickSave() {
        if(!validateRegistrationForm()){
            showRegisterErrorMessage();
        } else {
            $('#profile_form').submit();
            toggleContent();
        }
    }

    function toggleContent() {
        var buttons = ['#save-detail', '#cancel-edit', '#edit-detail'];
        buttons.forEach(function (button) {
            $(button).toggle();
        });
        $('#user-details').toggle();
        $('#profile_form').toggle();
    }
});