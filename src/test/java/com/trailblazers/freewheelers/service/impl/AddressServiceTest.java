package com.trailblazers.freewheelers.service.impl;


import com.trailblazers.freewheelers.mappers.AddressMapper;
import com.trailblazers.freewheelers.model.Address;
import com.trailblazers.freewheelers.service.AddressService;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AddressServiceTest {
    AddressService addressService;

    @Mock
    SqlSession sqlSession;
    @Mock
    AddressMapper addressMapper;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(sqlSession.getMapper(AddressMapper.class)).thenReturn(addressMapper);


        addressService = new AddressService(sqlSession);
    }

    @Test
    public void shouldCreateAddressWhenThereAreNoValidationErrors(){
        Address address = getAddressWithoutErrors();

        addressService.createAddress(address);

        verify(addressMapper, times(1)).insert(address);
        verify(sqlSession, times(1)).commit();
    }


    private Address getAddressWithoutErrors() {
        Address address = new Address();
        address.setStreet_one("123 wall st");
        address.setStreet_two("apartment 4");
        address.setCity("Delhi");
        address.setState("Maharashtra");
        address.setPost_code("CD-12231");

        return address;
    }
}
