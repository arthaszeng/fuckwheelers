package com.trailblazers.freewheelers.web;

import com.trailblazers.freewheelers.configuration.ToggledFeature;
import com.trailblazers.freewheelers.model.Account;
import com.trailblazers.freewheelers.model.Item;
import com.trailblazers.freewheelers.service.AccountService;
import com.trailblazers.freewheelers.service.CountryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.security.Principal;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
public class CartControllerTest {

    public static final Long UK_COUNTRY_ID = 2L;
    public static final Long GERMANY_COUNTRY_ID = 3L;
    public static final Long EMPTY_COUNTRY_ID = null;

    @Mock
    AccountService accountService;

    @Mock
    CountryService countryService;

    @Mock
    Principal principal;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession session;

    Model model;

    @InjectMocks
    CartController cartController;

    Item item;

    @Before
    public void setUp() {
        initMocks(this);

        model = new ExtendedModelMap();
        cartController = new CartController(accountService, countryService);

        item = new Item();
        item.setItemId(1113920840l);
        item.setPrice(BigDecimal.valueOf(49.99));

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("items")).thenReturn(asList(item));
        when(session.getAttribute("vatPercentage")).thenReturn("20");

        when(principal.getName()).thenReturn("John");
        when(accountService.getAccountByName("John")).thenReturn(createAccountFromCountry(UK_COUNTRY_ID));
        when(countryService.getVatByCountryId(UK_COUNTRY_ID)).thenReturn(0.2);
    }

    @Test
    public void shouldSetItemInSession() throws Exception {
        if (ToggledFeature.SHOPPING_CART.isActive()) {
            cartController.get(model, request, null);
            assertTrue(model.containsAttribute("items"));
        }
    }

    @Test
    public void shouldDisplayVATForUKUser() throws Exception {
        cartController.get(model, request, principal);



        verify(session).setAttribute("vatPercentage", "20");
    }

    @Test
    public void shouldDisplaySubtotalWhenUserIsNotLoggedIn() throws Exception {
        cartController.get(model, request, null);
        verify(session).setAttribute("subTotal", "49.99");
    }

    @Test
    public void shouldDisplaySubtotal() throws Exception {
        cartController.get(model, request, principal);

        verify(session).setAttribute("subTotal", "49.99");
    }

    @Test
    public void shouldDisplayMessageForUsersWithoutCountry() throws Exception {
        when(accountService.getAccountByName("John")).thenReturn(createAccountFromCountry(EMPTY_COUNTRY_ID));

        cartController.get(model, request, principal);

        assertThat((String) model.asMap().get("missingCountryMessage"),
                containsString("additional charges may apply according to your region"));
    }

    @Test
    public void shouldDisplayMessageForNotLoggedInUser() throws Exception {
        cartController.get(model, request, null);

        assertThat((String) model.asMap().get("missingCountryMessage"),
                containsString("additional charges may apply according to your region"));
    }

    @Test
    public void shouldDisplayVATForGermanyUser() throws Exception {
        when(accountService.getAccountByName("John")).thenReturn(createAccountFromCountry(GERMANY_COUNTRY_ID));
        when(countryService.getVatByCountryId(GERMANY_COUNTRY_ID)).thenReturn(0.19);

        cartController.get(model, request, principal);

        verify(session).setAttribute("vatPercentage", "19");
    }

    private Account createAccountFromCountry(Long countryId) {
        return new Account().setCountry_id(countryId);
    }
}
