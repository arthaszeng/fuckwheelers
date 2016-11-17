package com.trailblazers.freewheelers.model;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AddressValidator {

    public HashMap verifyInputs(Address address, String countryName) {
        HashMap errors = new HashMap();

        String streetOnePattern = "[a-z\\u00E0-\\u00FCA-Z\\u00E0-\\u00FC0-9\\d\\\\/#,:.&'\\-_\\s]{1,255}";
        String streetTwoPattern = "[a-z\\u00E0-\\u00FCA-Z\\u00E0-\\u00FC0-9\\d\\\\/#,:.&'\\-_\\s]{0,255}";
        String cityPattern = "[a-z\\u00E0-\\u00FCA-Z\\u00E0-\\u00FC0-9\\d\\\\/#,:.&'\\-_\\s]{1,100}";
        String statePattern = "[a-z\\u00E0-\\u00FCA-Z\\u00E0-\\u00FC0-9\\d\\\\/#,:.&'\\-_\\s]{0,100}";
        String postCodePattern = "[a-z\\u00E0-\\u00FCA-Z\\u00E0-\\u00FC0-9\\d\\-_\\s]{4,10}";

        List<String> mandatoryStateCountries = Arrays.asList("USA","Italy","Canada");

        if(mandatoryStateCountries.contains(countryName) && address.getState().isEmpty()){
            errors.put("state", "Must enter a valid state!");
        }

        if(!address.getStreet_one().matches(streetOnePattern)) {
            errors.put("street 1", "Must enter valid street 1!");
        }

        if(!address.getStreet_two().matches(streetTwoPattern)){
            errors.put("street 2","Must enter valid street 2!");
        }

        if(!address.getCity().matches(cityPattern)){
            errors.put("city","Must enter a valid city!");
        }

        if (!address.getState().matches(statePattern)){
            errors.put("state","Must enter a valid state!");
        }

        if (!address.getPost_code().matches(postCodePattern)){
            errors.put("postCode","Must enter a valid post code!");
        }

        return errors;
    }
}
