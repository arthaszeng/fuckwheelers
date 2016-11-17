package com.trailblazers.freewheelers.web;

import com.trailblazers.freewheelers.configuration.ToggledFeature;
import com.trailblazers.freewheelers.model.CreditCardPayment;
import com.trailblazers.freewheelers.model.CreditCardType;
import com.trailblazers.freewheelers.model.Item;
import com.trailblazers.freewheelers.model.PaymentResult;
import com.trailblazers.freewheelers.service.ItemQuantityLessThanShoppingCartException;
import com.trailblazers.freewheelers.service.PaymentService;
import com.trailblazers.freewheelers.service.ReserveItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/payment")
public class PaymentController {


    private PaymentService paymentService;
    private ReserveItemService reserveItemService;

    @Autowired
    public PaymentController(PaymentService paymentService, ReserveItemService reserveItemService) {
        this.paymentService = paymentService;
        this.reserveItemService = reserveItemService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView details(HttpServletRequest request) {
        if (!ToggledFeature.CREDIT_CARD_PAYMENT.isActive()) {
            throw new NoSuchMethodError();
        }

        String total = (String) request.getSession().getAttribute("totalPrice");
        ModelMap model = new ModelMap();
        model.addAttribute("totalPrice", total);
        return new ModelAndView("payment/details", "displayTotal", model);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/checkout")
    public ModelAndView checkout(HttpServletRequest request, Principal principal) {
        if (!ToggledFeature.CREDIT_CARD_PAYMENT.isActive()) {
            throw new NoSuchMethodError();
        }

        CreditCardPayment payment = createPayment(request);
        Map<String, String> errors = paymentService.checkOut(payment);


        if (!errors.isEmpty()) {
            return showErrors(errors);
        } else {
            errors = reserveItems(request, principal);
            if (!errors.isEmpty()) {
                return showErrors(errors);
            }
            return showSuccess();
        }
    }

    private ModelAndView showSuccess() {
        ModelMap model = new ModelMap();
        ModelAndView successView;
        model.put("returnState", PaymentResult.SUCCESS);
        successView = new ModelAndView("/payment/success", "receivedResult", model);
        return successView;
    }

    private Map<String, String> reserveItems(HttpServletRequest request, Principal principal) {
        Map<String, String> errors = new HashMap<>();
        try {
            List<Item> itemToShow = (List<Item>) request.getSession().getAttribute("items");
            reserveItemService.reserveItems(itemToShow, principal.getName());
            request.getSession().removeAttribute("items");
        } catch (ItemQuantityLessThanShoppingCartException ex) {
            errors = new HashMap<>();
            errors.put("itemError", ex.getMessage());
        }
        return errors;
    }

    private CreditCardPayment createPayment(HttpServletRequest request) {
        String expiryDate = String.format("%s-%s",
                request.getParameter("expiryMonth"),
                request.getParameter("expiryYear"));

        CreditCardPayment payment = new CreditCardPayment()
                .setCardNumber(request.getParameter("cardNumber"))
                .setCsc(request.getParameter("csc"))
                .setExpiryDate(expiryDate)
                .setAmount(Double.parseDouble((String) request.getSession().getAttribute("totalPrice")));

        CreditCardType type = extractCreditCardType(request);
        payment.setType(type);
        return payment;

    }

    private CreditCardType extractCreditCardType(HttpServletRequest request) {
        CreditCardType type;
        try {
            type = CreditCardType.valueOf(request.getParameter("type"));
        } catch (IllegalArgumentException ex) {
            type = null;
        }
        return type;
    }

    private ModelAndView showErrors(Map errors) {
        ModelMap model = new ModelMap();
        model.put("errors", errors);
        return new ModelAndView("payment/details", "validationMessage", model);
    }

}
