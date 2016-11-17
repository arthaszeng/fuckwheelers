package com.trailblazers.freewheelers.web;


import com.trailblazers.freewheelers.configuration.ToggledFeature;
import com.trailblazers.freewheelers.model.*;
import com.trailblazers.freewheelers.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/userProfile")
public class UserProfileController {
    private AccountService accountService;
    private ReserveOrderService reserveOrderService;
    private ItemService itemService;
    private CountryService countryService;
    private AddressService addressService;

    public UserProfileController() {
    }

    @Autowired
    public UserProfileController(AccountService accountService, ReserveOrderService reserveOrderService,
                                 ItemService itemService, CountryService countryService, AddressService addressService) {
        this.accountService = accountService;
        this.reserveOrderService = reserveOrderService;
        this.itemService = itemService;
        this.countryService = countryService;
        this.addressService = addressService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{userName:.*}", method = RequestMethod.GET)
    public String get(@PathVariable String userName, Model model, Principal principal) {
        userName = extractUserName(userName, principal);

        Account account = accountService.getAccountByName(userName);

        List<Item> items = getItemsOrderByUser(account);
        Address address = addressService.getById(account.getAddress_id());
        Country country = getCountryFromId(account);

        model.addAttribute("items", items);
        model.addAttribute("country", country);
        model.addAttribute("countryOptions", countryService.findAll());
        model.addAttribute("account", account);
        model.addAttribute("address", address);

        return "userProfile";
    }

    private Country getCountryFromId(Account account) {
        if (account.getCountry_id() == null) {
            return Country.NO_COUNTRY;
        }
        return countryService.getById(account.getCountry_id());
    }

    public String extractUserName(@PathVariable String userName, Principal principal) {
        if (userName == null) {
            userName = principal.getName();
        }
        userName = decode(userName);
        return userName;
    }

    private String decode(String userName) {
        try {
            return URLDecoder.decode(userName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return userName;
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String get(Model model, Principal principal) {
        return get(null, model, principal);
    }

    private List<Item> getItemsOrderByUser(Account account) {
        List<ReserveOrder> reserveOrders = reserveOrderService.findAllOrdersByAccountId(account.getAccount_id());
        List<Item> items = new ArrayList<Item>();
        for (ReserveOrder reserveOrder : reserveOrders) {
            items.add(itemService.get(reserveOrder.getItem_id()));
        }
        return items;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView update(HttpServletRequest request, Model model, Principal principal) {
        if (ToggledFeature.EDIT_DETAIL.isActive()) {
            Account oldAccount = accountService.getAccountByName(principal.getName());
            oldAccount.setPhone_number(request.getParameter("phone_number"));
            oldAccount.setCountry_id(Long.parseLong(request.getParameter("country_id")));

            Address oldAddress = addressService.getById(oldAccount.getAddress_id());
            oldAddress.setStreet_one(request.getParameter("street_one"));
            oldAddress.setStreet_two(request.getParameter("street_two"));
            oldAddress.setCity(request.getParameter("city"));
            oldAddress.setState(request.getParameter("state"));
            oldAddress.setPost_code(request.getParameter("post_code"));

            Country country = countryService.getById(oldAccount.getCountry_id());

            try {
                accountService.updateAccount(oldAccount);

            } catch (CannotUpdateAccountException e) {
                e.printStackTrace();
            }

            model.addAttribute("account", oldAccount);
            model.addAttribute("address", oldAddress);
            model.addAttribute("country", country);
            model.addAttribute("countryOptions", countryService.findAll());

            ModelAndView modelAndView = new ModelAndView("userProfile", "user-details", model);
            return modelAndView;
        } else {
            return null;
        }
    }

}
