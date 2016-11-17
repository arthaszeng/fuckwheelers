package com.trailblazers.freewheelers.model;


public class Address {

    private Long address_id;
    private String street_one;
    private String street_two;
    private String city;
    private String state;
    private String post_code;


    public Address() {
        this.address_id = 0L;
    }

    public Address setStreet_one(String street_one) {
        this.street_one = street_one;
        return this;
    }

    public String getStreet_one() {
        return street_one;
    }

    public Address setStreet_two(String street_two) {
        this.street_two = street_two;
        return this;
    }

    public String getStreet_two() {
        return street_two;
    }

    public Long getAddress_id() {
        return address_id;
    }

    public Address setAddress_id(Long address_id) {
        this.address_id = address_id;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public Address setState(String state) {
        this.state = state;
        return this;
    }

    public Address setPost_code(String postCode) {
        this.post_code = postCode;
        return this;
    }

    public String getPost_code() {
        return post_code;
    }
}
