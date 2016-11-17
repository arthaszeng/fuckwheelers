package com.trailblazers.freewheelers.service;

import com.trailblazers.freewheelers.mappers.AddressMapper;
import com.trailblazers.freewheelers.mappers.MyBatisUtil;
import com.trailblazers.freewheelers.model.Address;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private AddressMapper addressMapper;
    private SqlSession sqlSession;

    public AddressService() {
        this(MyBatisUtil.getSqlSessionFactory().openSession());
    }

    public AddressService(SqlSession sqlSession) {
        this.sqlSession= sqlSession;
        this.addressMapper = sqlSession.getMapper(AddressMapper.class);

    }

    public Address getByStreet_one(String street_one) {
        return addressMapper.getByStreet_one(street_one);
    }

    public Address getById(Long address_id) {
        return addressMapper.getById(address_id);
    }

    public Address
    createAddress(Address address) {
        addressMapper.insert(address);
        sqlSession.commit();
        return address;
    }

    public Address updateAddress(Address address){
        return addressMapper.update(address);
    }
}
