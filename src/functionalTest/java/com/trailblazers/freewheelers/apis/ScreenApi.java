package com.trailblazers.freewheelers.apis;

import com.trailblazers.freewheelers.helpers.HomeTable;
import com.trailblazers.freewheelers.helpers.ManageItemTable;
import com.trailblazers.freewheelers.helpers.URLs;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringContains.containsString;

public class ScreenApi {
    private WebDriver driver;

    public ScreenApi(WebDriver driver) {
        this.driver = driver;
    }

    public void shows_error_alert(String expectedMessage) {
        expectMessageWithClass(expectedMessage, "error");
    }

    public void shows_error(String expectedMessage, String id) {
        expectMessageWithField(expectedMessage, "text-error", id);
    }

    public void shows_different_error(String expectedMessage, String type, String id){
        expectMessageWithField(expectedMessage, type, id);
    }


    public void shows_message(String expectedMessage) {
        expectMessageWithClass(expectedMessage, "page-action");
    }

    public void shows_in_navbar(String expectedMessage) {
        expectMessageWithClass(expectedMessage, "navbar-text");
    }


    public ScreenApi shows_profile_for(String name) {
        String userDetails = driver.findElement(By.id("user-details")).getText();

        assertThat(userDetails, containsString(name));
        return this;
    }

    public ScreenApi shows_country(String expectedCountry) {
        String country = driver.findElement(By.id("user-details")).getText();

        assertThat(country, containsString(expectedCountry));
        return this;
    }


    public ScreenApi shows_address_for(String street_one) {
        String userDetails = driver.findElement(By.id("user-details")).getText();

        assertThat(userDetails, containsString(street_one));
        return this;
    }

    public ScreenApi shows_login() {
        assertThat(driver.getCurrentUrl(), is(URLs.login()));
        return this;
    }

    public ScreenApi shows_admin_profile() {
        assertThat(driver.getCurrentUrl(), is(URLs.admin()));
        return this;
    }

    public ScreenApi shows_in_manage_item_list(String item) {
        assertNumberOfRows(1, ManageItemTable.nameFieldFor(item));
        return this;
    }

    public ScreenApi shows_not_in_manage_item_list(String item) {
        assertNumberOfRows(0, ManageItemTable.nameFieldFor(item));
        return this;
    }

    public ScreenApi should_list_item(String item) {
        assertNumberOfRows(1, HomeTable.getNameFieldFor(item));
        return this;
    }

    public ScreenApi should_not_list_item(String item) {
        assertNumberOfRows(0, HomeTable.getNameFieldFor(item));
        return this;
    }

    private void assertNumberOfRows(int expectedRows, By selector) {
        List<WebElement> elements = driver.findElements(selector);
        assertThat(elements.size(), is(expectedRows));
    }

    private ScreenApi expectMessageWithClass(String expectedMessage, String messageClass) {
        String errorMessage = driver.findElement(By.className(messageClass)).getText();

        assertThat(errorMessage, containsString(expectedMessage));
        return this;
    }

    private ScreenApi expectMessageWithField(String expectedMessage, String messageClass, String fieldId) {
        String selector = "#" + fieldId + " ." + messageClass;
        String errorMessage = driver.findElement(By.cssSelector(selector)).getText();

        assertThat(errorMessage, containsString(expectedMessage));
        return this;
    }

    public ScreenApi should_show_access_denied() {
        assertThat(driver.getPageSource(), containsString("403 Your access is denied"));
        return this;
    }

    public ScreenApi should_not_contain_nps_report_link_in_header() {
        assertThat(driver.findElements(By.linkText("NPS Report")).size(), is(0));
        return this;
    }


    public ScreenApi should_mask_the_password_given_by_user_in_login() {
        assertThat(driver.findElement(By.name("j_password")).getAttribute("type"), is("password"));
        assertThat(driver.findElement(By.name("j_password")).getAttribute("value").length(), is(12));
        return this;
    }


    public ScreenApi should_mask_the_password_given_by_user_in_create_account_page() {
        assertThat(driver.findElement(By.id("fld_password")).getAttribute("type"), is("password"));
        assertThat(driver.findElement(By.id("fld_password")).getAttribute("value").length(), is(12));
        return this;
    }

    public ScreenApi shows_home_screen() {
        assertThat(driver.getCurrentUrl(), is((URLs.home())));
        return this;
    }

    public ScreenApi shows_added_to_cart_message(String message) {
        expectMessageWithClass(message, "added-to-cart");
        return this;
    }

    public ScreenApi shows_shopping_cart_screen() {
        assertThat(driver.getCurrentUrl(), is((URLs.cart())));
        return this;
    }

    public ScreenApi shows_payment_screen() {
        String headline = driver.findElement(By.className("page-action")).getText();
        assertThat(headline, containsString("Order Summary"));
        return this;
    }

    public ScreenApi shows_total_price(String expectedTotalPrice) {
        String totalPrice = driver.findElement(By.id("total-price-value")).getText();
        assertThat(expectedTotalPrice, is(totalPrice));
        return this;
    }

    public ScreenApi shows_purchase_confirmation() {
        String confirmationMessage = driver.findElement(By.className("page-action")).getText();
        assertThat(confirmationMessage, is("Thank you for purchasing from Freewheelers!"));
        return this;
    }

    public ScreenApi shows_result_message_after_account_creation(String expectedString) {
        String confirmationMessage = driver.findElement(By.id("resultMessage")).getText();
        assertThat(confirmationMessage, containsString(expectedString));
        return this;
    }

    public ScreenApi shows_taxes_for_uk() {
        String tax = driver.findElement(By.id("vat_label")).getText();
        assertThat(tax, containsString("VAT (20%)"));
        return this;
    }

    public ScreenApi shows_no_taxes() {
        String tax = driver.findElement(By.id("missingCountryMessage")).getText();
        assertThat(tax, containsString("additional charges may apply according to your region"));
        return this;
    }

    public ScreenApi shows_taxes_for_germany() {
        String tax = driver.findElement(By.id("vat_label")).getText();
        assertThat(tax, containsString("VAT (19%)"));
        return this;
    }

    public ScreenApi shows_checkout_button() {
        assertThat(driver.findElement(By.id("checkOut")).getText(), containsString("Check Out"));
        return this;
    }

    public ScreenApi shows_no_reserve_button() {
        assertThat(driver.findElement(By.tagName("body")).getText(), not(containsString("Reserve")));
        return this;
    }

    public ScreenApi shows_subtotal_price(String expectedSubtotal) {
        String actualSubtotal = driver.findElement(By.id("sub-total-price-value")).getText();
        assertThat(expectedSubtotal, is(actualSubtotal));
        return this;
    }

    public ScreenApi shows_no_checkout_button() {
        assertThat(driver.findElement(By.tagName("body")).getText(), not(containsString("Check Out")));
        return this;
    }

    public ScreenApi does_not_show_admin_link() {
        assertThat(driver.findElement(By.tagName("body")).getText(), not(containsString("Admin Profile")));
        return this;
    }
    public ScreenApi shows_phone_number_for(String phone_number) {
        String userDetails = driver.findElement(By.id("user-details")).getText();

        assertThat(userDetails, containsString(phone_number));
        return this;
    }
}
