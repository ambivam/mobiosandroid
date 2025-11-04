package com.automation.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

/**
 * Test Runner for iOS tests on Perfecto Cloud
 * Runs QA App BDD scenarios on iOS devices in Perfecto
 */
@CucumberOptions(
        features = "src/test/resources/features/QAAppLogin.feature",
        glue = {"com.automation.steps", "com.automation.hooks"},
        plugin = {
                "pretty",
                "html:test-output/cucumber-reports/perfecto-ios",
                "json:test-output/cucumber-reports/PerfectoiOS.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true,
        tags = "@qa-app and @smoke"
)
public class PerfectoiOSTestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    public void setSystemProperties() {
        // Set platform and execution type for this runner
        System.setProperty("platform", "ios");
        System.setProperty("execution.type", "perfecto");
        
        System.out.println("=== Perfecto iOS Test Runner ===");
        System.out.println("Platform: iOS");
        System.out.println("Execution: Perfecto Cloud");
        System.out.println("=================================");
    }

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
