package com.trailblazers.freewheelers;

import com.trailblazers.freewheelers.service.CannotUpdateAccountException;
import org.junit.Ignore;
import org.junit.Test;

import static com.trailblazers.freewheelers.helpers.SyntaxSugar.*;

@Ignore
public class AccountTest extends UserJourneyBase {

    @Test
    public void testCreateAccount() throws Exception {
        String jan = "Jan Plewka";

        admin
                .there_is_no_account_for(jan);
        user
                .is_logged_out()
                .logs_in_with(jan, SOME_PASSWORD);
        screen
                .shows_error_alert("login attempt was not successful");
        user
                .creates_an_account(jan, SOME_EMAIL, SOME_PASSWORD, EMPTY_PHONE_NUMBER, SOME_PASSWORD, SOME_COUNTRY, SOME_STREET_ONE, SOME_STREET_TWO, SOME_CITY, SOME_STATE, SOME_POST_CODE, CHECK_AGREEMENT);
        screen
                .shows_different_error("Phone number cannot be empty","empty-error", "phone_number_field");
        user
                .creates_an_account(jan, SOME_EMAIL, SOME_PASSWORD, SOME_PHONE_NUMBER, SOME_PASSWORD, NO_COUNTRY, SOME_STREET_ONE, SOME_STREET_TWO, SOME_CITY, SOME_STATE, SOME_POST_CODE, CHECK_AGREEMENT);
        screen
                .shows_different_error("Please choose a country","empty-error", "country_field");
        user
                .creates_an_account(jan, SOME_EMAIL, EMPTY_PASSWORD, SOME_PHONE_NUMBER, SOME_PASSWORD, SOME_COUNTRY, SOME_STREET_ONE, SOME_STREET_TWO, SOME_CITY, SOME_STATE, SOME_POST_CODE, CHECK_AGREEMENT);
        screen
                .shows_different_error("Password cannot be empty","empty-error", "password_field");
        user
                .creates_an_account(jan, SOME_EMAIL, INVALID_PASSWORD, SOME_PHONE_NUMBER, SOME_PASSWORD, SOME_COUNTRY, SOME_STREET_ONE, SOME_STREET_TWO, SOME_CITY, SOME_STATE, SOME_POST_CODE, CHECK_AGREEMENT);
        screen
                .shows_different_error("Please enter valid password","invalid-error", "password_field");
        user
                .creates_an_account(jan, SOME_EMAIL, SOME_PASSWORD, SOME_PHONE_NUMBER, "abc", SOME_COUNTRY, SOME_STREET_ONE, SOME_STREET_TWO, SOME_CITY, SOME_STATE, SOME_POST_CODE, CHECK_AGREEMENT);
        screen
                .shows_different_error("Passwords do not match","invalid-error", "confirmedPassword_field");
        user
                .creates_an_account(jan, SOME_EMAIL, SOME_PASSWORD, SOME_PHONE_NUMBER, SOME_PASSWORD, SOME_COUNTRY, EMPTY_STREET, SOME_STREET_TWO, SOME_CITY, SOME_STATE, SOME_POST_CODE, CHECK_AGREEMENT);
        screen
                .shows_different_error("Street name cannot be empty","empty-error", "street_one_field");
        user
                .creates_an_account(jan, SOME_EMAIL, SOME_PASSWORD, SOME_PHONE_NUMBER, SOME_PASSWORD, SOME_COUNTRY, SOME_STREET_ONE, INVALID_STREET, SOME_CITY, SOME_STATE, SOME_POST_CODE, CHECK_AGREEMENT);
        screen
                .shows_different_error("Please enter valid Street name","invalid-error", "street_two_field");
        user
                .creates_an_account(jan, SOME_EMAIL, SOME_PASSWORD, SOME_PHONE_NUMBER, SOME_PASSWORD, SOME_COUNTRY, SOME_STREET_ONE, SOME_STREET_TWO, EMPTY_CITY, SOME_STATE, SOME_POST_CODE, CHECK_AGREEMENT);
        screen
                .shows_different_error("City cannot be empty","empty-error", "city_field");
        user
                .creates_an_account(jan, SOME_EMAIL, SOME_PASSWORD, SOME_PHONE_NUMBER, SOME_PASSWORD, SOME_COUNTRY, SOME_STREET_ONE, SOME_STREET_TWO, SOME_CITY, INVALID_STATE, SOME_POST_CODE, CHECK_AGREEMENT);
        screen
                .shows_different_error("Please enter valid State name", "invalid-error","state_field");
        user
                .creates_an_account(jan, SOME_EMAIL, SOME_PASSWORD, SOME_PHONE_NUMBER, SOME_PASSWORD, SOME_COUNTRY, SOME_STREET_ONE, SOME_STREET_TWO, SOME_CITY, SOME_STATE, INVALID_POST_CODE, NOT_CHECK_AGREEMENT);
        screen
                .shows_different_error("Please enter valid Post code", "invalid-error","post_code_field");
        user
                .creates_an_account(jan, SOME_EMAIL, SOME_PASSWORD, SOME_PHONE_NUMBER, SOME_PASSWORD, SOME_COUNTRY, SOME_STREET_ONE, SOME_STREET_TWO, SOME_CITY, SOME_STATE, SOME_POST_CODE, NOT_CHECK_AGREEMENT);
        screen
                .shows_different_error("Please agree to the Terms & Conditions","empty-error", "agreement_field");
        user
                .creates_an_account(jan, SOME_EMAIL, SOME_PASSWORD, SOME_PHONE_NUMBER, SOME_PASSWORD, SOME_COUNTRY, SOME_STREET_ONE, SOME_STREET_TWO, SOME_CITY, SOME_STATE, SOME_POST_CODE, CHECK_AGREEMENT);
        screen
                .shows_message("account has been created");
        user
                .is_logged_out()
                .logs_in_with(jan, SOME_PASSWORD)
                .visits_his_profile();
        screen
                .shows_in_navbar("Welcome " + jan);
        user
                .is_logged_out()
                .logs_in_with(jan, SOME_PASSWORD)
                .visits_his_profile();
        screen
                .shows_address_for(SOME_STREET_ONE);
        user
                .is_logged_out()
                .logs_in_with(jan, SOME_PASSWORD)
                .visits_his_profile();
        screen
                .shows_address_for(SOME_STREET_TWO);
    }

