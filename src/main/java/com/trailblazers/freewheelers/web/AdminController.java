package com.trailblazers.freewheelers.web;

import com.trailblazers.freewheelers.model.Account;
import com.trailblazers.freewheelers.model.Item;
import com.trailblazers.freewheelers.model.OrderStatus;
import com.trailblazers.freewheelers.model.ReserveOrder;
import com.trailblazers.freewheelers.service.AccountService;
import com.trailblazers.freewheelers.service.ItemService;
import com.trailblazers.freewheelers.service.ReserveOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.valueOf;

@Controller
public class AdminController {

    private static final String URL = "/admin";

    private ReserveOrderService reserveOrderService = new ReserveOrderService();
    private ItemService itemService = new ItemService();
    private AccountService accountService = new AccountService();

    @RequestMapping(value = URL, method = RequestMethod.GET)
    public void get(Model model) {
        model.addAttribute("reserveOrders", getAllOrders());
    }

    @RequestMapping(value = URL, method = RequestMethod.POST, params="save=Save Changes")
    public void updateOrder(Model model, String state, String orderId, String note) {
        Long order_id = valueOf(orderId);
        OrderStatus status = OrderStatus.valueOf(state);
        reserveOrderService.updateOrderDetails(order_id, status, note);
        get(model);
    }

    private List<ReservedOrderDetail> getAllOrders() {
        List<ReserveOrder> reserveOrders = reserveOrderService.getAllOrdersByAccount();

        List<ReservedOrderDetail> reservedOrderDetails = new ArrayList<>();

        for (ReserveOrder reserveOrder: reserveOrders){
            Account account = accountService.get(reserveOrder.getAccount_id());
            Item item = itemService.get(reserveOrder.getItem_id());

            reservedOrderDetails.add(new ReservedOrderDetail(reserveOrder.getOrder_id(),
                                                             account,
                                                             item,
                                                             reserveOrder.getReservation_timestamp(),
                                                             reserveOrder.getStatus(),
                                                             reserveOrder.getNote()));

        }
        return reservedOrderDetails;
    }

}
