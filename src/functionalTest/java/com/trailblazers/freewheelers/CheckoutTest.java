package com.trailblazers.freewheelers;

import com.trailblazers.freewheelers.configuration.ToggledFeature;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


import java.math.BigDecimal;

import static com.trailblazers.freewheelers.helpers.SyntaxSugar.*;
import static com.trailblazers.freewheelers.helpers.SyntaxSugar.ONLY_ONE_LEFT;
import static com.trailblazers.freewheelers.helpers.SyntaxSugar.SOME_PASSWORD;
import static org.hamcrest.core.Is.is;
import static org.junit.Assume.assumeThat;

@Ignore
public class CheckoutTest extends UserJourneyBase {
    @Before
    public void setUp() throws Exception {
        togglzRule.enable(ToggledFeature.SHOPPING_CART);
        togglzRule.disable(ToggledFeature.RESERVE_ITEM);
        togglzRule.disable(ToggledFeature.CREDIT_CARD_PAYMENT);
    }

    @Test
    public void testCheckoutProcess() {
        if (ToggledFeature.SHOPPING_CART.isActive() && ToggledFeature.CREDIT_CARD_PAYMENT.isActive()) {
            String bob = "Bob Buyer";
            String simplon_frame = "Simplon Pavo 3 Ultra " + System.currentTimeMillis();

            admin
                    .there_is_a_user(bob, SOME_PASSWORD)
                    .create_an_item(simplon_frame, ONLY_ONE_LEFT);
            user
                    .logs_in_with(bob, SOME_PASSWORD)
                    .visits_home_page()
                    .visits_shopping_cart();
            screen
                    .shows_no_checkout_button();
            user
                    .visits_home_page()
                    .add_item_to_cart(simplon_frame)
                    .visits_shopping_cart();
            screen
                    .shows_no_reserve_button()
                    .shows_checkout_button();
            user
                    .clicks_checkout_button();
            screen
                    .shows_payment_screen();
            user
                    .is_logged_out()
                    .visits_home_page()
                    .add_item_to_cart(simplon_frame)
                    .visits_shopping_cart()
                    .clicks_checkout_button();
            screen
                    .shows_login();
            user
                    .logs_in_with(bob, SOME_PASSWORD)
                    .is_logged_out();
        }
    }

    @Test
    public void testCheckoutOrderSummary() {
        if (ToggledFeature.SHOPPING_CART.isActive() && ToggledFeature.CREDIT_CARD_PAYMENT.isActive()) {
            String User = "Da Buyer";
            String simplon_frame = "Simplon Pavo 3 Ultra " + System.currentTimeMillis();
            String complex_frame = "Complex Pavo 3 Ultra " + System.currentTimeMillis();

            admin
                    .there_is_a_user_with_country(User, SOME_PASSWORD, UK_COUNTRY_ID)
                    .create_an_item(simplon_frame, ONLY_ONE_LEFT)
                    .create_an_item(complex_frame, ONLY_ONE_LEFT);
            user
                    .logs_in_with(User, SOME_PASSWORD)
                    .visits_home_page()
                    .add_item_to_cart(simplon_frame)
                    .add_item_to_cart(complex_frame)
                    .visits_shopping_cart()
                    .clicks_checkout_button();
            screen
                    .shows_payment_screen()
                    .should_list_item(complex_frame)
                    .should_list_item(simplon_frame)
                    //.shows_taxes_for_uk()
                    //.shows_subtotal_price(SOME_PRICE.multiply(BigDecimal.valueOf(2)).toString())
                    .shows_total_price(SOME_PRICE_WITH_ADDED_20_PERCENT.multiply(BigDecimal.valueOf(2)).toString());
        }
    }
}
