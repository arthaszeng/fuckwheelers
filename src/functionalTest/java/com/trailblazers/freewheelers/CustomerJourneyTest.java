package com.trailblazers.freewheelers;


import com.trailblazers.freewheelers.helpers.FeedbackType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.trailblazers.freewheelers.helpers.SyntaxSugar.*;

public class CustomerJourneyTest extends UserJourneyBase {
    private String COMPLEX_FRAME = "Complex Pavo 3 Ultra " + System.currentTimeMillis();
    private String SIMPLE_FRAME = "Simple Pavo 3 Ultra " + System.currentTimeMillis();
    private String NORMALUSER = "Normal User";

    @Before
    public void setUp() throws Exception {
        admin
                .create_an_item(SIMPLE_FRAME, ONLY_ONE_LEFT)
                .create_an_item(COMPLEX_FRAME, ONLY_ONE_LEFT)
                .there_is_no_account_for(NORMALUSER);

        user
                .is_logged_out();
    }

    @Test
    public void shouldBeAllowedForUserJourney() throws InterruptedException {
        user
                .visits_home_page()
                .add_item_to_cart(SIMPLE_FRAME);

        screen
                .shows_added_to_cart_message(SIMPLE_FRAME + " has been added to your cart.");

        user
                .visits_shopping_cart()
                .clicks_continue_shopping()
                .add_item_to_cart(COMPLEX_FRAME);

        screen
                .shows_added_to_cart_message(COMPLEX_FRAME + " has been added to your cart.");

        user
                .visits_shopping_cart();

        screen
                .shows_shopping_cart_screen()
                .should_list_item(SIMPLE_FRAME)
                .should_list_item(COMPLEX_FRAME);

        user
                .checks_out()
                .creates_an_account(NORMALUSER, SOME_EMAIL, EMPTY_PASSWORD, SOME_PHONE_NUMBER, SOME_PASSWORD, SOME_COUNTRY, SOME_STREET_ONE, SOME_STREET_TWO, SOME_CITY, SOME_STATE, SOME_POST_CODE, CHECK_AGREEMENT);

        screen
                .shows_different_error("Password cannot be empty","empty-error", "password_field");

        user
                .creates_an_account(NORMALUSER, SOME_EMAIL, SOME_PASSWORD, SOME_PHONE_NUMBER, SOME_PASSWORD, SOME_COUNTRY, SOME_STREET_ONE, SOME_STREET_TWO, SOME_CITY, SOME_STATE, SOME_POST_CODE, CHECK_AGREEMENT);

        screen
                .shows_message("account has been created");

        user
                .logs_in_with(NORMALUSER, SOME_PASSWORD)
                .visits_his_profile();

        screen
                .shows_profile_for(NORMALUSER);

        user
                .visits_shopping_cart()
                .clicks_checkout_button();

        screen
                .shows_payment_screen();

        user
                .enters_credit_card_number(VALIDTESTCREDITCARDNUMBER)
                .enters_expiry_month(VALIDTESTCREDITCARDMONTH)
                .enters_expiry_year(VALIDTESTCREDITCARDEXPIRYYEAR)
                .enters_csc(VALIDTESTCREDITCARDCSC)
                .selects_card_type(VISA)
                .pay_now();

        screen
                .shows_purchase_confirmation();

        user
                .waits_for_survey_popup();

        nps_survey_form.submitFeedback(FeedbackType.Positive, "Some Feedback");
        Assert.assertTrue(nps_survey_form.thankYouMessageExists());
        nps_survey_form.closeWindow();
    }
}