package com.trailblazers.freewheelers.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "authorisation-request")
public class CreditCardPayment {

    private String cardNumber;

    private String expiryDate;

    private String csc;

    private double amount;

    private CreditCardType creditCardType;

    @XmlTransient
    public CreditCardType getType() {
        return creditCardType;
    }

    public CreditCardPayment setType(CreditCardType type) {
        this.creditCardType = type;
        return this;
    }

    @XmlElement(name = "cc_number")
    public String getCardNumber() {
        return cardNumber;
    }

    public CreditCardPayment setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    @XmlElement(name = "expiry")
    public String getExpiryDate() {
        return expiryDate;
    }

    public CreditCardPayment setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    @XmlElement(name = "csc")
    public String getCsc() {
        return csc;
    }

    public CreditCardPayment setCsc(String csc) {
        this.csc = csc;
        return this;
    }

    @XmlElement(name = "amount")
    public double getAmount() {
        return amount;
    }

    public CreditCardPayment setAmount(double amount) {
        if(amount < 0) {
            throw new IllegalArgumentException("The amount must be positive");
        }
        this.amount = amount;
        return this;
    }

}
