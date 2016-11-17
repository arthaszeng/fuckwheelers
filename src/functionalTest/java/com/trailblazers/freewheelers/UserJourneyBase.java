package com.trailblazers.freewheelers;

import com.trailblazers.freewheelers.apis.AdminApi;
import com.trailblazers.freewheelers.apis.ScreenApi;
import com.trailblazers.freewheelers.apis.UserApi;
import com.trailblazers.freewheelers.configuration.ToggledFeature;
import com.trailblazers.freewheelers.pages.NetPromoterScoreReportPage;
import com.trailblazers.freewheelers.pages.NetPromoterScoreSurveyForm;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.togglz.junit.TogglzRule;

import java.io.File;
import java.io.IOException;

public class UserJourneyBase {

    private static WebDriver driver;

    protected static AdminApi admin;
    protected static UserApi user;
    protected static ScreenApi screen;

    // Specific for NPS Flow
    protected static NetPromoterScoreSurveyForm nps_survey_form;
    protected static NetPromoterScoreReportPage nps_report_page;

    @BeforeClass
    public static void before() {
        driver = new FirefoxDriver();

        admin = new AdminApi();
        user = new UserApi(driver);
        screen = new ScreenApi(driver);

        // NPS Pages
        nps_survey_form = new NetPromoterScoreSurveyForm(driver);
        nps_report_page = new NetPromoterScoreReportPage(driver);
    }

    @AfterClass
    public static void after() {
        driver.close();
    }

    @Rule
    public TestRule testWatcher = new TestWatcher() {
        private String screenshots_dir = System.getProperty("user.dir") + "/build/functionalTests/snapshots/";;

        @Override
        public void failed(Throwable e, Description d) {
            takeScreenshot(d);
        }

        private void takeScreenshot(Description d) {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String scrFilename = d.getClassName() + "." + d.getMethodName();
            try {
                FileUtils.forceMkdir(new File(screenshots_dir));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            File screenshotPath = new File(screenshots_dir, scrFilename + ".png");
            try {
                FileUtils.copyFile(scrFile, screenshotPath);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    };

    @Rule
    public TogglzRule togglzRule = TogglzRule.allEnabled(ToggledFeature.class);
}
