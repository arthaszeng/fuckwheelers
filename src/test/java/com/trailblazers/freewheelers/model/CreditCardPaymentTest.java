package com.trailblazers.freewheelers.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardPaymentTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptNegativeAmounts() throws Exception {
        CreditCardPayment payment = new CreditCardPayment();
        payment.setAmount(-55);
    }
}