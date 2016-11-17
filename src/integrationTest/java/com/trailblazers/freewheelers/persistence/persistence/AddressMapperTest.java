package com.trailblazers.freewheelers.persistence.persistence;

import com.trailblazers.freewheelers.mappers.AddressMapper;
import com.trailblazers.freewheelers.model.Address;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class AddressMapperTest  extends MapperTestBase{
    private AddressMapper addressMapper;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        addressMapper = getSqlSession().getMapper(AddressMapper.class);
    }

    @Test
    public void shouldInsertAndGetAddress() throws Exception {
        Address address = someAddress();

        addressMapper.insert(address);
        Address fetchedFromDB = addressMapper.getById(address.getAddress_id());

        assertThat(fetchedFromDB.getStreet_one(), is("123 wall st"));
        assertThat(fetchedFromDB.getStreet_two(),is("apartment 4"));
        assertThat(fetchedFromDB.getCity(),is("Delhi"));
        assertThat(fetchedFromDB.getState(),is("Maharashtra"));
        assertThat(fetchedFromDB.getPost_code(),is("CG-12345"));
    }

    @Test
    public void shouldInsertAndGetAddressByStreetOne() {
        Address address = someAddress();

        addressMapper.insert(address);
        Address fetchedFromDB = addressMapper.getByStreet_one(address.getStreet_one());

        assertThat(fetchedFromDB.getStreet_one(), is("123 wall st"));
        assertThat(fetchedFromDB.getStreet_two(),is("apartment 4"));
        assertThat(fetchedFromDB.getCity(),is("Delhi"));
        assertThat(fetchedFromDB.getState(),is("Maharashtra"));
        assertThat(fetchedFromDB.getPost_code(),is("CG-12345"));
    }

    private Address someAddress() {
        return new Address()
                .setStreet_one("123 wall st")
                .setStreet_two("apartment 4")
                .setCity("Delhi")
                .setState("Maharashtra")
                .setPost_code("CG-12345");
    }

}