    @Test
    public void testAccessRights() throws Exception {
        String hugo = "Hugo Huser";
        String arno = "Arno Admin";

        admin
                .there_is_a_user(hugo, SOME_PASSWORD)
                .there_is_an_admin(arno, SOME_PASSWORD);
        user
                .is_logged_out()
                .logs_in();
        screen
                .shows_login();
        user
                .logs_in_with(hugo, SOME_PASSWORD)
                .visits_his_profile();
        screen
                .shows_profile_for(hugo)
                .shows_country(SOME_COUNTRY);
        screen
                .does_not_show_admin_link();
        user
                .logs_in_with(arno, SOME_PASSWORD)
                .visits_admin_profile();
        screen
                .shows_admin_profile();
    }

    @Test
    public void testPasswordMaskingForForms() throws Exception {
        String hugo = "Hugo Huser";

        user
                .give_login_credentials(hugo, SOME_PASSWORD);
        screen
                .should_mask_the_password_given_by_user_in_login();
    }

    @Test
    public void testAccessOfProfileWithUserThatHasNoCountry() throws Exception {
        String hans = "Hans Homeless";
        admin
                .there_is_a_user_with_no_country(hans, SOME_PASSWORD);
        user
                .is_logged_out()
                .logs_in_with(hans, SOME_PASSWORD)
                .visits_his_profile();
        screen
                .shows_profile_for(hans);
    }

    @Test
    public void testPasswordMaskingForCreateAccountPage() throws Exception {
        String hugo = "Hugo Huser";

        user
                .is_logged_out()
                .give_user_details(hugo, SOME_EMAIL, SOME_PASSWORD, SOME_PHONE_NUMBER, SOME_PASSWORD, SOME_COUNTRY, SOME_STREET_ONE, SOME_STREET_TWO, SOME_CITY, SOME_STATE, SOME_POST_CODE);
        screen
                .should_mask_the_password_given_by_user_in_create_account_page();

    }

    @Test
    public void testHTMLTagsAsUserNameSoThatHTMLCodeDoesNotExecute() {
        String user_name_with_html_tag = "<img src=\"https://i.ytimg.com/vi/MM9-fJgoL4A/maxresdefault.jpg\" style=\"width:980px;height:1200px;\">";
        String email_id = "email@a.com";
        String password = "Password@1.";

        user
                .is_logged_out()
                .creates_an_account(user_name_with_html_tag, email_id, password, SOME_PHONE_NUMBER, password, SOME_COUNTRY, SOME_STREET_ONE, SOME_STREET_TWO, SOME_CITY, SOME_STATE, SOME_POST_CODE, CHECK_AGREEMENT);
        screen
                .shows_result_message_after_account_creation(user_name_with_html_tag);
    }

    @Test
    public void shouldShowMessageOnDuplicateUsername() {
        String hugo = "Hugo Huser";

        admin
                .there_is_no_account_for(hugo)
                .there_is_a_user(hugo, SOME_PASSWORD);
        user
                .fills_username_field(hugo);
        screen
                .shows_different_error("This username already exists", "invalid-error","name_field");
    }

    @Test
    public void testCanCancelEditUserDetail() {
        String Band = "Daft Punk";
        admin
                .there_is_a_user(Band, SOME_PASSWORD);
        user
                .is_logged_out()
                .logs_in_with(Band, SOME_PASSWORD)
                .visits_his_profile();
        screen
                .shows_profile_for(Band);
        user
                .edit_user_detail()
                .cancel_edit();
        screen
                .shows_profile_for(Band);
    }

    @Test
    public void userCanUpdatePhoneNumber() throws CannotUpdateAccountException {
        String Band = "Daft";
        admin
                .there_is_a_user(Band, SOME_PASSWORD);
        user
                .is_logged_out()
                .logs_in_with(Band, SOME_PASSWORD)
                .visits_his_profile();
        screen
                .shows_phone_number_for(SOME_PHONE_NUMBER);
        user
                .edit_user_detail()
                .change_phone_number(SOME_OTHER_PHONE_NUMBER)
                .save_detail();
        screen
                .shows_profile_for(SOME_OTHER_PHONE_NUMBER);

    }
}
