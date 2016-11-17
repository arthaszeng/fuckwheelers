package com.trailblazers.freewheelers;

import org.junit.Ignore;
import org.junit.Test;

import static com.trailblazers.freewheelers.helpers.SyntaxSugar.*;
@Ignore
public class LoginTest extends UserJourneyBase {
    @Test
    public void testLoginWithAccount() throws Exception {
        String jan = "Jan Plewka";
        admin
                .there_is_no_account_for(jan)
                .there_is_a_user(jan, SOME_PASSWORD);
        user
                .is_logged_out();
        user
                .logs_in_with(EMPTY_USERNAME, SOME_PASSWORD);
        screen
                .shows_error("Must enter a username or email.", "user_field");
        user
                .logs_in_with(SOME_EMAIL, EMPTY_PASSWORD);
        screen
                .shows_error("Must enter a password.", "password_field");
        user
                .logs_in_with(ERROR_EMAIL, SOME_PASSWORD);
        screen
                .shows_message("Your login attempt was not successful, try again.");
        user
                .logs_in_with("Jan-Plewka@random-email.com", SOME_PASSWORD);
        screen
                .shows_in_navbar("Welcome " + jan);
        user
                .is_logged_out()
                .logs_in_with(jan, SOME_PASSWORD);
        screen
                .shows_in_navbar("Welcome " + jan);

    }
}
