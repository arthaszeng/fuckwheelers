package com.trailblazers.freewheelers.model;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AccountTest {

    @Test
    public void testCreatingNewAccounts() throws Exception {
        Account account = new Account()
                .setAccount_name("Bob")
                .setPassword("password")
                .setEmail_address("foo@bar.com")
                .setPhone_number("123443245")
                .setCountry_id((long) 1)
                .setAddress_id(12345L);


        assertThat(account.getAccount_name(), is("Bob"));
        assertThat(account.getPassword(), is("password"));
        assertThat(account.getEmail_address(), is("foo@bar.com"));
        assertThat(account.getPhone_number(), is("123443245"));
        assertThat(account.getCountry_id(), is((long) 1));
        assertThat(account.getAddress_id(), is(12345L));


    }
}
