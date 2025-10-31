package com.automation.tests;

import com.automation.driver.DriverFactory;
import com.automation.utils.AppAnalyzer;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class to analyze your QA App and discover element locators
 * Run this test first to understand your app structure
 * 
 * Instructions:
 * 1. Place your app-V1.14.10-QA.apk in src/test/resources/apps/
 * 2. Update the package name in android.properties
 * 3. Start Appium server
 * 4. Run this test to analyze your app
 */
public class QAAppAnalysisTest {

    @BeforeMethod
    public void setUp() {
        System.out.println("=== STARTING QA APP ANALYSIS ===");
        
        // Create driver for your app
        String platform = System.getProperty("platform", "android");
        String executionType = System.getProperty("execution.type", "local");
        
        DriverFactory.createDriver(platform, executionType);
        AppAnalyzer.setDriver();
        
        // Wait for app to load
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test(priority = 1, description = "Analyze the current screen structure")
    public void analyzeAppStructure() {
        System.out.println("\n🔍 ANALYZING APP STRUCTURE");
        AppAnalyzer.analyzeCurrentScreen();
    }

    @Test(priority = 2, description = "Find login-related elements")
    public void findLoginElements() {
        System.out.println("\n🔍 SEARCHING FOR LOGIN ELEMENTS");
        AppAnalyzer.findLoginElements();
    }

    @Test(priority = 3, description = "Generate suggested locators")
    public void generateLocators() {
        System.out.println("\n💡 GENERATING SUGGESTED LOCATORS");
        AppAnalyzer.generateSuggestedLocators();
    }

    @Test(priority = 4, description = "Test common element patterns", enabled = false)
    public void testCommonPatterns() {
        System.out.println("\n🧪 TESTING COMMON ELEMENT PATTERNS");
        
        // Test common ID patterns - update these based on your app
        System.out.println("\nTesting ID patterns:");
        AppAnalyzer.testElementExists("id", "com.yourapp.qa:id/username");
        AppAnalyzer.testElementExists("id", "com.yourapp.qa:id/password");
        AppAnalyzer.testElementExists("id", "com.yourapp.qa:id/loginButton");
        AppAnalyzer.testElementExists("id", "com.yourapp.qa:id/btnLogin");
        
        // Test XPath patterns
        System.out.println("\nTesting XPath patterns:");
        AppAnalyzer.testElementExists("xpath", "//android.widget.EditText[contains(@hint,'Username')]");
        AppAnalyzer.testElementExists("xpath", "//android.widget.EditText[contains(@hint,'Password')]");
        AppAnalyzer.testElementExists("xpath", "//android.widget.Button[contains(@text,'Login')]");
    }

    @Test(priority = 5, description = "Get full page source for detailed analysis", enabled = false)
    public void getPageSource() {
        System.out.println("\n📄 GETTING PAGE SOURCE");
        System.out.println("Note: This will print the entire XML structure of your app");
        AppAnalyzer.getPageSource();
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("\n=== ANALYSIS COMPLETE ===");
        System.out.println("Next steps:");
        System.out.println("1. Review the console output above");
        System.out.println("2. Update QAAppLoginPage.java with the correct locators");
        System.out.println("3. Update android.properties with correct package name");
        System.out.println("4. Run the actual login tests");
        
        DriverFactory.quitDriver();
    }
}
