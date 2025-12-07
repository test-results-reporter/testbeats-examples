package com.testbeats;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for capturing screenshots on test failure
 */
public class ScreenshotUtil {

    private static final String SCREENSHOT_DIR = "target/screenshots";

    /**
     * Captures a screenshot when a test fails
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

            System.out.println("Screenshot saved to: " + destination.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}
