package com.trailblazers.freewheelers.web;

import com.trailblazers.freewheelers.model.Item;
import com.trailblazers.freewheelers.service.ItemService;
import com.trailblazers.freewheelers.service.ReserveItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

import static java.util.Arrays.asList;

@Controller
@RequestMapping(ReserveController.URL)
public class ReserveController {

    static final String URL = "/reserve";
    private ReserveItemService reserveItemService;
    private ItemService itemService;


    @Autowired
    public ReserveController(ReserveItemService reserveItemService, ItemService itemService) {
        this.reserveItemService = reserveItemService;
        this.itemService = itemService;
    }

    @RequestMapping(method = RequestMethod.POST, params = "reserve=Reserve Item")
    public void reserveItem(Model model, Principal principal, @ModelAttribute Item item, HttpServletRequest httpServletRequest) {
        Item itemToReserve = itemService.get(item.getItemId());
        String userName = principal.getName();
        reserveItemService.reserveItems(asList(itemToReserve), userName);
        httpServletRequest.getSession().removeAttribute("item");


        model.addAttribute("item", itemToReserve);
    }

}
