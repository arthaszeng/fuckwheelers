package com.trailblazers.freewheelers.helpers;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

public class SyntaxSugar {

    public static final String SOME_PHONE_NUMBER = "555-123456";
    public static final String SOME_OTHER_PHONE_NUMBER = "555-123456";
    public static final long SOME_ADDRESS_ID = 12345L;
    public static final long SOME_COUNTRY_ID = 1L;
    public static final long UK_COUNTRY_ID = 2L;
    public static final long GERMANY_COUNTRY_ID = 3L;
    public static final String SOME_PASSWORD = "2WSX@WSX2wsx";
    public static final String SOME_EMAIL = "somebody@something.de";
    public static final String SOME_COUNTRY = "USA";
    public static final BigDecimal SOME_PRICE = valueOf(49.99);
    public static final BigDecimal SOME_PRICE_WITH_ADDED_20_PERCENT = valueOf(59.99);
    public static final BigDecimal SOME_PRICE_WITH_ADDED_19_PERCENT = valueOf(59.49);
    public static final String SOME_STREET_ONE = "Main St 214";
    public static final String SOME_STREET_TWO = "Apartment 4";
    public static final String SOME_CITY = "Delhi";
    public static final String EMPTY_CITY = "";
    public static final String INVALID_STREET = "~!&&%&*A";
    public static final String SOME_STATE = "Maharashtra";
    public static final String INVALID_STATE = "123TN~~~";
    public static final String SOME_POST_CODE = "CA-3267";
    public static final String INVALID_POST_CODE = "~!&&%&*A";

    public static final String INVALID_PASSWORD = "aA1öī!@b";
    public static final String ERROR_EMAIL = "error@error.com";
    public static final String EMPTY_USERNAME = "";
    public static final String EMPTY_PASSWORD = "";
    public static final String NO_COUNTRY = "Please choose";
    public static final String EMPTY_PHONE_NUMBER = "";
    public static final String NO_QUANTITY = "";
    public static final long ONLY_ONE_LEFT = 1L;
    public static final String REALLY_EXPENSIVE = "2899.00";
    public static final String SOME_DESCRIPTION = "4 x red, curved Arrow shape, scew fastening";
    public static final String A_LOT = "1000";
    public static final String ITEM_TYPE_FRAME = "FRAME";

    public static final String EMPTY_STREET = "";
    public static final boolean CHECK_AGREEMENT = true;
    public static final boolean NOT_CHECK_AGREEMENT = false;

    public static final String VALIDTESTCREDITCARDCSC = "534";
    public static final String INVALIDTESTCREDITCARDCSC = "53";
    public static final String VALIDTESTCREDITCARDNUMBER = "4111111111111111";
    public static final String INVALIDTESTCREDITCARDNUMBER = "4111";
    public static final String VALIDTESTCREDITCARDEXPIRYYEAR = "2020";
    public static final String INVALIDTESTCREDITCARDMONTH = "13";
    public static final String VALIDTESTCREDITCARDMONTH = "11";
    public static final String VISA = "Visa";
    public static final String ADMIN = "AdminCat";
    public static final String ADMINPASSWORD = "Yellow bikes are just amazingly awesome, right? Says Robert, my good friend.";
    public static final String USER = "UserCat";
    public static final long USER_ACCOUNT_ID = 2;
    public static final long ITEM_ID = 1;
    public static final String USERPASSWORD = "Part 3: Tall zebra mobile responsive communication patterns.";

    public static String emailFor(String userName) {
        return userName.replace(' ', '-') + "@random-email.com";
    }

    public static String from(String s) {
        return s;
    }

    public static String to(String s) {
        return s;
    }

    public static BigDecimal add20Percent(BigDecimal decimal) {
        return decimal.multiply(valueOf(1.2));
    }


}