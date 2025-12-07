package com.testbeats;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * TestBeats Pricing Page Tests
 */
public class TestBeatsPricingPageTests {

    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeMethod
    public void setUp() {
        // Setup Chrome driver with headless mode for CI/CD
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @AfterMethod
    public void tearDown() {
        // Screenshots are captured automatically by ScreenshotListener on test failure
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void shouldHaveCorrectTitle_PASS() {
        // This test will pass
        driver.get("https://testbeats.com/pricing");
        String title = driver.getTitle();
        assertTrue(title.contains("TestBeats Pricing"), "Title should contain 'TestBeats Pricing'");
    }

    @Test
    public void shouldHaveCorrectTitle_FAIL() {
        // This test will fail intentionally
        driver.get("https://testbeats.com/pricing");
        String title = driver.getTitle();
        assertTrue(title.contains("Wrong Title That Does Not Exist"),
                   "Title should contain 'Wrong Title That Does Not Exist'");
    }
}
