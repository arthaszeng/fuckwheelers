package com.trailblazers.freewheelers;


import com.trailblazers.freewheelers.configuration.ToggledFeature;
import org.junit.Before;
import org.junit.Test;

import static com.trailblazers.freewheelers.helpers.SyntaxSugar.*;
import static org.junit.Assert.assertTrue;

public class AdminJourneyTest extends UserJourneyBase {
    private String SIMPLE_FRAME = "Simple Pavo 3 Ultra " + System.currentTimeMillis();
    private String NEW_SIMPLE_FRAME = "Simple name after change" + System.currentTimeMillis();

    private final long some_account_id = 12L;

    @Before
    public void setUp() throws Exception {
        long quantity = 10;

        togglzRule.enable(ToggledFeature.SHOPPING_CART);
        togglzRule.disable(ToggledFeature.RESERVE_ITEM);
        togglzRule.enable(ToggledFeature.CREDIT_CARD_PAYMENT);

        admin
                .there_is_no_item(SIMPLE_FRAME)
                .there_is_a_order_has_been_paid();
    }

    @Test
    public void shouldBeAllowedForAdminJourney() {
        //manage item
        user
                .logs_in_with(ADMIN, ADMINPASSWORD)
                .visits_his_profile();

        screen
                .shows_profile_for(ADMIN);

        user
                .visits_admin_profile()
                .visits_manage_items_page()
                .creates_an_item(SIMPLE_FRAME, ITEM_TYPE_FRAME, A_LOT, REALLY_EXPENSIVE, SOME_DESCRIPTION);

        screen
                .shows_in_manage_item_list(SIMPLE_FRAME);

        user
                .changes_item_name(from(SIMPLE_FRAME), to(NEW_SIMPLE_FRAME));

        screen
                .shows_in_manage_item_list(NEW_SIMPLE_FRAME)
                .shows_not_in_manage_item_list(SIMPLE_FRAME);

        user
                .delete_item(NEW_SIMPLE_FRAME);

        screen
                .shows_not_in_manage_item_list(NEW_SIMPLE_FRAME);

        //check orders
        user
                .visits_admin_profile()
                .click_one_order_for_some_user(USER);
        screen
                .shows_profile_for(USER);

        admin

                .there_are_no_survey_entries()
                .there_is_a_survey_entry_for(some_account_id, 10, "Some Feedback")
                .there_is_a_survey_entry_for(some_account_id, 10, "Some Positive Feedback")
                .there_is_a_survey_entry_for(some_account_id, 7, "Some Passive Feedback")
                .there_is_a_survey_entry_for(some_account_id, 0, "Some Negative Feedback");
        user
                .visits_nps_report_page();

        assertTrue(nps_report_page.totalResponsesCorrect("4"));
        assertTrue(nps_report_page.npsScoreCorrect("25"));
        assertTrue(nps_report_page.percentagePromotersCorrect("50"));
        assertTrue(nps_report_page.percentageDetractorsCorrect("25"));
        assertTrue(nps_report_page.percentagePassivesCorrect("25"));
        assertTrue(nps_report_page.containsPromoterFeedbackComment("Some Positive Feedback"));
        assertTrue(nps_report_page.containsDetractorFeedbackComment("Some Negative Feedback"));
        assertTrue(nps_report_page.containsPassiveFeedbackComment("Some Passive Feedback"));





    }

}