package com.trailblazers.freewheelers.apis;

import com.google.common.base.Function;
import com.trailblazers.freewheelers.helpers.HomeTable;
import com.trailblazers.freewheelers.helpers.ManageItemTable;
import com.trailblazers.freewheelers.helpers.OrderTable;
import com.trailblazers.freewheelers.helpers.URLs;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static com.trailblazers.freewheelers.helpers.Controls.*;

public class UserApi {

    private final String mainPageWindowHandle;
    private WebDriver driver;

    public UserApi(WebDriver driver) {
        this.driver = driver;
        this.mainPageWindowHandle = driver.getWindowHandle();
    }

    public UserApi is_logged_out() {
        driver.get(URLs.logout());
        driver.findElement(By.linkText("Logout")).click();
        return this;
    }

    public UserApi logs_in_with(String userName, String userPassword) {
        driver.get(URLs.login());

        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.name("j_username")));
        driver.findElement(By.name("j_username")).sendKeys(userName);
        driver.findElement(By.name("j_password")).sendKeys(userPassword);

        driver.findElement(By.name("submit")).click();

        return this;
    }

    public UserApi creates_an_account(String name, String email, String password, String phone_number, String confirmedPassword, String country, String streetOne, String streetTwo, String city, String state, String postCode, boolean isCheckAgreement) {

        fill_fields_for_account_creation(name, email, password, phone_number, confirmedPassword, country, streetOne, streetTwo, city, state, postCode);
        check_agreement(isCheckAgreement);
        driver.findElement(By.id("createAccount")).click();

        return this;
    }

    public UserApi fills_username_field(String name) {

        driver.get(URLs.home());
        driver.findElement(By.linkText("Create Account")).click();

        fillField(driver.findElement(By.id("fld_name")), name);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("$('#fld_name').blur()");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    public UserApi give_user_details(String name, String email, String password, String phone_number, String confirmedPassword, String country, String streetOne, String streetTwo, String city, String state, String postCode) {
        fill_fields_for_account_creation(name, email, password, phone_number, confirmedPassword, country, streetOne, streetTwo, city, state, postCode);

        return this;
    }

    private void fill_fields_for_account_creation(String name, String email, String password, String phone_number, String confirmedPassword, String country, String streetOne, String streetTwo, String city, String state, String postCode) {
        driver.get(URLs.home());
        driver.findElement(By.linkText("Create Account")).click();


        fillField(driver.findElement(By.id("fld_email")), email);
        fillField(driver.findElement(By.id("fld_password")), password);
        fillField(driver.findElement(By.id("fld_name")), name);
        fillField(driver.findElement(By.id("fld_phone_number")), phone_number);
        fillField(driver.findElement(By.id("fld_confirmedPassword")), confirmedPassword);
        select(driver.findElement(By.id("fld_country")), country);
        fillField(driver.findElement(By.id("fld_street_one")), streetOne);
        fillField(driver.findElement(By.id("fld_street_two")), streetTwo);
        fillField(driver.findElement(By.id("fld_city")), city);
        fillField(driver.findElement(By.id("fld_state")), state);
        fillField(driver.findElement(By.id("fld_post_code")), postCode);
    }

    public UserApi creates_an_item(String name, String type, String quantity, String price, String description) {
        fillField(driver.findElement(By.id("name")), name);
        fillField(driver.findElement(By.id("price")), price);

        Select select = new Select(driver.findElement(By.id("type")));
        select.selectByVisibleText(type);

        fillField(driver.findElement(By.id("description")), description);
        fillField(driver.findElement(By.id("quantity")), quantity);

        driver.findElement(By.id("createItem")).click();

        return this;
    }

    public UserApi visits_his_profile() {
        driver.findElement(By.linkText("Account Details")).click();
        return this;
    }

    public UserApi visits_admin_profile() {
        driver.findElement(By.linkText("Admin Profile")).click();
        return this;
    }

    public UserApi visits_nps_report_page() {
        driver.findElement(By.linkText("NPS Report")).click();
        return this;
    }

    public UserApi visits_nps_report_page_by_link() {
        driver.get(URLs.surveyReport());
        return this;
    }

    public UserApi visits_profile_for(String name) {
        driver.get(URLs.userProfile() + "/" + encoded(name));
        return this;
    }

    public UserApi visits_home_page() {
        driver.get(URLs.home());
        return this;
    }

    public UserApi clicks_checkout_button() {
        driver.findElement(By.id("checkOut")).click();
        return this;
    }

    public UserApi visits_manage_items_page() {
        driver.get(URLs.admin());
        driver.findElement(By.id("manageItems")).click();
        return this;
    }

    public UserApi changes_item_name(String from, String to) {
        check(driver.findElement(ManageItemTable.toggleAll()));

        WebElement input = driver.findElement(ManageItemTable.nameFieldFor(from));
        fillField(input, to);

        driver.findElement(By.name("update")).click();

        return this;
    }

    public UserApi delete_item(String itemName) {
        check(driver.findElement(ManageItemTable.checkBoxFor(itemName)));
        driver.findElement(By.name("delete")).click();

        return this;
    }

    public UserApi add_item_to_cart(String name) {
        driver.findElement(HomeTable.reserveButtonFor(name)).click();
        return this;
    }

    public UserApi changes_order_status(String item, String toState) {
        select(driver.findElement(OrderTable.selectFor(item)), toState);
        driver.findElement(OrderTable.saveButtonFor(item)).click();

        return this;
    }

    private String encoded(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public UserApi waits_for_survey_popup() throws InterruptedException {
        waitForSurveyToShow();
        focusOnPopUpWindow();
        return this;
    }

    private void focusOnPopUpWindow() {
        Iterator<String> handleIterator = driver.getWindowHandles().iterator();
        String popupHandle = handleIterator.next();
        popupHandle = popupHandle.equals(mainPageWindowHandle) ? handleIterator.next() : popupHandle;
        driver.switchTo().window(popupHandle);
    }

    private void waitForSurveyToShow() {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(5, TimeUnit.SECONDS)
                .pollingEvery(100, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        wait.until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                return driver.getWindowHandles().size() > 1;
            }
        });
    }

    public UserApi give_login_credentials(String userName, String userPassword) {
        driver.get(URLs.login());

        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.name("j_username")));
        driver.findElement(By.name("j_username")).sendKeys(userName);
        driver.findElement(By.name("j_password")).sendKeys(userPassword);

        return this;
    }

    public void check_agreement(boolean isCheckAgreement) {
        if (isCheckAgreement) {
            check(driver.findElement(By.id("fld_agreement")));
        }
    }

    public UserApi visits_shopping_cart() {
        driver.findElement(By.linkText("Shopping Cart")).click();
        return this;
    }

    public UserApi cancels_order() {
        driver.findElement(By.id("cancel")).click();
        return this;
    }

    public UserApi cancels_order(int elementNumber) {
        List<WebElement> cancelButtons = driver.findElements(By.name("cancel"));
        cancelButtons.get(elementNumber).click();
        return this;
    }


    public UserApi visits_payment_page(double totalPrice) {
        driver.get(URLs.paymentPage() + "?total_price=" + totalPrice);
        return this;
    }

    public UserApi enters_credit_card_number(String creditCardNumber) {
        fillField(driver.findElement(By.id("fld_credit_card_number")), creditCardNumber);
        return this;
    }

    public UserApi enters_expiry_month(String month) {
        fillField(driver.findElement(By.id("fld_expiry_month")), month);
        return this;
    }

    public UserApi enters_expiry_year(String year) {
        fillField(driver.findElement(By.id("fld_expiry_year")), year);
        return this;
    }

    public UserApi enters_csc(String csc) {
        fillField(driver.findElement(By.id("fld_csc")), csc);
        return this;
    }

    public UserApi reserves_item(String name) {
        driver.findElement(HomeTable.reserveButtonFor(name)).click();
        return this;
    }

    public UserApi edit_user_detail() {
        driver.findElement(By.id("edit-detail")).click();
        return this;
    }

    public UserApi checks_out() {
        driver.findElement(By.id("checkOut")).click();
        return this;
    }

    public UserApi pay_now() {
        driver.findElement(By.id("payNow")).click();
        return this;
    }

    public UserApi selects_card_type(String cardType) {
        select(driver.findElement(By.id("fld_card_type")), cardType);
        return this;
    }

    public UserApi logs_in() {
        driver.findElement(By.linkText("Login")).click();
        return this;
    }

    public UserApi clicks_continue_shopping() {
        driver.findElement(By.linkText("Continue Shopping")).click();
        return this;
    }

    public UserApi cancel_edit() {
        driver.findElement(By.id("cancel-edit")).click();
        return this;
    }

    public UserApi change_phone_number(String phone_number) {
        fillField(driver.findElement(By.id("fld_phone_number")), phone_number);
        return this;
    }

    public UserApi save_detail() {
        driver.findElement(By.id("save-detail")).click();
        return this;
    }

    public UserApi gives_credit_card_details() {
        enters_credit_card_number("4111111111111111");
        enters_csc("534");
        enters_expiry_month("11");
        enters_expiry_year("2020");
        selects_card_type("Visa");
        return this;
    }

    public UserApi submits() {
        driver.findElement(By.id("payNow")).click();
        return this;
    }

    public UserApi click_one_order_for_some_user(String username) {
        driver.findElement(By.linkText(username)).click();
        return this;
    }

}
