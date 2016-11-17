package com.trailblazers.freewheelers.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by rameshb on 1/16/14.
 */
public class NetPromoterScoreReportPage extends BasePage {
    private final By promoterComments;
    private final By detractorComments;
    private final By passiveComments;
    private By totalResponses;
    private By npsScore;
    private By percentagePromoters;
    private By percentagePassives;
    private By percentageDetractors;

    public NetPromoterScoreReportPage(WebDriver driver) {
        super(driver);
        totalResponses = By.id("totalResponse");
        npsScore = By.id("npsScore");
        percentagePromoters = By.id("promoterPercentage");
        percentagePassives = By.id("passivePercentage");
        percentageDetractors = By.id("detractorPercentage");
        promoterComments = By.id("promoterComments");
        detractorComments = By.id("detractorComments");
        passiveComments = By.id("passiveComments");
    }

    public boolean totalResponsesCorrect(String expectedValue) {
        return findElement(totalResponses).getText().equals(expectedValue);
    }

    public boolean npsScoreCorrect(String expectedValue) {
        return findElement(npsScore).getText().equals(expectedValue);
    }

    public boolean percentagePromotersCorrect(String expectedValue) {
        return findElement(percentagePromoters).getText().equals(String.format("%s%% Promoters", expectedValue));
    }

    public boolean percentagePassivesCorrect(String expectedValue) {
        return findElement(percentagePassives).getText().equals(String.format("%s%% Passives", expectedValue));
    }

    public boolean percentageDetractorsCorrect(String expectedValue) {
        return findElement(percentageDetractors).getText().equals(String.format("%s%% Detractors", expectedValue));
    }

    public boolean containsPromoterFeedbackComment(String comment) {
        return findElement(promoterComments).getText().contains(comment);
    }

    public boolean containsDetractorFeedbackComment(String comment) {
        return findElement(detractorComments).getText().contains(comment);
    }

    public boolean containsPassiveFeedbackComment(String comment) {
        return findElement(passiveComments).getText().contains(comment);
    }
}
