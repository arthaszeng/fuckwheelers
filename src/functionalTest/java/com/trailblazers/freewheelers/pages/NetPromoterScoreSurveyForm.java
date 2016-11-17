package com.trailblazers.freewheelers.pages;

import com.trailblazers.freewheelers.helpers.FeedbackType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NetPromoterScoreSurveyForm extends BasePage {
    private final By closeButton;
    private final By positiveRatingRadio;
    private final By neutralRatingRadio;
    private final By negativeRatingRadio;
    private final By commentsTextBox;
    private final By submitButton;
    private final By thankYouMessage;

    public NetPromoterScoreSurveyForm(WebDriver driver) {
        super(driver);
        positiveRatingRadio = By.id("rating_10");
        neutralRatingRadio = By.id("rating_7");
        negativeRatingRadio = By.id("rating_0");
        commentsTextBox = By.id("surveyComment");
        submitButton = By.id("surveySubmitButton");
        closeButton = By.id("closeButton");
        thankYouMessage = By.id("surveyThankYou");
    }

    public NetPromoterScoreSurveyForm submitFeedback(FeedbackType feedbackType, String comment) {
        switch (feedbackType) {
            case Positive:
                findElement(positiveRatingRadio).click();
                break;
            case Neutral:
                findElement(neutralRatingRadio).click();
                break;
            case Negative:
                findElement(negativeRatingRadio).click();
                break;
        }

        findElement(commentsTextBox).sendKeys(comment);
        findElement(submitButton).click();
        waitForElement(thankYouMessage);
        return this;
    }

    public boolean thankYouMessageExists() {
        return elementExists(thankYouMessage);
    }

    public void closeWindow() {
        findElement(closeButton).click();
        switchBackToMainWindow();
    }
}
