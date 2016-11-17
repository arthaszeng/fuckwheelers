package com.trailblazers.freewheelers.model;

import java.util.HashMap;

public class CreditCardPaymentValidator {


    private static final String DATEREGEX = "^(1[0-2]|0[1-9])-(\\d{4})$";

    private static final String CREDITCARDNUMREGEX = "^\\d{16}$";

    private static final String CREDITCARDNUMREGEXFORAMEXCARD = "^\\d{15}$";

    private static final String CVVNUMREGEX = "^\\d{3}$";

    private static final String CIDNUMREGEX = "^\\d{4}$";

    public static HashMap verifyInputs(CreditCardPayment payment) {
        HashMap errors = new HashMap();

        if(payment.getType() == null) {
            errors.put("type", "Must enter a valid type");
            return errors;
        }
        checkCreditCardNumber(payment,errors);
        checkCSC(payment, errors);
        checkExpirationDate(payment, errors);

        return errors;
    }

    private static void checkExpirationDate(CreditCardPayment payment, HashMap errors) {
        if(!payment.getExpiryDate().matches(DATEREGEX)) {
            errors.put("expiry", "Must enter a valid expiry date");
        }
    }


    private static void checkCSC(CreditCardPayment payment, HashMap errors) {
        String csc = payment.getCsc();
        switch(payment.getType()) {
            case AMEX:
                if(!csc.matches(CIDNUMREGEX)) {
                    errors.put("csc", "Must enter a valid cid");
                }
                break;
            default:
                if(!csc.matches(CVVNUMREGEX)) {
                    errors.put("csc", "Must enter a valid cvv");
                }
        }
    }

    private static void checkCreditCardNumber(CreditCardPayment payment, HashMap errors) {
        String creditCardNumber = payment.getCardNumber();
        switch(payment.getType()) {
            case AMEX:
                if(!creditCardNumber.matches(CREDITCARDNUMREGEXFORAMEXCARD)) {
                    errors.put("creditCard", "Must enter a valid credit card number");
                }
                break;
            default:
                if(!creditCardNumber.matches(CREDITCARDNUMREGEX)) {
                    errors.put("creditCard", "Must enter a valid credit card number");

                }
        }
    }

}
