package com.trailblazers.freewheelers.service;

import com.trailblazers.freewheelers.model.Account;
import com.trailblazers.freewheelers.model.Item;
import com.trailblazers.freewheelers.model.ReserveOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class ReserveItemService {


    public static final int ONE = 1;
    private AccountService accountService;
    private ReserveOrderService reserveOrderService;
    private ItemService itemService;

    @Autowired
    public ReserveItemService(AccountService accountService, ReserveOrderService reserveOrderService, ItemService itemService) {
        this.accountService = accountService;
        this.reserveOrderService = reserveOrderService;
        this.itemService = itemService;
    }


    public void reserveItems(List<Item> itemsToReserve, String userName) {
        Account account = accountService.getAccountByName(userName);
        Map<Long, Integer> itemWithNumbers = getItemWithNumbers(itemsToReserve);

        if (!haveItemQuantityLessThanShoppingCart(itemWithNumbers)) {
            reserveItems(account, itemWithNumbers);
        } else {
            throw new ItemQuantityLessThanShoppingCartException();
        }
    }

    private void reserveItems(Account account, Map<Long, Integer> itemWithNumbers) {
        for (Entry<Long, Integer> itemWithNumber : itemWithNumbers.entrySet()) {
            for (int i = 0; i < itemWithNumber.getValue(); i++) {
                Date rightNow = new Date();
                ReserveOrder reserveOrder = new ReserveOrder(account.getAccount_id(), itemWithNumber.getKey(), rightNow);
                reserveOrderService.save(reserveOrder);
            }

            itemService.decreaseQuantityByNumbers(itemWithNumber.getKey(), itemWithNumber.getValue());
        }
    }

    private boolean haveItemQuantityLessThanShoppingCart(Map<Long, Integer> itemWithNumbers) {
        boolean haveLessThan = false;
        for (Entry<Long, Integer> itemWithNumber : itemWithNumbers.entrySet()) {
            Item item = itemService.get(itemWithNumber.getKey());
            if (item.getQuantity() < itemWithNumber.getValue()) {
                haveLessThan = true;
                break;
            }
        }
        return haveLessThan;
    }

    private Map<Long, Integer> getItemWithNumbers(List<Item> itemsToReserve) {
        HashMap<Long, Integer> itemNumbers = new HashMap<>();
        for (Item item : itemsToReserve) {
            if (itemNumbers.containsKey(item.getItemId())) {
                itemNumbers.put(item.getItemId(), itemNumbers.get(item.getItemId()) + 1);
            } else {
                itemNumbers.put(item.getItemId(), 1);
            }
        }
        return itemNumbers;
    }
}
