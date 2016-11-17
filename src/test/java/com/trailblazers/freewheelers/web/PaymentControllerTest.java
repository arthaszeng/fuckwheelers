package com.trailblazers.freewheelers.web;

import com.trailblazers.freewheelers.model.CreditCardPayment;
import com.trailblazers.freewheelers.model.Item;
import com.trailblazers.freewheelers.model.PaymentResult;
import com.trailblazers.freewheelers.service.PaymentService;
import com.trailblazers.freewheelers.service.ReserveItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class PaymentControllerTest {
    @Mock
    private PaymentService paymentService;
    @Mock
    private ReserveItemService reserveItemService;

    private HttpServletRequest requestWithoutError;

    @InjectMocks
    private PaymentController paymentController;
    private Principal principal;

    @Before
    public void setup() throws Exception {
        initMocks(this);
        principal = mock(Principal.class);
        requestWithoutError = getValidHttpServletRequest();
    }

    @Test
    public void shouldShowTheEnterCreditCardDetailsForm() throws Exception {
        ModelAndView modelAndView = paymentController.details(getValidHttpServletRequest());

        assertThat(modelAndView.getViewName(), is("payment/details"));
    }

    @Test
    public void shouldForwardToSuccessPageAfterSuccessfulPayment() throws Exception {
        HashMap<String, String> error = new HashMap<>();

        when(paymentService.checkOut(any(CreditCardPayment.class))).thenReturn(error);

        ModelAndView successView = paymentController.checkout(requestWithoutError, principal);

        ModelMap model = new ModelMap();
        model.put("returnState", PaymentResult.SUCCESS);
        ExtendedModelMap expectedModel = new ExtendedModelMap();
        expectedModel.put("receivedResult", model);

        verify(reserveItemService, times(1)).reserveItems(anyListOf(Item.class), anyString());
        verify(paymentService).checkOut(any(CreditCardPayment.class));

        assertThat(successView.getViewName(), is("/payment/success"));
        assertThat(successView.getModel(), is(expectedModel.asMap()));
        assertNull(requestWithoutError.getSession().getAttribute("items"));
    }

    @Test
    public void shouldProceedPaymentFromHttpRequest() throws Exception {
        ArgumentCaptor<CreditCardPayment> captor = ArgumentCaptor.forClass(CreditCardPayment.class);
        paymentController.checkout(requestWithoutError, principal);

        verify(paymentService).checkOut(captor.capture());
        CreditCardPayment payment = captor.getValue();

        assertThat(payment.getCardNumber(), is("4111111111111111"));
        assertThat(payment.getCsc(), is("534"));
        assertThat(payment.getExpiryDate(), is("11-2020"));
        assertThat(payment.getAmount(), is(100.00));
    }

    @Test
    public void shouldShowErrorInCaseOfMalformedCreditCardDetails() throws Exception {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("creditCard", "Must enter a valid credit card number");
        when(paymentService.checkOut(any(CreditCardPayment.class))).thenReturn(errors);

        HttpServletRequest requestWithError = getHttpServletRequestWithMalformedCreditCardDetails();

        ModelAndView failView = paymentController.checkout(requestWithError, principal);

        verify(paymentService).checkOut(any(CreditCardPayment.class));

        ModelMap model = new ModelMap();
        model.put("errors", errors);
        ExtendedModelMap expectedModel = new ExtendedModelMap();
        expectedModel.put("validationMessage", model);

        assertThat(failView.getViewName(), is("payment/details"));
        assertThat(failView.getModel(), is(expectedModel.asMap()));
    }

    @Test
    public void shouldShowErrorInCaseOfNetworkError() throws Exception {
        HashMap<String, String> errors = new HashMap<>();
        errors.put(PaymentResult.NET_ERR.toString(), "An unexpected error occurred on the server");

        when(paymentService.checkOut(any(CreditCardPayment.class))).thenReturn(errors);

        ModelAndView failView = paymentController.checkout(requestWithoutError, principal);

        ModelMap model = new ModelMap();
        model.put("errors", errors);
        ExtendedModelMap expectedModel = new ExtendedModelMap();
        expectedModel.put("validationMessage", model);
        assertThat(failView.getViewName(), is("payment/details"));
        assertThat(failView.getModel(), is(expectedModel.asMap()));

    }

    private HttpServletRequest getHttpServletRequestWithMalformedCreditCardDetails() {
        return getHttpServletRequest("41114111");
    }

    private HttpServletRequest getValidHttpServletRequest() {
        return getHttpServletRequest("4111111111111111");
    }

    private HttpServletRequest getHttpServletRequest(String creditCardNumber) {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("totalPrice")).thenReturn("100.00");
        when(request.getParameter("cardNumber")).thenReturn(creditCardNumber);
        when(request.getParameter("expiryMonth")).thenReturn("11");
        when(request.getParameter("expiryYear")).thenReturn("2020");
        when(request.getParameter("csc")).thenReturn("534");
        when(request.getParameter("type")).thenReturn("VISA");
        when(request.getSession()).thenReturn(session);
        return request;
    }


}