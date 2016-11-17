package com.trailblazers.freewheelers.apis;

import com.trailblazers.freewheelers.model.*;
import com.trailblazers.freewheelers.service.*;

import java.util.Date;

import static com.trailblazers.freewheelers.helpers.SyntaxSugar.*;


public class AdminApi {

    private AccountService accountService;
    private AddressService addressService;
    private ItemService itemService;
    private ReserveOrderService reserveOrderService;
    private SurveyService surveyService;

    public AdminApi() {
        this.accountService = new AccountService();
        this.itemService = new ItemService();
        this.reserveOrderService = new ReserveOrderService();
        this.surveyService = new SurveyService();
        this.addressService = new AddressService();
    }

    public AdminApi there_are_no_survey_entries() {
        surveyService.deleteAll();
        return this;
    }

    public AdminApi there_is_no_account_for(String accountName) {
        Account account = accountService.getAccountByName(accountName);
        if (account != null) {
            accountService.delete(account);
        }

        return this;
    }

    public AdminApi there_is_a_user(String userName, String password) {

        there_is_no_account_for(userName);
        Address address = addressService.createAddress(address_for());
        Account account = account_for(userName, password);
        account.setAddress_id(address.getAddress_id());
        accountService.createAccount(account);
        return this;
    }

    public AdminApi there_is_a_user_with_country(String userName, String password, Long countryId) {
        there_is_no_account_for(userName);
        accountService.createAccount(account_with_country(userName, password, countryId));
        return this;
    }


    public AdminApi there_is_a_user_with_no_country(String userName, String password) {
        there_is_no_account_for(userName);
        Account account = account_for(userName, password);
        account.setCountry_id(null);
        accountService.createAccount(account);
        return this;
    }


    public AdminApi there_is_an_admin(String userName, String password) {
        there_is_no_account_for(userName);
        accountService.createAdmin(account_for(userName, password));

        return this;
    }

    public AdminApi there_is_no_item(String itemName) {
        Item toBeDeleted = itemService.getByName(itemName);

        while (toBeDeleted != null) {
            itemService.delete(toBeDeleted);
            toBeDeleted = itemService.getByName(itemName);
        }

        return this;
    }

    public AdminApi create_an_item(String itemName, Long quantity) {
        there_is_no_item(itemName);
        itemService.saveItem(itemFor(itemName, quantity));

        return this;
    }

    private Item itemFor(String itemName, Long quantity) {
        return new Item()
                .setName(itemName)
                .setQuantity(quantity)
                .setDescription(SOME_DESCRIPTION)
                .setPrice(SOME_PRICE)
                .setType(ItemType.FRAME);
    }

    private Account account_for(String userName, String password) {
        return new Account()
                .setAccount_name(userName)
                .setPassword(password)
                .setEmail_address(emailFor(userName))
                .setPhone_number(SOME_PHONE_NUMBER)
                .setCountry_id(SOME_COUNTRY_ID)
                .setAddress_id(SOME_ADDRESS_ID)
                .setEnabled(true);
    }

    private Address address_for(){
        return new Address()
                .setStreet_one(SOME_STREET_ONE)
                .setStreet_two(SOME_STREET_TWO)
                .setCity(SOME_CITY)
                .setState(SOME_STATE)
                .setPost_code(SOME_POST_CODE);
    }

    private Account account_with_country(String userName, String password, Long countryId) {
        Account account = account_for(userName, password);
        account.setCountry_id(countryId);
        return account;
    }

    public AdminApi there_is_a_survey_entry_for(long accountId, int feedbackType, String comment) {
        surveyService.submitSurvey(accountId, new SurveyEntry(feedbackType, comment));
        return this;
    }

    public AdminApi updates_user_phone_number(String username, String phone_number) throws CannotUpdateAccountException {

        Account account = accountService.getAccountByName(username);
        account.setPhone_number(phone_number);
        accountService.updateAccount(account);
        return this;
    }

    public AdminApi there_is_a_order_has_been_paid() {
        ReserveOrder reserveOrder = new ReserveOrder(USER_ACCOUNT_ID, ITEM_ID, new Date());
        reserveOrderService.save(reserveOrder);
        return this;
    }
}
