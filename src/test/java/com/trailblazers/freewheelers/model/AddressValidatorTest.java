package com.trailblazers.freewheelers.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AddressValidatorTest {
    public static final String SOME_STREET_ONE = "main st 123";
    public static final String SOME_STREET_TWO = "apartment 4";
    public static final String SOME_CITY = "Delhi";
    public static final String INVALID_STREET_ONE = "(**&jhsj";
    public static final String INVALID_STREET_TWO = "(**&jhsj";
    public static final String INVALID_CITY = "";
    public static final String SOME_STATE = "Maharashtra";
    public static final String INVALID_STATE = "*&^%^%&";
    public static final String SOME_POST_CODE = "CA-5426";
    public static final String INVALID_POST_CODE = "*&^%^%&";
    public static final String SOME_COUNTRY = "USA";


    private  Address address, invalidAddress;
    private  AddressValidator addressValidator;

    @Before
    public void setUp() throws Exception {
        address = new Address()
                .setStreet_one(SOME_STREET_ONE)
                .setStreet_two(SOME_STREET_TWO)
                .setCity(SOME_CITY)
                .setState(SOME_STATE)
                .setPost_code(SOME_POST_CODE);
        invalidAddress = new Address()
                .setStreet_one(INVALID_STREET_ONE)
                .setStreet_two(INVALID_STREET_TWO)
                .setCity(INVALID_CITY)
                .setState(INVALID_STATE)
                .setPost_code(INVALID_POST_CODE);
        addressValidator = new AddressValidator();
    }

    @Test
    public void shouldHaveNoErrorsForValidInput() throws Exception {

        HashMap errors = addressValidator.verifyInputs(address, SOME_COUNTRY);

        assertThat(errors.size(), is(0));
    }

    @Test
    public void shouldHaveErrorsForInValidInput() throws Exception {
        HashMap errors = addressValidator.verifyInputs(invalidAddress, SOME_COUNTRY);

        assertThat(errors.size(), is(5));
    }


}
