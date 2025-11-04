package com.automation.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

/**
 * Test Runner for Android tests on Perfecto Cloud
 * Runs QA App BDD scenarios on Android devices in Perfecto
 */
@CucumberOptions(
        features = "src/test/resources/features/QAAppLogin.feature",
        glue = {"com.automation.steps", "com.automation.hooks"},
        plugin = {
                "pretty",
                "html:test-output/cucumber-reports/perfecto-android",
                "json:test-output/cucumber-reports/PerfectoAndroid.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true,
        tags = "@qa-app and @smoke"
)
public class PerfectoAndroidTestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    public void setSystemProperties() {
        // Set platform and execution type for this runner
        System.setProperty("platform", "android");
        System.setProperty("execution.type", "perfecto");
        
        System.out.println("=== Perfecto Android Test Runner ===");
        System.out.println("Platform: Android");
        System.out.println("Execution: Perfecto Cloud");
        System.out.println("=====================================");
    }

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
