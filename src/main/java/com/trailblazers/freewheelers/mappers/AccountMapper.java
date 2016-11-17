package com.trailblazers.freewheelers.mappers;

import com.trailblazers.freewheelers.model.Account;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AccountMapper {

    @Insert(
        "INSERT INTO account (account_name, email_address, password, phone_number, enabled, country_id, address_id) " +
        "VALUES (#{account_name}, #{emailAddress}, #{password}, #{phone_number}, #{enabled}, #{country_id},#{address_id})"
    )
    @Options(keyProperty = "account_id", useGeneratedKeys = true)
    Integer insert(Account account);

    @Select(
            "SELECT COUNT(*)" + "FROM account "+ "WHERE account_name = #{account_name}"
    )
    Integer getCountOfAccountsByName(String accountName);


    @Select(
        "SELECT account_id, account_name, email_address, password, phone_number, enabled, country_id, address_id " +
        "FROM account " +
        "WHERE account_id = #{account_id}"
    )
    Account getById(Long account_id);

    @Select(
        "SELECT account_id, account_name, email_address, password, phone_number, enabled, country_id, address_id " +
        "FROM account " +
        "WHERE account_name = #{account_name} " +
        "LIMIT 1"
    )
    Account getByName(String accountName);

    @Update(
        "UPDATE account " +
        "SET account_name=#{account_name}, email_address=#{emailAddress}, phone_number=#{phone_number}, enabled=#{enabled}, country_id=#{country_id}, address_id=#{address_id}" +
        "WHERE account_id=#{account_id}"
    )
    void update(Account account);

    @Select(
        "SELECT account_id, account_name, email_address, password, phone_number, enabled, country_id, address_id FROM account"
    )
    @Results(value = {
            @Result(property="account_id"),
            @Result(property="account_name"),
            @Result(property="emailAddress", column="email_address"),
            @Result(property="password"),
            @Result(property="phone_number", column="phone_number"),
            @Result(property="enabled"),
            @Result(property ="country_id"),
            @Result(property ="address_id")
    })
    public List<Account> findAll();

    @Delete(
        "DELETE FROM account WHERE account_id = #{account_id}"
    )
    @Options(flushCache = true)
    void delete(Account account);


}
