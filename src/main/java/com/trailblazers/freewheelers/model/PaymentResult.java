package com.trailblazers.freewheelers.model;

public enum PaymentResult {

    SUCCESS(""),
    UNAUTH("The charge on this card was declined. This could be due to invalid card information, or on reaching the card limit."),
    NET_ERR("An unexpected error occurred on the server"),
    RVK_CARD("This card has been revoked by the card provider.");

    private String message;

    PaymentResult(String message) {
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
