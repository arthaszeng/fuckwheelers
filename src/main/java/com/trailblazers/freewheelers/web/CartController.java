package com.trailblazers.freewheelers.web;


import com.trailblazers.freewheelers.configuration.ToggledFeature;
import com.trailblazers.freewheelers.model.Account;
import com.trailblazers.freewheelers.model.CartCalculator;
import com.trailblazers.freewheelers.model.Item;
import com.trailblazers.freewheelers.service.AccountService;
import com.trailblazers.freewheelers.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.List;

@Controller
@RequestMapping(CartController.URL)
public class CartController {
    static final String URL = "/cart";

    private AccountService accountService;
    private CountryService countryService;
    private CartCalculator cartCalculator;

    @Autowired
    public CartController(AccountService accountService, CountryService countryService) {
        this.accountService = accountService;
        this.countryService = countryService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public void get(Model model, HttpServletRequest request, Principal principal) {
        if (!ToggledFeature.SHOPPING_CART.isActive()) {
            throw new NoSuchMethodError();
        }

        if (principal != null) {
            tryAddVat(model, principal, request.getSession());
        } else {
            showMissingCountryMessage(model);
        }
        BigDecimal subTotal = new BigDecimal(0);
        List<Item> itemToShow = (List<Item>) request.getSession().getAttribute("items");
        if(itemToShow!=null) {
            model.addAttribute("items", itemToShow);
            cartCalculator = new CartCalculator(itemToShow);
            subTotal = cartCalculator.calculateSubtotal();
        }
        request.getSession().setAttribute("subTotal", subTotal.toString());
        calculateTotalPrice(model, principal, subTotal, request.getSession());

    }

    private void showMissingCountryMessage(Model model) {
        model.addAttribute("missingCountryMessage", "additional charges may apply according to your region");
    }

    private void tryAddVat(Model model, Principal principal, HttpSession session) {
        Account account = accountService.getAccountByName(principal.getName());
        if (account.getCountry_id() == null) {
            showMissingCountryMessage(model);
        } else {
            double vat = countryService.getVatByCountryId(account.getCountry_id());
            if (vat > 0.0) session.setAttribute("vatPercentage", formatVat(vat));
        }
    }

    private double findVat(Principal principal) {
        Account account = accountService.getAccountByName(principal.getName());
        if (account.getCountry_id() != null) {
            return countryService.getVatByCountryId(account.getCountry_id());
        } else {
            return 0.0;
        }
    }

    private void calculateTotalPrice(Model model, Principal principal, BigDecimal subTotal, HttpSession session) {
        if (principal != null) {
            double vat = findVat(principal);
            if (vat != 0.0) session.setAttribute("vat", formatNumber(subTotal.doubleValue() * findVat(principal)));

            model.addAttribute("totalPrice", formatNumber(subTotal.doubleValue() * (1 + findVat(principal))));
            session.setAttribute("totalPrice",formatNumber(subTotal.doubleValue() * (1 + findVat(principal))));
        } else {
            model.addAttribute("totalPrice", formatNumber(subTotal.doubleValue()));
            session.setAttribute("totalPrice",formatNumber(subTotal.doubleValue()));

        }
    }

    private String formatNumber(double number) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(number);
    }

    private String formatVat(double vat) {
        DecimalFormat df = new DecimalFormat("###.#");
        return df.format(vat * 100);
    }

    @RequestMapping(method = RequestMethod.POST)
    public RedirectView removeItem(HttpSession session, HttpServletRequest request) {
        if (ToggledFeature.SHOPPING_CART.isActive()) {
            List<Item> items = (List<Item>) session.getAttribute("items");
            String itemId = request.getParameter("itemId");
            Item item = findItemById(items, itemId);
            items.remove(item);
            if (items.isEmpty()) {
                session.removeAttribute("items");
                return new RedirectView("/");
            }
            session.setAttribute("items", items);
        }

        return new RedirectView("/");

    }

    private Item findItemById(List<Item> items, String itemId) {
        Long id = Long.parseLong(itemId);
        for (Item item : items) {
            if (item.getItemId() == id)
                return item;
        }
        return null;
    }
}
