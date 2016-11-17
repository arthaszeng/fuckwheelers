package com.trailblazers.freewheelers.mappers;

import com.trailblazers.freewheelers.model.Country;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CountryMapper {
    @Select(
            "SELECT country_id, name, vat FROM country ORDER BY country_id"
    )
    @Results(value = {
            @Result(property="country_id", column = "country_id"),
            @Result(property="name"),
            @Result(property="vat")
    })
    List<Country> findAll();


    @Select(
            "SELECT country_id, name, vat " +
                    "FROM country " +
                    "WHERE country_id = #{country_id}"
    )
    Country getById(Long country_id);

}
