package com.trailblazers.freewheelers.configuration;

import org.togglz.core.Feature;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

public enum ToggledFeature implements Feature {
    @Label("SHOPPING_CART")
    SHOPPING_CART,

    @Label("RESERVE_ITEM")
    RESERVE_ITEM,

    @Label("CREDIT_CARD_PAYMENT")
    CREDIT_CARD_PAYMENT,

    @Label("EDIT_DETAIL")
    EDIT_DETAIL;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }
}
