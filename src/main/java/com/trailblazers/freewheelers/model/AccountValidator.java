package com.trailblazers.freewheelers.model;

import java.util.*;

public class AccountValidator {
    public static final String REGEXRULEOFPASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[.#%ˆ\\^$@$!%*?&+\\\\/':,)(}{\\]\\[~\\-_`])[A-Za-z\\d.#%ˆ\\^$@$!%*?&+\\\\/':,)(}{\\]\\[~\\-_`]{8,20}$";

    private List<Long> validCountryIds;

    public AccountValidator(List<Long> validCountryIds) {
        this.validCountryIds = validCountryIds;
    }

    public HashMap verifyInputs(Account account) {
        HashMap errors = new HashMap();

        if (!account.getEmail_address().contains("@")) {
           errors.put("email", "Please enter a valid email");
        }

        if(!account.getPassword().matches(REGEXRULEOFPASSWORD)){
            errors.put("password", "Please enter a valid password");
        }

        if(account.getAccount_name().isEmpty()) {
            errors.put("name", "Please enter a name");
        }

        if(account.getPhone_number().isEmpty()) {
            errors.put("phone_number", "Please enter a phone number");
        }

        if(account.getCountry_id() == null) {
            errors.put("country", "Please choose a country");
        } else if(!validCountryIds.contains(account.getCountry_id())) {
            errors.put("country", "Please choose a valid country");
        }

        if(account.getAddress_id() == null){
            errors.put("address","Please fill the address fields");
        }

        return errors;
    }


}