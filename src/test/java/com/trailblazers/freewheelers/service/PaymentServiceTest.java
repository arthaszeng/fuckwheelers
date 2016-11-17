package com.trailblazers.freewheelers.service;

import com.trailblazers.freewheelers.configuration.ApplicationConfiguration;
import com.trailblazers.freewheelers.model.CreditCardPayment;
import com.trailblazers.freewheelers.model.CreditCardType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PaymentServiceTest {

    @Mock
    ApplicationConfiguration applicationConfiguration;
    @Mock
    RestTemplate restTemplate;

    @Mock
    ResponseEntity responseEntity;

    @InjectMocks
    PaymentService paymentService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        paymentService = new PaymentService(applicationConfiguration);
        when(applicationConfiguration.getPaymentGatewayUrl()).thenReturn("https://test-payment-gateway.freewheelers.bike/authorise");
    }


    @Test
    public void shouldReturnSuccessStateForValidPayment() throws Exception {
        String responseText = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<authorisation-response><SUCCESS id='831618995'/></authorisation-response>";

        when(responseEntity.getBody()).thenReturn(responseText);

        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(String.class))).thenReturn(responseEntity);
        paymentService.setRestTemplate(restTemplate);

        CreditCardPayment payment = getValidCreditCardPayment();
        HashMap<String, String> errors = paymentService.checkOut(payment);

        assertTrue(errors.isEmpty());
    }

    @Test
    public void shouldReturnFailStateForInvalidPayment() throws Exception {
        String responseText = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                              "<authorisation-response><UNAUTH id='831618994'/></authorisation-response>";

        when(responseEntity.getBody()).thenReturn(responseText);

        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(String.class))).thenReturn(responseEntity);
        paymentService.setRestTemplate(restTemplate);

        CreditCardPayment payment = getValidCreditCardPayment();
        payment.setAmount(1000000);
        HashMap<String, String> errors = paymentService.checkOut(payment);

        assertFalse(errors.isEmpty());
        assertEquals("The charge on this card was declined. This could be due to invalid card information, or on reaching the card limit.", errors.get("UNAUTH"));
    }

    private CreditCardPayment getValidCreditCardPayment() {
        return new CreditCardPayment()
                .setCardNumber("4111111111111111")
                .setCsc("534")
                .setType(CreditCardType.VISA)
                .setExpiryDate("11-2020")
                .setAmount(52.04);
    }

    private CreditCardPayment getCreditCardPaymentWhichCausesRvkCardState() {
        return new CreditCardPayment()
                .setCardNumber("4111111111111116")
                .setCsc("534")
                .setType(CreditCardType.VISA)
                .setExpiryDate("11-2020")
                .setAmount(52.04);
    }

}