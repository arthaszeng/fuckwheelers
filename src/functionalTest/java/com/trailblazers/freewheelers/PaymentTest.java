package com.trailblazers.freewheelers;

import com.trailblazers.freewheelers.configuration.ToggledFeature;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static com.trailblazers.freewheelers.helpers.SyntaxSugar.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assume.assumeThat;


@Ignore
public class PaymentTest extends UserJourneyBase {

    @Before
    public void setUp() throws Exception {
        togglzRule.enable(ToggledFeature.SHOPPING_CART);
        togglzRule.disable(ToggledFeature.RESERVE_ITEM);
        togglzRule.disable(ToggledFeature.CREDIT_CARD_PAYMENT);
    }

    @Test
    public void shouldShowPurchaseConfirmationWhenUserMakesPurchaseWithValidCreditCard() {
        userLogsInAndChecksOutAndItem();

        screen
                .shows_payment_screen();

        payByCreditCard(VALIDTESTCREDITCARDCSC, VALIDTESTCREDITCARDNUMBER, VALIDTESTCREDITCARDMONTH, VALIDTESTCREDITCARDEXPIRYYEAR);

        screen
                .shows_purchase_confirmation();
    }

    @Test
    public void shouldReturnToPaymentDetailScreenWhenUserEntersInvalidCreditCardCSC() {
        userLogsInAndChecksOutAndItem();

        screen
                .shows_payment_screen();

        payByCreditCard(INVALIDTESTCREDITCARDCSC, VALIDTESTCREDITCARDNUMBER, VALIDTESTCREDITCARDMONTH, VALIDTESTCREDITCARDEXPIRYYEAR);

        screen
                .shows_payment_screen()
                .shows_error("Must enter a valid cvv", "csc_field");
    }

    @Test
    public void shouldReturnToPaymentDetailScreenWhenUserEntersInvalidExpiryDate() throws Exception {
        userLogsInAndChecksOutAndItem();

        screen
                .shows_payment_screen();

        payByCreditCard(VALIDTESTCREDITCARDCSC, VALIDTESTCREDITCARDNUMBER, INVALIDTESTCREDITCARDMONTH, VALIDTESTCREDITCARDEXPIRYYEAR);

        screen
                .shows_payment_screen()
                .shows_error("Must enter a valid expiry date", "expiry_field");

    }

    @Test
    public void shouldAlertUserToEnterAValidNumberWhenUserEntersInvalidCreditCardNumber() throws Exception {
        userLogsInAndChecksOutAndItem();

        screen
                .shows_payment_screen();

        payByCreditCard(VALIDTESTCREDITCARDCSC, INVALIDTESTCREDITCARDNUMBER, VALIDTESTCREDITCARDMONTH, VALIDTESTCREDITCARDEXPIRYYEAR);

        screen
                .shows_payment_screen()
                .shows_error("Must enter a valid credit card number", "credit_card_number_field");
    }

    private void payByCreditCard(String csc, String creditCardNumber, String expiryMonth, String expiryYear) {
        user
                .enters_credit_card_number(creditCardNumber)
                .enters_expiry_month(expiryMonth)
                .enters_expiry_year(expiryYear)
                .enters_csc(csc)
                .selects_card_type("Visa")
                .pay_now();
    }

    private void userLogsInAndChecksOutAndItem() {
        assumeThat(
                togglzRule.getFeatureManager().isActive(ToggledFeature.CREDIT_CARD_PAYMENT), is(true));

        String simple_frame = "Simplon Pavo 3 Ultra " + System.currentTimeMillis();
        admin
                .there_is_a_user("Hugo Huser", SOME_PASSWORD)
                .create_an_item(simple_frame, ONLY_ONE_LEFT);
        user
                .logs_in_with("Hugo Huser", SOME_PASSWORD)
                .visits_home_page()
                .add_item_to_cart(simple_frame)
                .visits_shopping_cart()
                .checks_out();

    }
}
