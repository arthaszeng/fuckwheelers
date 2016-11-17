package com.trailblazers.freewheelers.helpers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Controls {

    public static void fillField(WebElement field, String value) {
        field.clear();
        field.sendKeys(value);
    }


    public static void check(WebElement checkBox) {
        if(!checkBox.isSelected()) {
            checkBox.click();
        }
    }

    public static void select(WebElement select, String state) {
        new Select(select).selectByVisibleText(state);
    }

}
