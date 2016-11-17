package com.trailblazers.freewheelers.web;

import com.trailblazers.freewheelers.model.*;
import com.trailblazers.freewheelers.service.AccountService;
import com.trailblazers.freewheelers.service.AddressService;
import com.trailblazers.freewheelers.service.CountryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/account")
public class AccountController {

    public static final String TRUE = "true";
    private final AccountValidator accountValidator;
    private final AddressValidator addressValidator;

    AccountService accountService;
    AddressService addressService;

    CountryService countryService;
    private Model model;

    public AccountController() {
        addressValidator = new AddressValidator();
        accountService = new AccountService();
        countryService = new CountryService();
        accountValidator = new AccountValidator(collectValidCountryIds(countryService));
        addressService = new AddressService();
    }


    private List<Long> collectValidCountryIds(CountryService countryService) {
        List<Long> result = new ArrayList<>();
        for (Country country : countryService.findAll()) {
            result.add(country.getCountry_id());
        }
        return result;
    }

    @RequestMapping(value = {"/create"}, method = RequestMethod.GET)
    public ModelAndView createAccountForm(Model model) {
        List<Country> allTheCountries = countryService.findAll();
        model.addAttribute("countries", allTheCountries);
        return new ModelAndView("account/create", "validationMessage", model);
    }

    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    public ModelAndView processCreate(HttpServletRequest request) throws IOException {
        HashMap errors = new HashMap();

        if (showErrorIfUsernameAlreadyExists(request, errors)) return showErrors(errors);

        Address address = getAddress(request);

        errors = validAddressForm(request, address);

        if (!errors.isEmpty()) {
            return showErrors(errors);
        }

        Long address_id = getAddressId(address);

        Account account = getAccount(request, address_id);

        errors = validAccountForm(request, account);


        if (!errors.isEmpty()) {
            return showErrors(errors);
        }

        try {
            return showSuccess(accountService.createAccount(account));
        } catch (Exception e) {
            return showError();
        }
    }

    public Long getAddressId(Address address) {
        addressService.createAddress(address);
        return address.getAddress_id();
    }

    @RequestMapping(value = {"/userDuplicate"}, method = RequestMethod.GET)
    public void userExists(@RequestParam String name , HttpServletResponse response){
        if(accountService.getNumberOfExistingAccountsByName(name)!= 0){
            response.setStatus(200);
        }else {
            response.setStatus(404);
        }
    }

    private Account getAccount(HttpServletRequest request, Long address_id) {
        return new Account()
                    .setEmail_address(request.getParameter("email"))
                    .setPassword(request.getParameter("password"))
                    .setAccount_name(convertIntoString(request.getParameter("name")))
                    .setPhone_number(request.getParameter("phone_number"))
                    .setEnabled(true)
                    .setAddress_id(address_id);
    }

    private Address getAddress(HttpServletRequest request) {
        return new Address()
                    .setStreet_one(request.getParameter("street_one"))
                    .setStreet_two(request.getParameter("street_two"))
                    .setCity(request.getParameter("city"))
                    .setState(request.getParameter("state"))
                    .setPost_code(request.getParameter("post_code"));
    }

    private boolean showErrorIfUsernameAlreadyExists(HttpServletRequest request, HashMap errors) {
        if(accountService.getNumberOfExistingAccountsByName(request.getParameter("name"))!=0)
        {
            errors.put("duplicateUserName","Account name already exists!");
            return true;
        }
        return false;
    }

    private HashMap validAddressForm(HttpServletRequest request, Address address) {
        Long countryId = Long.parseLong(request.getParameter("country_id"));

        String countryName = countryService.getById(countryId).getName();


        HashMap errors = addressValidator.verifyInputs(address, countryName);

        return errors;
    }

    private HashMap validAccountForm(HttpServletRequest request, Account account) {
        try {
            account.setCountry_id(Long.parseLong(request.getParameter("country_id")));
        } catch (NumberFormatException ex) {
            account.setCountry_id(null);
        }

        HashMap errors = accountValidator.verifyInputs(account);

        String isAgree = request.getParameter("isAgree");
        if (isAgree == null || !isAgree.equals(TRUE)) {
            errors.put("agreement", "Must agree to the Terms & Conditions");
        }

        return errors;
    }



    private ModelAndView showErrors(Map errors) {
        ModelMap model = new ModelMap();
        model.put("errors", errors);
        List<Country> allTheCountries = countryService.findAll();
        model.addAttribute("countries", allTheCountries);
        return new ModelAndView("account/create", "validationMessage", model);
    }

    private ModelAndView showError() {
        return new ModelAndView("account/createFailure");
    }

    private ModelAndView showSuccess(Account account) {
        ModelMap model = new ModelMap();
        model.put("name", account.getAccount_name());
        return new ModelAndView("account/createSuccess", "postedValues", model);
    }

    private String convertIntoString(String inputString) {
        return inputString.replace("<", "&lt").replace(">", "&gt");
    }


}
