package com.trailblazers.freewheelers;

import com.trailblazers.freewheelers.configuration.ToggledFeature;
import com.trailblazers.freewheelers.helpers.FeedbackType;
import org.junit.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;

@Ignore
public class NetPromoterScoreTest extends UserJourneyBase {

    private final String admin_user = "NPSAdmin";
    private final String password = "password";
    private final String username = "Promoter";
    private final String frame = "Bike Frame";
    private final long some_account_id = 12L;

    @Before
    public void setUp() {
        long quantity = 10;
        admin
                .there_is_an_admin(admin_user, password)
                .create_an_item(frame, quantity)
                .there_is_a_user(username, password);

        togglzRule.enable(ToggledFeature.SHOPPING_CART);
        togglzRule.disable(ToggledFeature.RESERVE_ITEM);
        togglzRule.enable(ToggledFeature.CREDIT_CARD_PAYMENT);
}


    @Test
    public void testSurveyPopUp() throws InterruptedException {
        assumeThat(
                togglzRule.getFeatureManager().isActive(ToggledFeature.SHOPPING_CART) &&
                        !togglzRule.getFeatureManager().isActive(ToggledFeature.RESERVE_ITEM) && togglzRule.getFeatureManager().isActive(ToggledFeature.CREDIT_CARD_PAYMENT), is(true));
        user
                .logs_in_with(username, password)
                .visits_home_page()
                .add_item_to_cart(frame)
                .visits_shopping_cart()
                .checks_out()
                .gives_credit_card_details()
                .submits()
                .waits_for_survey_popup();

        nps_survey_form.submitFeedback(FeedbackType.Positive, "Some Feedback");
        Assert.assertTrue(nps_survey_form.thankYouMessageExists());
        nps_survey_form.closeWindow();

        user.is_logged_out();

    }


    @Test
    public void shouldShowNPSReport() {

        admin
                .there_are_no_survey_entries()
                .there_is_a_survey_entry_for(some_account_id, 10, "Some Feedback")
                .there_is_a_survey_entry_for(some_account_id, 10, "Some Positive Feedback")
                .there_is_a_survey_entry_for(some_account_id, 7, "Some Passive Feedback")
                .there_is_a_survey_entry_for(some_account_id, 0, "Some Negative Feedback");

        //unlogged in user
        user
                .is_logged_out()
                .visits_nps_report_page_by_link();
        screen
                .shows_login()
                .should_not_contain_nps_report_link_in_header();

        //unauthorised user
        user.logs_in_with(username, password);
        screen
                .should_show_access_denied()
                .should_not_contain_nps_report_link_in_header();

        //show survey report
        user
                .logs_in_with(admin_user, password)
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

    @After
    public void tearDown() {
        admin
                .there_are_no_survey_entries()
                .there_is_no_item(frame)
                .there_is_no_account_for(admin_user)
                .there_is_no_account_for(username);
    }
}
