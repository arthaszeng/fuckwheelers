package com.trailblazers.freewheelers.service;

import com.trailblazers.freewheelers.mappers.CountryMapper;
import com.trailblazers.freewheelers.mappers.MyBatisUtil;
import com.trailblazers.freewheelers.model.Country;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final SqlSession sqlSession;
    private CountryMapper countryMapper;

    public CountryService() {
        this(MyBatisUtil.getSqlSessionFactory().openSession());
    }

    public CountryService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
        this.countryMapper = this.sqlSession.getMapper(CountryMapper.class);
    }

    public Country getById(Long country_id) throws IllegalArgumentException {
        return countryMapper.getById(country_id);
    }

    public double getVatByCountryId(Long country_id){
        return countryMapper.getById(country_id).getVat();
    }

    public List<Country> findAll() {
        sqlSession.clearCache();
        return countryMapper.findAll();
    }
}
