package com.trailblazers.freewheelers.model;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.valueOf;

public class CartCalculator {

    private List<Item> items;

    public CartCalculator(List<Item> items) {
        this.items = items;
    }

    public BigDecimal calculateSubtotal() {
        BigDecimal subTotal = BigDecimal.ZERO;
        for(Item item : items) {
            subTotal = subTotal.add(item.getPrice());
        }
        return subTotal;
    }


    public BigDecimal calculateOverallTax(double tax) {
        return calculateSubtotal().multiply(valueOf(tax));
    }

    public BigDecimal calculateTotal(double tax) {
        return calculateSubtotal().add(calculateOverallTax(tax));
    }
}
