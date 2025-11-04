package com.automation.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

/**
 * Unified Test Runner that supports multiple platforms and execution types
 * 
 * Usage:
 * - Android Emulator: mvn test -Dtest=UnifiedTestRunner -Dplatform=android -Dexecution.type=emulator
 * - Android Perfecto: mvn test -Dtest=UnifiedTestRunner -Dplatform=android -Dexecution.type=perfecto
 * - iOS Perfecto: mvn test -Dtest=UnifiedTestRunner -Dplatform=ios -Dexecution.type=perfecto
 * - BrowserStack: mvn test -Dtest=UnifiedTestRunner -Dplatform=android -Dexecution.type=browserstack
 */
@CucumberOptions(
        features = "src/test/resources/features/QAAppLogin.feature",
        glue = {"com.automation.steps", "com.automation.hooks"},
        plugin = {
                "pretty",
                "html:test-output/cucumber-reports/unified",
                "json:test-output/cucumber-reports/Unified.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true,
        tags = "@qa-app and @smoke"
)
public class UnifiedTestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    public void setSystemProperties() {
        // Get platform and execution type from system properties
        String platform = System.getProperty("platform", "android");
        String executionType = System.getProperty("execution.type", "emulator");
        String tags = System.getProperty("cucumber.filter.tags", "@qa-app and @smoke");
        
        System.out.println("=== Unified Test Runner ===");
        System.out.println("Platform: " + platform);
        System.out.println("Execution: " + executionType);
        System.out.println("Tags: " + tags);
        System.out.println("============================");
        
        // Validate configuration
        if ("ios".equalsIgnoreCase(platform) && "emulator".equalsIgnoreCase(executionType)) {
            System.out.println("⚠️  WARNING: iOS emulator support is limited. Consider using Perfecto or BrowserStack for iOS testing.");
        }
        
        if ("perfecto".equalsIgnoreCase(executionType)) {
            System.out.println("🚀 Running tests on Perfecto Cloud");
            System.out.println("📱 Make sure your Perfecto credentials are configured in perfecto.properties");
        }
    }

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
