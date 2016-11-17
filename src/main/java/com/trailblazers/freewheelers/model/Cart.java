package com.trailblazers.freewheelers.model;

public class Cart {

    private Long cart_id;
    private Long item_id;

    public Long getCart_id() {
        return cart_id;
    }

    public Cart setCart_id(Long cart_id) {
        this.cart_id = cart_id;
        return this;
    }

    public Long getItem_id() {
        return item_id;
    }

    public Cart setItem_id(Long item_id) {
        this.item_id = item_id;
        return this;
    }

}
