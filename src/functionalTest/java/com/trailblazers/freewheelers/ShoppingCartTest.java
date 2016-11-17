package com.trailblazers.freewheelers;

import com.trailblazers.freewheelers.configuration.ToggledFeature;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static com.trailblazers.freewheelers.helpers.SyntaxSugar.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assume.assumeThat;

@Ignore
public class ShoppingCartTest extends UserJourneyBase {
    @Before
    public void setUp() throws Exception {
        togglzRule.enable(ToggledFeature.SHOPPING_CART);
        togglzRule.disable(ToggledFeature.RESERVE_ITEM);
    }

    @Test
    public void shoppingCartProcess() throws Exception {
        assumeThat(
                togglzRule.getFeatureManager().isActive(ToggledFeature.SHOPPING_CART), is(true));

        String bob = "Bob Buyer";
        String simplon_frame = "Simplon Pavo 3 Ultra " + System.currentTimeMillis();
        String complex_frame = "Complex Pavo 3 Ultra " + System.currentTimeMillis();

        admin
                .there_is_a_user(bob, SOME_PASSWORD)
                .create_an_item(simplon_frame, ONLY_ONE_LEFT)
                .create_an_item(complex_frame, ONLY_ONE_LEFT);
        user
                .is_logged_out()
                .visits_home_page()
                .visits_shopping_cart();
        screen
                .should_not_list_item(simplon_frame)
                .should_not_list_item(complex_frame);
        user
                .visits_home_page()
                .add_item_to_cart(complex_frame);

        screen
                .shows_home_screen()
                .shows_added_to_cart_message(complex_frame + " has been added to your cart.");
        user
                .visits_home_page()
                .add_item_to_cart(simplon_frame);
        screen
                .shows_home_screen()
                .shows_added_to_cart_message(simplon_frame + " has been added to your cart.");

        user
                .visits_shopping_cart();
        screen
                .shows_shopping_cart_screen()
                .should_list_item(complex_frame)
                .should_list_item(simplon_frame);
        user
                .cancels_order();
        screen
                .shows_home_screen()
                .should_list_item(simplon_frame);
        user
                .visits_shopping_cart();
        screen
                .shows_shopping_cart_screen();
        user
                .clicks_continue_shopping();
        screen
                .shows_home_screen();
    }

    @Test
    public void shouldShowVATAndTotalForLoggedInUKUser() throws Exception {
        assumeThat(togglzRule.getFeatureManager().isActive(ToggledFeature.SHOPPING_CART), is(true));

        String bob = "Bobby Bobbling";
        String simplon_frame = "Simplon Pavo 3 Ultra " + System.currentTimeMillis();

        admin

                .there_is_a_user_with_country(bob, SOME_PASSWORD, UK_COUNTRY_ID)
                .create_an_item(simplon_frame, ONLY_ONE_LEFT);
        user
                .is_logged_out()
                .visits_home_page()
                .add_item_to_cart(simplon_frame)
                .visits_shopping_cart();
        screen
                .shows_no_taxes()
                .shows_subtotal_price(SOME_PRICE.toString())
                .shows_total_price(SOME_PRICE.toString());
        user
                .logs_in_with(bob, SOME_PASSWORD)
                .visits_shopping_cart();
        screen
                .shows_taxes_for_uk()
                .shows_subtotal_price(SOME_PRICE.toString())
                .shows_total_price(SOME_PRICE_WITH_ADDED_20_PERCENT.toString());
    }

    @Test
    public void shouldShowVATAndTotalForLoggedInGermanyUser() throws Exception {
        assumeThat(
                togglzRule.getFeatureManager().isActive(ToggledFeature.SHOPPING_CART), is(true));

        String bob = "Hans Hamm";
            String simplon_frame = "Simplon Pavo 3 Ultra " + System.currentTimeMillis();

            admin

                    .there_is_a_user_with_country(bob, SOME_PASSWORD, GERMANY_COUNTRY_ID)
                    .create_an_item(simplon_frame, ONLY_ONE_LEFT);
            user
                    .is_logged_out()
                    .logs_in_with(bob, SOME_PASSWORD)
                    .visits_home_page()
                    .add_item_to_cart(simplon_frame)
                    .visits_shopping_cart();
            screen
                    .shows_taxes_for_germany()
                    .shows_subtotal_price(SOME_PRICE.toString())
                    .shows_total_price(SOME_PRICE_WITH_ADDED_19_PERCENT.toString());
    }
}
