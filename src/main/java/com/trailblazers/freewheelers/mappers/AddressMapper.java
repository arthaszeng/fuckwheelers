package com.trailblazers.freewheelers.mappers;


import com.trailblazers.freewheelers.model.Address;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AddressMapper {
    @Insert(
            "INSERT INTO address (street_one, street_two, city, state, post_code) " +
                    "VALUES (#{street_one}, #{street_two}, #{city}, #{state}, #{post_code})"
    )
    @Options(keyProperty = "address_id", useGeneratedKeys = true)
    Integer insert(Address address);

    @Select(
            "SELECT address_id, street_one, street_two, city, state, post_code " +
                    "FROM address " +
                    "WHERE address_id = #{address_id}"
    )
    Address getById(Long address_id);

    @Select(
            "SELECT address_id, street_one, street_two, city, state, post_code " +
                    "FROM address " +
                    "WHERE street_one = #{street_one}" +
                    "LIMIT 1"
    )

    Address getByStreet_one(String street_one);

    @Update(
            "UPDATE address " +
                    "SET street_one=#{street_one}, street_two=#{street_two}, city=#{city}, state=#{state}, post_code=#{post_code}}" +
                    "WHERE address_id=#{address_id}"
    )
    Address update(Address address);
}
