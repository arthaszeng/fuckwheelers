package com.trailblazers.freewheelers.web;

import com.trailblazers.freewheelers.configuration.ToggledFeature;
import com.trailblazers.freewheelers.model.Item;
import com.trailblazers.freewheelers.service.ItemService;
import com.trailblazers.freewheelers.web.helper.HintMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private final ItemService itemService;

    @Autowired
    HomeController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String get(Model model, @ModelAttribute("item") Item item) {
        model.addAttribute("items", itemService.getItemsWithNonZeroQuantity());
        return "home";
    }

    @RequestMapping(method = RequestMethod.POST)
    public RedirectView addToCart(Model model, @ModelAttribute Item item, final RedirectAttributes redirectAttributes, HttpSession session) {
        if (ToggledFeature.SHOPPING_CART.isActive()) {
            Item itemToAddToCart = itemService.get(item.getItemId());
            List<Item> items = (List<Item>) session.getAttribute("items");
            if (items != null) {
                items.add(itemToAddToCart);
                session.setAttribute("items", items);
            } else {
                items = new ArrayList<>();
                items.add(itemToAddToCart);
                session.setAttribute("items", items);
            }

            model.addAttribute("items", items);
            String itemName = itemToAddToCart.getName();
            redirectAttributes.addFlashAttribute("message", String.format(HintMessage.ADD_TO_CART_MESSAGE, itemName));
        }
        return new RedirectView("/");
    }
}

