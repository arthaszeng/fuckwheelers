package com.trailblazers.freewheelers.helpers;

import org.openqa.selenium.By;

public class HomeTable {
    public static By getNameFieldFor(String itemName) {
        return By.xpath("//tbody/tr/td[1][text()='" + itemName + "']");
    }

    public static By reserveButtonFor(String name) {
        return By.xpath("//tbody/tr/td[1][text() = '" + name + "']/parent::*/td[6]/form/button");
    }


}
