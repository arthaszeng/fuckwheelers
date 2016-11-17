package com.trailblazers.freewheelers.service;

import com.trailblazers.freewheelers.mappers.CountryMapper;
import com.trailblazers.freewheelers.model.Country;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CountryServiceTest {

    @Mock
    CountryMapper countryMapper;

    @Mock
    SqlSession sqlSession;

    CountryService countryService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(sqlSession.getMapper(CountryMapper.class)).thenReturn(countryMapper);
        countryService = new CountryService(sqlSession);
    }

    @Test
    public void shouldGetCountryById() throws Exception {
        Country expectedCountry = new Country();
        when(countryMapper.getById(0L)).thenReturn(expectedCountry);

        Country returnedCountry = countryService.getById(0L);

        verify(countryMapper).getById(0L);
        assertThat(returnedCountry, is(expectedCountry));
    }

    @Test
    public void shouldGetVatById() throws Exception {
        double expectedVat = 0.2;
        Country expectedCountry = new Country().setVat(expectedVat);
        when(countryMapper.getById(0L)).thenReturn(expectedCountry);

        double actualVat = countryService.getVatByCountryId(0L);

        verify(countryMapper).getById(0L);
        assertThat(actualVat, is(expectedVat));
    }

    @Test
    public void shouldFindAllCountries() throws Exception {
        when(countryMapper.findAll()).thenReturn(new ArrayList<Country>());

        countryService.findAll();

        verify(countryMapper).findAll();
    }
}