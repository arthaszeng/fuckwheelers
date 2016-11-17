package com.trailblazers.freewheelers;

import com.trailblazers.freewheelers.configuration.ToggledFeature;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static com.trailblazers.freewheelers.helpers.SyntaxSugar.ONLY_ONE_LEFT;
import static com.trailblazers.freewheelers.helpers.SyntaxSugar.SOME_PASSWORD;
import static org.hamcrest.core.Is.is;
import static org.junit.Assume.assumeThat;
@Ignore
public class OrderTest extends UserJourneyBase {
    @Before
    public void setUp() throws Exception {
        togglzRule.enable(ToggledFeature.SHOPPING_CART);
        togglzRule.disable(ToggledFeature.RESERVE_ITEM);
    }

    @Test
    public void testOrderProcess() throws Exception {
        assumeThat(togglzRule.getFeatureManager().isActive(ToggledFeature.SHOPPING_CART), is(true));

        String arno = "Arno Admin";
        String bob = "Bob Buyer";
        String simplon_frame = "Simplon Pavo 3 Ultra " + System.currentTimeMillis();

        admin
                .there_is_an_admin(arno, SOME_PASSWORD)
                .there_is_a_user(bob, SOME_PASSWORD)
                .create_an_item(simplon_frame, ONLY_ONE_LEFT);

        user
                .logs_in_with(bob, SOME_PASSWORD)
                .visits_home_page();

        screen
                .should_list_item(simplon_frame);

        user
                .add_item_to_cart(simplon_frame)
                .visits_home_page();

        user
                .logs_in_with(arno, SOME_PASSWORD)
                .visits_admin_profile();

  //      screen
  //              .there_should_be_an_order(Simplon_Frame, "NEW");
//
//        user
//                .changes_order_status(Simplon_Frame, "IN_PROGRESS");
//
//        screen
//                .there_should_be_an_order(Simplon_Frame, "IN_PROGRESS");
    }

}
