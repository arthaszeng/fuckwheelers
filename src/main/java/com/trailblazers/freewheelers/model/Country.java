package com.trailblazers.freewheelers.model;

public class Country {

    public final static Country NO_COUNTRY = new Country();

    private Long country_id;

    private String name;

    private double vat;

    public Country() {
        this.country_id = 0L;
    }

    public Long getCountry_id() {
        return country_id;
    }

    public Country setCountry_id(Long country_id) {
        this.country_id = country_id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Country setName(String name) {
        this.name = name;
        return this;
    }

    public Country setVat(double vat) {
        this.vat = vat;
        return this;
    }

    public double getVat(){
        return this.vat;
    }

}
