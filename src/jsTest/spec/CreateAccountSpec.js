describe("validate registration form", function() {

    function setUpHTMLFixture() {
        jasmine.getFixtures().set(' <input id="fld_email" type="text" >\
                                    <input id="fld_name" type = "text">\
                                    <input id="fld_password" type = "text">\
                                    <input id="fld_phone_number" type = "text">\
                                    <input id="fld_confirmedPassword" type = "text">\
                                    <select id="fld_country" name="country_id">\
                                        <option value="">Options</option>\
                                        <option value="1">USA</option>\
                                    </select>\
                                    <input id="fld_street_one" type="text"> \
                                    <input id="fld_street_two" type="text"> \
                                    <input id="fld_city" type="text"> \
                                    <input id="fld_state" type="text"> \
                                    ');

    }

    beforeEach(function() {
        setUpHTMLFixture();
    });

    it("should return false when input is empty", function() {
        var empty = "   ";
        $("#fld_email").val(empty);

        expect(validator.isFieldNotEmpty("#fld_email")).toBeFalsy();
    });

    it("should return false when email is invalid", function() {
        var invalidEmail = "gamil";
        $("#fld_email").val(invalidEmail);

        expect(validator.isValidEmail("#fld_email")).toBeFalsy();
    });



    it("should return true when email is valid", function() {
        var validEmail = "test@gmail.com";
        $("#fld_email").val(validEmail);

        expect(validator.isValidEmail("#fld_email")).toBeTruthy();
    });

    //8 and 20 characters, and contain at least 1 number, 1 lowercase letter, 1 uppercase letter, and 1 special character

    it("should return false when password's length is less than 8 characters", function() {
        var invalidPassword1 = "Aa1@";

        $("#fld_password").val(invalidPassword1);
        expect(validator.isValidPassword("#fld_password")).toBeFalsy();
    });

    it("should return true when password's length is. 8 characters", function() {
        var invalidPassword1 = "Aa1@Aa1@";

        $("#fld_password").val(invalidPassword1);
        expect(validator.isValidPassword("#fld_password")).toBeTruthy();
    });

    it("should return false when password's length is more than 20 characters", function() {
        var invalidPassword = "Aa1@Aa1@Aa1@Aa1@Aa1@!!!";

        $("#fld_password").val(invalidPassword);
        expect(validator.isValidPassword("#fld_password")).toBeFalsy();
    });

    it("should return true when password's length is 20", function() {
        var invalidPassword = "Aa1@Aa1@Aa1@Aa1@Aa1@";

        $("#fld_password").val(invalidPassword);
        expect(validator.isValidPassword("#fld_password")).toBeTruthy();
    });

    it("should return false when password contain at no number", function() {
        var invalidPassword = "Aa@Aa@Aa@";

        $("#fld_password").val(invalidPassword);
        expect(validator.isValidPassword("#fld_password")).toBeFalsy();
    });

    it("should return false when password contain at no lowercase letter", function() {
        var invalidPassword = "A1@A1@A1@";

        $("#fld_password").val(invalidPassword);
        expect(validator.isValidPassword("#fld_password")).toBeFalsy();
    });

    it("should return false when password contain at no uppercase letter", function() {
        var invalidPassword = "a1@a1@a1@";

        $("#fld_password").val(invalidPassword);
        expect(validator.isValidPassword("#fld_password")).toBeFalsy();
    });

    it("should return false when password contain at no special character", function() {
        var invalidPassword = "Aa1Aa1Aa1";

        $("#fld_password").val(invalidPassword);
        expect(validator.isValidPassword("#fld_password")).toBeFalsy();
    });

    it("should return true when country id is not empty", function() {
        var country = $("select[name='country_id'] option:eq(1)").val();
        expect(country).toEqual('1');
    });

    it("should return true when street_one is invalid", function() {
        var someStreet = "123~~~";
        $("#fld_street_one").val(someStreet);

        expect(validator.isValidStreetName("#fld_street_one")).toBeFalsy();
    });

    it("should return true when street_one is valid", function() {
        var someStreet = "main st 123";
        $("#fld_street_one").val(someStreet);

        expect(validator.isValidStreetName("#fld_street_one")).toBeTruthy();
    });

    it("should return true when city is not empty", function() {
        var someCity = "Delhi";
        $("#fld_city").val(someCity);

        expect(validator.isValidCity("#fld_city")).toBeTruthy();
    });

    it("should return true when state is not empty", function() {
        var someState = "Maharashtra";
        $("#fld_state").val(someState);

        expect(validator.isValidStreetName("#fld_state")).toBeTruthy();
    });

});

