package com.testbeats;

import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG listener to capture screenshots on test failure
 * Note: Screenshots are primarily captured in @AfterMethod, but this listener
 * serves as a backup in case @AfterMethod doesn't execute properly.
 */
public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        // Get the WebDriver instance from the test class
        Object testInstance = result.getInstance();
        WebDriver driver = null;

        // Try to get driver from common test classes
        if (testInstance instanceof TestBeatsHomePageTests) {
            driver = ((TestBeatsHomePageTests) testInstance).getDriver();
        } else if (testInstance instanceof TestBeatsPricingPageTests) {
            driver = ((TestBeatsPricingPageTests) testInstance).getDriver();
        }

        if (driver != null) {
            ScreenshotUtil.captureScreenshot(driver, result);
        }
    }
}
