package com.trailblazers.freewheelers.persistence.persistence;

import com.trailblazers.freewheelers.mappers.CountryMapper;
import com.trailblazers.freewheelers.model.Country;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class CountryMapperTest extends MapperTestBase {

    public static final long UK_COUNTRY_ID = 2L;
    private CountryMapper countryMapper;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        countryMapper = getSqlSession().getMapper(CountryMapper.class);
    }

    @Test
    public void shouldGetCountryById() throws Exception {
        Country fetchedFromDB = countryMapper.getById(1L);

        assertThat(fetchedFromDB.getCountry_id(), is(1L));
    }

    @Test
    public void shouldFindAllCountries() throws Exception {
        List<Country> fetchedFromDB = countryMapper.findAll();
        assertThat(fetchedFromDB.get(0).getCountry_id(), is(1L));
        assertThat(fetchedFromDB.get(1).getName(), is("UK"));
        assertThat(fetchedFromDB.size(), is(6));
    }

    @Test
    public void shouldGetVatByCountryId() throws Exception {
        Country fetchedFromDB = countryMapper.getById(UK_COUNTRY_ID);

        assertThat(fetchedFromDB.getVat(), is(0.2));
    }
}