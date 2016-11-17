package com.trailblazers.freewheelers.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static com.trailblazers.freewheelers.model.CreditCardPaymentValidator.*;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

public class CreditCardPaymentValidatorTest {

    private CreditCardPayment creditCard;

    @Before
    public void setUp() throws Exception {
        creditCard = new CreditCardPayment();
        creditCard.setCardNumber("4111111111111111");
        creditCard.setAmount(52.04);
        creditCard.setCsc("534");
        creditCard.setExpiryDate("11-2020");
        creditCard.setType(CreditCardType.VISA);
    }

    @Test
    public void shouldComplainAboutInvalidCreditCardNumberLength() throws Exception {
        creditCard.setCardNumber("10293");
        HashMap errors = verifyInputs(creditCard);
        assertThereIsOneErrorFor("creditCard", "enter a valid credit card number", errors);
    }

    @Test
    public void shouldComplainWhenTheCreditCardContainsNonDigits() throws Exception {
        creditCard.setCardNumber("411111111A111111");
        HashMap errors = verifyInputs(creditCard);
        assertThereIsOneErrorFor("creditCard", "enter a valid credit card number", errors);
    }

    @Test
    public void shouldComplainAboutInvalidCVV() throws Exception {
        creditCard.setType(CreditCardType.VISA);
        creditCard.setCsc("1934");
        HashMap errors = verifyInputs(creditCard);
        assertThereIsOneErrorFor("csc", "enter a valid cvv", errors);
    }

    @Test
    public void shouldComplainAboutInvalidCID() throws Exception {
        creditCard.setType(CreditCardType.AMEX);
        creditCard.setCardNumber("411111111111111");
        creditCard.setCsc("123");
        HashMap errors = verifyInputs(creditCard);
        assertThereIsOneErrorFor("csc", "enter a valid cid", errors);
    }

    @Test
    public void shouldComplainAboutInvalidExpiryDate() throws Exception {
        creditCard.setExpiryDate("324523543-3234");
        HashMap errors = verifyInputs(creditCard);
        assertThereIsOneErrorFor("expiry", "enter a valid expiry date", errors);
    }

    @Test
    public void shouldNotComplainAboutValidExpiryDate() throws Exception {
        creditCard.setExpiryDate("12-2020");
        HashMap errors = verifyInputs(creditCard);
        assertThat(errors.size(), is(0));
    }

    @Test
    public void shouldComplainAboutNullType() throws Exception {
        creditCard.setType(null);
        HashMap errors = verifyInputs(creditCard);
        assertThereIsOneErrorFor("type", "enter a valid type", errors);
    }

    private void assertThereIsOneErrorFor(String field, String expected, HashMap<String, String> errors) {
        assertThat(errors.size(), is(1));
        assertThat(errors.get(field), containsString(expected));
    }

}