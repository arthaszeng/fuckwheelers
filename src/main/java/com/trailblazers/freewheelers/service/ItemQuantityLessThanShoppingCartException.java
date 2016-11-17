package com.trailblazers.freewheelers.service;

public class ItemQuantityLessThanShoppingCartException extends RuntimeException {
    public ItemQuantityLessThanShoppingCartException() {
        super("Inventory shortage!");
    }
}
