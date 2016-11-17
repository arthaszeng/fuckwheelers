package com.trailblazers.freewheelers.model;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static java.math.BigDecimal.valueOf;

public class CartCalculatorTest {

    public static final BigDecimal SOME_PRICE = valueOf(42.11);
    public static final BigDecimal ANOTHER_PRICE = valueOf(20.0);

    @Before
    public void setUp(){
        initMocks(this);
    }

    @Test
    public void shouldComputeSubtotalForOneItem() throws Exception {
        CartCalculator  cartCalculator = new CartCalculator(Arrays.asList(
                new Item().setPrice(SOME_PRICE)
        ));

        BigDecimal subtotal = cartCalculator.calculateSubtotal();

        assertThat(subtotal, is(SOME_PRICE));
    }

    @Test
    public void shouldComputeSubtotalForTwoItems() throws Exception {
        CartCalculator  cartCalculator = new CartCalculator(Arrays.asList(
                new Item().setPrice(SOME_PRICE),
                new Item().setPrice(ANOTHER_PRICE)
        ));

        BigDecimal subtotal = cartCalculator.calculateSubtotal();

        assertThat(subtotal, is(SOME_PRICE.add(ANOTHER_PRICE)));
    }

    @Test
    public void shouldComputeTaxForItems() throws Exception {
        CartCalculator  cartCalculator = new CartCalculator(Arrays.asList(
                new Item().setPrice(ANOTHER_PRICE),
                new Item().setPrice(ANOTHER_PRICE)
        ));

        BigDecimal actualTax = cartCalculator.calculateOverallTax(0.2);

        assertThat(actualTax.doubleValue(), is(valueOf(8).doubleValue()));
    }

    @Test
    public void shouldComputeTotalForTwoItems() throws Exception {
        CartCalculator  cartCalculator = new CartCalculator(Arrays.asList(
                new Item().setPrice(ANOTHER_PRICE),
                new Item().setPrice(ANOTHER_PRICE)
        ));

        BigDecimal actualTotal = cartCalculator.calculateTotal(0.2);

        assertThat(actualTotal.doubleValue(), is(valueOf(48).doubleValue()));
    }
}