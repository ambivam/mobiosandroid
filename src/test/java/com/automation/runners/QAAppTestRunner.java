package com.automation.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Test Runner specifically for QA App v1.14.10
 * Simple configuration for 4-5 years experienced automation developers
 */
@CucumberOptions(
        features = "src/test/resources/features/QAAppLogin.feature",
        glue = {"com.automation.steps", "com.automation.hooks"},
        plugin = {
                "pretty",
                "html:test-output/cucumber-reports/qa-app",
                "json:test-output/cucumber-reports/QAApp.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true,
        tags = "@qa-app and @smoke"
)
public class QAAppTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
