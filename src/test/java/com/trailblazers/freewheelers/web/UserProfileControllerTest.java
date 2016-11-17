package com.trailblazers.freewheelers.web;

import com.trailblazers.freewheelers.model.Account;
import com.trailblazers.freewheelers.model.Address;
import com.trailblazers.freewheelers.model.Country;
import com.trailblazers.freewheelers.model.ReserveOrder;
import com.trailblazers.freewheelers.service.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserProfileControllerTest {

    private UserProfileController userProfileController;

    @Mock
    private CountryService countryService;

    @Mock
    private AccountService accountService;

    @Mock
    private ItemService itemService;

    @Mock
    private ReserveOrderService reserveOrderService;


    private Model model;

    @Mock
    private AddressService addressService;

    @Mock
    private Principal principal;

    @Mock
    private HttpServletRequest request;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        userProfileController = new UserProfileController(accountService, reserveOrderService,
                itemService, countryService, addressService);
        model = new ExtendedModelMap();
    }

    @Test
    public void shouldReturnCountryForDisplay() throws Exception {
        Country country = new Country().setCountry_id(1L);
        Account account = new Account().setCountry_id(1L);
        when(countryService.getById(1L)).thenReturn(country);
        when(accountService.getAccountByName("John")).thenReturn(account);
        when(reserveOrderService.findAllOrdersByAccountId(account.getAccount_id())).thenReturn(new ArrayList<ReserveOrder>());

        userProfileController.get("John", model, null);

        verify(countryService).getById(country.getCountry_id());
        Country returnedCountry = (Country) model.asMap().get("country");
        assertThat(returnedCountry, is(country));
    }

    @Test
    public void shouldReturnAddressForDisplay() throws Exception {
        Address address = new Address().setAddress_id(12345L);
        Account account = new Account().setAddress_id(12345L);

        when(addressService.getById(12345L)).thenReturn(address);
        when(accountService.getAccountByName("John")).thenReturn(account);
        when(reserveOrderService.findAllOrdersByAccountId(account.getAccount_id())).thenReturn(new ArrayList<ReserveOrder>());

        userProfileController.get("John", model, null);

        verify(addressService).getById(address.getAddress_id());
        Address returnedAddress =(Address) model.asMap().get("address");
        assertThat(returnedAddress, is(address));
    }

    @Test
    public void shouldReturnNoAddressForDisplayWhenAddressIsNull() throws Exception {
        Account account = new Account().setAddress_id(null);

        when(accountService.getAccountByName("John")).thenReturn(account);
        when(reserveOrderService.findAllOrdersByAccountId(account.getAccount_id())).thenReturn(new ArrayList<ReserveOrder>());

        userProfileController.get("John", model, null);

        Address returnedAddress =(Address) model.asMap().get("address");
        assertEquals(returnedAddress,null);
    }

    @Test
    public void shouldReturnNoCountryForDisplayWhenCountryIdIsNull() throws Exception {
        Account account = new Account().setCountry_id(null);
        when(accountService.getAccountByName("John")).thenReturn(account);
        when(reserveOrderService.findAllOrdersByAccountId(account.getAccount_id())).thenReturn(new ArrayList<ReserveOrder>());

        userProfileController.get("John", model, null);

        Country returnedCountry = (Country) model.asMap().get("country");
        assertThat(returnedCountry, is(Country.NO_COUNTRY));
    }

    @Test
    public void shouldShowUpdatedUserDetails() throws Exception {
        when(principal.getName()).thenReturn("Daft");
        when(accountService.getAccountByName("Daft")).thenReturn(new Account().setAddress_id(1L));
        when(addressService.getById(1L)).thenReturn(new Address());
        when(countryService.getById(1L)).thenReturn(new Country().setCountry_id(1L));
        initializeRequest();

        ModelAndView modelAndView = userProfileController.update(request, model, principal);

        Account account = (Account)(((Map)modelAndView.getModel().get("user-details")).get("account"));
        Address address = (Address)(((Map)modelAndView.getModel().get("user-details")).get("address"));
        Country country = (Country)(((Map)modelAndView.getModel().get("user-details")).get("country"));
        assertThat(account.getPhone_number(), is("123456"));
        assertThat(account.getCountry_id(), is(1L));
        assertThat(address.getStreet_one(), is("Bakerstreet"));
        assertThat(address.getStreet_two(), is(""));
        assertThat(address.getCity(), is("London"));
        assertThat(address.getState(), is("Washington"));
        assertThat(address.getPost_code(), is("1234"));
        assertThat(country.getCountry_id(), is(1L));
    }

    @Test
    public void shouldMakeUpdatePersistent() throws Exception, CannotUpdateAccountException {
        Account account = new Account().setAddress_id(1L);
        account.setPhone_number("98765");
        when(principal.getName()).thenReturn("Daft");
        when(accountService.getAccountByName("Daft")).thenReturn(account);
        when(addressService.getById(1L)).thenReturn(new Address());
        initializeRequest();

        ModelAndView modelAndView = userProfileController.update(request, model, principal);

        verify(accountService).updateAccount(account);
    }

    private void initializeRequest() {
        when(request.getParameter("phone_number")).thenReturn("123456");
        when(request.getParameter("country_id")).thenReturn("1");
        when(request.getParameter("street_one")).thenReturn("Bakerstreet");
        when(request.getParameter("street_two")).thenReturn("");
        when(request.getParameter("city")).thenReturn("London");
        when(request.getParameter("state")).thenReturn("Washington");
        when(request.getParameter("post_code")).thenReturn("1234");
    }
}