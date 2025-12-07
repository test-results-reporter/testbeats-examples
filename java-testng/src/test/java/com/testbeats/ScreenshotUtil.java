package com.testbeats;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for capturing screenshots on test failure and embedding them in TestNG reports
 */
public class ScreenshotUtil {

    private static final String SCREENSHOT_DIR = "target/screenshots";

    /**
     * Captures a screenshot when a test fails and embeds it in the TestNG report
     *
     * @param driver The WebDriver instance
     * @param result The TestNG test result
     */
    public static void captureScreenshot(WebDriver driver, ITestResult result) {
        if (driver == null || !(driver instanceof TakesScreenshot)) {
            return;
        }

        try {
            // Create screenshots directory if it doesn't exist
            Path screenshotDir = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
            }

            // Generate screenshot filename
            String className = result.getTestClass().getName();
            String methodName = result.getMethod().getMethodName();
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String filename = String.format("%s_%s_%s.png",
                className.substring(className.lastIndexOf('.') + 1),
                methodName,
                timestamp);

            // Take screenshot
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path destination = screenshotDir.resolve(filename);
            Files.copy(screenshotFile.toPath(), destination);

            // Get paths for the screenshot
            String absolutePath = destination.toAbsolutePath().toString();
            // Relative path from junitreports directory (where JUnit XML files are) to screenshots directory
            // From target/surefire-reports/junitreports/ to target/screenshots/
            String relativePath = "../../screenshots/" + filename;

            // Embed screenshot link in TestNG report using Reporter.log()
            // HTML format for TestNG HTML reports (clickable thumbnail)
            Reporter.log("<a href='" + absolutePath + "'> <img src='" + absolutePath +
                "' height='100' width='100'/> </a>");

            // JUnit XML attachment format - appears in <system-out> CDATA section
            // This format is recognized by tools like Allure, TestBeats, etc.
            Reporter.log("[[ATTACHMENT|" + relativePath + "]]");

            System.out.println("Screenshot saved to: " + absolutePath);
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}
