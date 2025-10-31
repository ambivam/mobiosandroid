package com.automation.hooks;

import com.automation.driver.DriverFactory;
import com.automation.utils.ExtentReportManager;
import com.automation.utils.EmulatorHelper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("Starting scenario: " + scenario.getName());
        
        // Get platform and execution type from system properties or default values
        String platform = System.getProperty("platform", "android");
        String executionType = System.getProperty("execution.type", "emulator");
        
        System.out.println("Creating driver for: " + platform + " on " + executionType);
        DriverFactory.createDriver(platform, executionType);
        
        // Create test in report
        ExtentReportManager.createTest(scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                System.out.println("Scenario failed: " + scenario.getName());
                
                // Take screenshot on failure
                String screenshotPath = ExtentReportManager.captureScreenshot(scenario.getName());
                if (screenshotPath != null) {
                    ExtentReportManager.attachScreenshot(screenshotPath);
                }
                
                ExtentReportManager.logFail("Test failed");
            } else {
                System.out.println("Scenario passed: " + scenario.getName());
                ExtentReportManager.logPass("Test passed");
            }
        } catch (Exception e) {
            System.out.println("Error in tearDown: " + e.getMessage());
        } finally {
            DriverFactory.quitDriver();
        }
    }
}
