package com.trailblazers.freewheelers.web;

import com.trailblazers.freewheelers.model.Account;
import com.trailblazers.freewheelers.model.Address;
import com.trailblazers.freewheelers.model.Country;
import com.trailblazers.freewheelers.service.AccountService;
import com.trailblazers.freewheelers.service.AddressService;
import com.trailblazers.freewheelers.service.CountryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class AccountControllerTest {

    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @Mock
    private CountryService countryService;

    @Mock
    private AddressService addressService;


    @Mock
    private Principal principal;

    private Model model;
    private List<Country> countries;
    private Country country;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        accountController = new AccountController();
        accountController.accountService = accountService;
        accountController.countryService = countryService;
        model = new ExtendedModelMap();
        countries = new ArrayList<>();

        country = new Country();
        country.setName("USA");
    }

    @Test
    public void shouldReturnCountriesForDisplay() throws Exception {
        when(countryService.findAll()).thenReturn(countries);

        accountController.createAccountForm(model);

        verify(countryService).findAll();
        List<Country> returnedCountries = (List<Country>) model.asMap().get("countries");
        assertThat(returnedCountries, is(countries));
    }

    @Test
    public void shouldShowTheCreateAccountForm() throws Exception {
        ExtendedModelMap model = new ExtendedModelMap();

        ModelAndView accountForm = accountController.createAccountForm(model);

        ExtendedModelMap expectedModel = new ExtendedModelMap();
        expectedModel.put("validationMessage", model);
        assertThat(accountForm.getViewName(), is("account/create"));
        assertThat(accountForm.getModel(), is(expectedModel.asMap()));
    }

    @Test
    public void duplicateAccountNameShouldDisplayError() throws Exception {
        when(countryService.getById(any(Long.class))).thenReturn(country);

        accountController.processCreate(getValidHttpServletRequest());
        ModelAndView createView1 = accountController.processCreate(getValidHttpServletRequest());

        HashMap<String, String> errors = new HashMap<>();
        errors.put("duplicateUserName", "Account name already exists!");
        ModelMap model = new ModelMap();
        model.put("errors", errors);
        model.put("countries", countries);
        ExtendedModelMap expectedModel = new ExtendedModelMap();
        expectedModel.put("validationMessage", model);

        assertThat(createView1.getModel().get("errors"), is(expectedModel.asMap().get("errors")));
    }

    @Test
    public void successfulAccountCreationShouldShowSuccess() throws Exception {
        when(countryService.getById(any(Long.class))).thenReturn(country);

        Account account = new Account();
        account.setAccount_name("john smith");
        HttpServletRequest requestWithoutError = getValidHttpServletRequest();

        when(accountService.createAccount(any(Account.class))).thenReturn(account);

        ModelAndView createView = accountController.processCreate(requestWithoutError);

        ModelMap model = new ModelMap();
        model.put("name", "john smith");
        ExtendedModelMap expectedModel = new ExtendedModelMap();
        expectedModel.put("postedValues", model);
        assertThat(createView.getViewName(), is("account/createSuccess"));
        assertThat(createView.getModel(), is(expectedModel.asMap()));
    }

    @Test
    public void shouldCreateAnAccountFromTheHttpRequest() throws Exception {
        when(countryService.getById(any(Long.class))).thenReturn(country);

        HttpServletRequest request = getValidHttpServletRequest();

        accountController.processCreate(request);

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
        verify(accountService).createAccount(captor.capture());

        Account account = captor.getValue();
        assertThat(account.getEmail_address(), is("email@fake.com"));
        assertThat(account.getPassword(), is("@Aa1@Aa1"));
        assertThat(account.getAccount_name(), is("john smith"));
        assertThat(account.getPhone_number(), is("123456789"));
        assertThat(account.isEnabled(), is(true));
        assertThat(account.getCountry_id(), is((long) 1));
    }

    @Test
    public void accountValidationFailureShouldShowError() throws Exception {
        when(countryService.getById(any(Long.class))).thenReturn(country);

        HashMap<String, String> errors = new HashMap<>();
        errors.put("email", "Please enter a valid email");

        HttpServletRequest requestWithError = getInvalidHttpServletRequest();

        ModelAndView createView = accountController.processCreate(requestWithError);

        ModelMap model = new ModelMap();
        model.put("errors", errors);
        model.put("countries", countries);
        ExtendedModelMap expectedModel = new ExtendedModelMap();
        expectedModel.put("validationMessage", model);
        assertThat(createView.getViewName(), is("account/create"));
        assertThat(createView.getModel(), is(expectedModel.asMap()));
    }

    @Test
    public void accountCreationExceptionShouldShowError() throws Exception {
        when(countryService.getById(any(Long.class))).thenReturn(country);

        HttpServletRequest requestWithoutError = getValidHttpServletRequest();
        when(accountService.createAccount(any(Account.class))).thenThrow(new RuntimeException("validation errors"));

        ModelAndView createView = accountController.processCreate(requestWithoutError);

        assertThat(createView.getViewName(), is("account/createFailure"));
    }

    @Test
    public void shouldNotCallServiceWhenThereAreValidationErrors() throws IOException {
        when(countryService.getById(any(Long.class))).thenReturn(country);

        HttpServletRequest request = getInvalidHttpServletRequest();

        accountController.processCreate(request);

        verify(accountService, never()).createAccount(new Account());
    }

    private HttpServletRequest getInvalidHttpServletRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getParameter("email")).thenReturn("");
        when(request.getParameter("password")).thenReturn("@Aa1@Aa1");
        when(request.getParameter("name")).thenReturn("example");
        when(request.getParameter("phone_number")).thenReturn("1234567890");
        when(request.getParameter("country")).thenReturn("USA");
        when(request.getParameter("country_id")).thenReturn("1");
        when(request.getParameter("isAgree")).thenReturn("true");
        when(request.getParameter("street_one")).thenReturn("Viman Nagar");
        when(request.getParameter("street_two")).thenReturn("");
        when(request.getParameter("city")).thenReturn("Delhi97867");
        when(request.getParameter("state")).thenReturn("Maharashtra");
        when(request.getParameter("post_code")).thenReturn("710015");

        return request;
    }

    private HttpServletRequest getValidHttpServletRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getParameter("email")).thenReturn("email@fake.com");
        when(request.getParameter("password")).thenReturn("@Aa1@Aa1");
        when(request.getParameter("name")).thenReturn("john smith");
        when(request.getParameter("phone_number")).thenReturn("123456789");
        when(request.getParameter("country")).thenReturn("USA");
        when(request.getParameter("country_id")).thenReturn("1");
        when(request.getParameter("isAgree")).thenReturn("true");
        when(request.getParameter("street_one")).thenReturn("123 wall street");
        when(request.getParameter("street_two")).thenReturn("Apartment 4");
        when(request.getParameter("city")).thenReturn("Delhi");
        when(request.getParameter("state")).thenReturn("Maharashtra");
        when(request.getParameter("post_code")).thenReturn("CA_7633");

        return request;
    }



}
