package com.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.automation.driver.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleExtentReportManager {
    private static ExtentReports extent;
    private static ExtentTest currentTest;

    public static void initializeReport() {
        if (extent == null) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String reportName = "TestReport_" + timestamp + ".html";
            
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/extent-reports/" + reportName);
            sparkReporter.config().setDocumentTitle("Mobile Test Report");
            sparkReporter.config().setReportName("Mobile Tests");
            sparkReporter.config().setTheme(Theme.STANDARD);
            
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            
            extent.setSystemInfo("Platform", System.getProperty("platform", "Android"));
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            
            System.out.println("Report initialized");
        }
    }

    public static void createTest(String testName) {
        if (extent == null) {
            initializeReport();
        }
        currentTest = extent.createTest(testName);
        System.out.println("Test created: " + testName);
    }

    public static void logPass(String message) {
        if (currentTest != null) {
            currentTest.log(Status.PASS, message);
        }
    }

    public static void logFail(String message) {
        if (currentTest != null) {
            currentTest.log(Status.FAIL, message);
        }
    }

    public static String captureScreenshot(String testName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) DriverFactory.getDriver();
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = testName.replaceAll("[^a-zA-Z0-9]", "_") + "_" + timestamp + ".png";
            
            File destFile = new File("test-output/screenshots/" + fileName);
            destFile.getParentFile().mkdirs();
            
            FileUtils.copyFile(sourceFile, destFile);
            System.out.println("Screenshot saved: " + destFile.getAbsolutePath());
            
            return destFile.getAbsolutePath();
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }

    public static void attachScreenshot(String screenshotPath) {
        if (currentTest != null && screenshotPath != null) {
            currentTest.addScreenCaptureFromPath(screenshotPath);
        }
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
            System.out.println("Report saved");
        }
    }
}
