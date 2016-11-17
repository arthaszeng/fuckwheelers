package com.trailblazers.freewheelers.model;

import com.trailblazers.freewheelers.service.CountryService;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountValidatorTest {

    public static final String SOME_EMAIL = "guenter.grass@gmail.com";
    public static final String SOME_PASSWORD = "2WSX@WSX2wsx.";
    public static final String SOME_NAME = "Günter Grass";
    public static final String SOME_PHONE = "004945542741";
    public static final String SOME_COUNTRY = "Germany";
    public static final Long SOME_COUNTRY_ID = 1L;
    public static final Long SOME_ADDRESS_ID = 12345L;
    private Account account;
    private CountryService countryService;
    private AccountValidator accountValidator;

    @Before
    public void setup() {
        account = new Account()
                .setEmail_address(SOME_EMAIL)
                .setPassword(SOME_PASSWORD)
                .setAccount_name(SOME_NAME)
                .setPhone_number(SOME_PHONE)
                .setCountry_id(SOME_COUNTRY_ID)
                .setAddress_id(SOME_ADDRESS_ID)
                .setEnabled(true);

        countryService = mock(CountryService.class);
        Country country = new Country();
        country.setCountry_id(1L);
        when(countryService.findAll()).thenReturn(Arrays.asList(country));
        List<Long> validCountryIds = Arrays.asList(1L, 2L);
        accountValidator = new AccountValidator(validCountryIds);
    }
    @Test
    public void shouldHaveNoErrorsForValidInput() throws Exception {
        HashMap errors = accountValidator.verifyInputs(account);

        assertThat(errors.size(), is(0));
    }
    
    @Test
    public void shouldComplainAboutAnInvalidEmail() throws Exception {
        String invalidEmail = "invalid.email.address";

        account.setEmail_address(invalidEmail);

        HashMap errors = accountValidator.verifyInputs(account);

        assertThereIsOneErrorFor("email", "enter a valid email", errors);
    }

    @Test
    public void shouldComplainAboutAnEmptyPassword() throws Exception {
        String emptyPassword = "";

        account.setPassword(emptyPassword);

        HashMap errors = accountValidator.verifyInputs(account);

        assertThereIsOneErrorFor("password", "enter a valid password", errors);
    }

    @Test
    public void shouldComplainAboutAnEmptyName() throws Exception {
        String emptyName = "";

        account.setAccount_name(emptyName);

        HashMap errors = accountValidator.verifyInputs(account);

        assertThereIsOneErrorFor("name", "enter a name", errors);
    }

    @Test
    public void shouldComplainAboutAnEmptyPhoneNumber() throws Exception {
        String emptyPhoneNumber = "";

        account.setPhone_number(emptyPhoneNumber);

        HashMap errors = accountValidator.verifyInputs(account);

        assertThereIsOneErrorFor("phone_number", "enter a phone number", errors);
    }

    @Test
    public void shouldComplainAboutAnEmptyCountryId() throws Exception {
        Long emptyCountryId = null;
        account.setCountry_id(emptyCountryId);

        HashMap errors = accountValidator.verifyInputs(account);

        assertThereIsOneErrorFor("country", "choose a country", errors);
    }

    @Test
    public void shouldComplainAboutAnInvalidCountryId() throws Exception {
        Long invalidCountryId = 8L;
        account.setCountry_id(invalidCountryId);

        HashMap errors = accountValidator.verifyInputs(account);

        assertThereIsOneErrorFor("country", "choose a valid country", errors);
    }

    @Test
    public void shouldComplainAboutAnEmptyAddressId() throws Exception {
        Long emptyAddressId = null;
        account.setAddress_id(emptyAddressId);

        HashMap errors = accountValidator.verifyInputs(account);

        assertThereIsOneErrorFor("address", "Please fill the address fields", errors);
    }

   @Test
    public void shouldComPlainAboutAShortPassword() throws Exception {
        String password = "@Aa1";
        account.setPassword(password);

        HashMap errors = accountValidator.verifyInputs(account);

        assertThereIsOneErrorFor("password", "Please enter a valid password", errors);
    }

    @Test
    public void shouldComPlainAboutAPasswordWithoutNumber() throws Exception {
        String password = "@Aa@Aa@Aa";
        account.setPassword(password);

        HashMap errors = accountValidator.verifyInputs(account);

        assertThereIsOneErrorFor("password", "Please enter a valid password", errors);
    }

    @Test
    public void shouldComPlainAboutAPasswordWithoutUppercaseLetter() throws Exception {
        String password = "@a1@a1@a1";
        account.setPassword(password);

        HashMap errors = accountValidator.verifyInputs(account);

        assertThereIsOneErrorFor("password", "Please enter a valid password", errors);
    }

    @Test
    public void shouldComPlainAboutAPasswordWithoutLowercaseLetter() throws Exception {
        String password = "@A1@A1@A1";
        account.setPassword(password);

        HashMap errors = accountValidator.verifyInputs(account);

        assertThereIsOneErrorFor("password", "Please enter a valid password", errors);
    }


    @Test
    public void shouldComPlainAboutAPasswordWithoutSpecialCharacters() throws Exception {
        String password = "Aa1Aa1Aa1";
        account.setPassword(password);

        HashMap errors = accountValidator.verifyInputs(account);

        assertThereIsOneErrorFor("password", "Please enter a valid password", errors);
    }

    @Test
    public void shouldComPlainAboutAPasswordWithAccentCharacters() throws Exception {
        String password = "Aa!Aa!1íã";
        account.setPassword(password);

        HashMap errors = accountValidator.verifyInputs(account);

        assertThereIsOneErrorFor("password", "Please enter a valid password", errors);
    }

    private void assertThereIsOneErrorFor(String field, String expected, HashMap<String, String> errors) {
        assertThat(errors.size(), is(1));
        assertThat(errors.get(field), containsString(expected));
    }


}
