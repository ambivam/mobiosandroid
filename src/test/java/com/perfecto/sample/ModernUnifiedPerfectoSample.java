package com.perfecto.sample;

import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.HidesKeyboard;

/**
 * Modern Unified Perfecto Native Sample that supports both Android and iOS platforms
 * Compatible with Appium 8.x and Selenium 4.x
 * 
 * This version uses WebElement instead of deprecated MobileElement
 * and includes simplified reporting without Perfecto Reportium dependency
 * 
 * How to use:
 * 1. Set the PLATFORM variable to either "Android" or "iOS"
 * 2. Update your cloud name and security token
 * 3. Update app paths and identifiers in PlatformConfig.java
 * 4. Run the test
 */
public class ModernUnifiedPerfectoSample {

    // CONFIGURATION: Change this to "Android" or "iOS" to run tests on different platforms
    private static final String PLATFORM = "Android"; // Change this to "iOS" for iOS testing
    
    // CONFIGURATION: Replace with your Perfecto cloud details
    private static final String CLOUD_NAME = "trial"; // e.g., "demo" for demo.perfectomobile.com
    private static final String SECURITY_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI2ZDM2NmJiNS01NDAyLTQ4MmMtYTVhOC1kODZhODk4MDYyZjIifQ.eyJpYXQiOjE3NjIyNDc4NjcsImp0aSI6ImYxMjc3MzU2LTA3NTYtNGZmNC1iZGY5LWEzMzg1YTI4MzZlZiIsImlzcyI6Imh0dHBzOi8vYXV0aDMucGVyZmVjdG9tb2JpbGUuY29tL2F1dGgvcmVhbG1zL3RyaWFsLXBlcmZlY3RvbW9iaWxlLWNvbSIsImF1ZCI6Imh0dHBzOi8vYXV0aDMucGVyZmVjdG9tb2JpbGUuY29tL2F1dGgvcmVhbG1zL3RyaWFsLXBlcmZlY3RvbW9iaWxlLWNvbSIsInN1YiI6ImZiOTA1YmU1LWU4YzEtNDdiNS1iNjBiLTA1M2Y3OGI1ZjdjMSIsInR5cCI6Ik9mZmxpbmUiLCJhenAiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsIm5vbmNlIjoiNDNiNTg0ODYtOTlhNC00ZGM4LTk3ZDctMTg2ZGYyMDczZTA2Iiwic2Vzc2lvbl9zdGF0ZSI6ImM3MTUzNzk3LWNjMzYtNDE3MC05MmE1LWNjZDc2NmQ4MzNmMyIsInNjb3BlIjoib3BlbmlkIG9mZmxpbmVfYWNjZXNzIHByb2ZpbGUgZW1haWwiLCJzaWQiOiJjNzE1Mzc5Ny1jYzM2LTQxNzAtOTJhNS1jY2Q3NjZkODMzZjMifQ.kDVuoZqRF2Z4XooJ-SY5HbHgbzuzjuFCQTJ5u3WTg10"; // Your Perfecto security token
    
    public static void main(String[] args) throws Exception {
        
        System.out.println("========================================");
        System.out.println("Modern Unified Perfecto Sample");
        System.out.println("Platform: " + PLATFORM);
        System.out.println("========================================");
        
        // Create driver based on platform
        AppiumDriver driver = createDriver(PLATFORM);
        
        // Setting implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        try {
            System.out.println("Starting test execution...");
            
            // Run the test steps
            runTestSteps(driver);
            
            System.out.println("✅ Test completed successfully!");
            
        } catch (Exception e) {
            System.err.println("❌ Test failed with error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            // Quit driver
            if (driver != null) {
                System.out.println("Closing driver...");
                driver.quit();
            }
        }
    }
    
    /**
     * Creates the appropriate driver based on platform
     * @param platform - "Android" or "iOS"
     * @return AppiumDriver instance
     */
    private static AppiumDriver createDriver(String platform) throws Exception {
        
        DesiredCapabilities capabilities = new DesiredCapabilities("", "", Platform.ANY);
        
        // Set common capabilities
        capabilities.setCapability("securityToken", SECURITY_TOKEN);
        capabilities.setCapability("enableAppiumBehavior", true);
        capabilities.setCapability("autoLaunch", true);
        capabilities.setCapability("takesScreenshot", false);
        capabilities.setCapability("screenshotOnError", true);
        capabilities.setCapability("openDeviceTimeout", 5);
        
        String hubUrl = "https://" + CLOUD_NAME + ".perfectomobile.com/nexperience/perfectomobile/wd/hub";
        
        // Set platform-specific capabilities
        if (PlatformConfig.ANDROID.equalsIgnoreCase(platform)) {
            System.out.println("Configuring Android driver...");
            
            // Android-specific capabilities
            capabilities.setCapability("platformName", PlatformConfig.ANDROID);
            capabilities.setCapability("model", PlatformConfig.getDeviceModel(platform));
            capabilities.setCapability("app", PlatformConfig.getAppPath(platform));
            capabilities.setCapability("appPackage", PlatformConfig.getAppIdentifier(platform));
            
            // Create Android driver
            return new AndroidDriver(new URL(hubUrl), capabilities);
                
        } else if (PlatformConfig.IOS.equalsIgnoreCase(platform)) {
            System.out.println("Configuring iOS driver...");
            
            // iOS-specific capabilities
            capabilities.setCapability("platformName", PlatformConfig.IOS);
            capabilities.setCapability("model", PlatformConfig.getDeviceModel(platform));
            capabilities.setCapability("app", PlatformConfig.getAppPath(platform));
            capabilities.setCapability("bundleId", PlatformConfig.getAppIdentifier(platform));
            capabilities.setCapability("iOSResign", true);
            
            // Create iOS driver
            return new IOSDriver(new URL(hubUrl), capabilities);
        } else {
            throw new IllegalArgumentException("Unsupported platform: " + platform + ". Use 'Android' or 'iOS'");
        }
    }
    
    /**
     * Run the main test steps - this method contains the actual test logic
     * @param driver - The AppiumDriver instance
     */
    private static void runTestSteps(AppiumDriver driver) {
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        
        try {
            // Step 1: Enter email
            System.out.println("Step 1: Entering email...");
            WebElement emailField = findElement(driver, wait, "email");
            emailField.sendKeys(PlatformConfig.TEST_EMAIL);
            System.out.println("✓ Email entered successfully");

            // Step 2: Enter password
            System.out.println("Step 2: Entering password...");
            WebElement passwordField = findElement(driver, wait, "password");
            passwordField.sendKeys(PlatformConfig.TEST_PASSWORD);
            System.out.println("✓ Password entered successfully");

            // Step 3: Click login
            System.out.println("Step 3: Clicking login button...");
            try {
                if (driver instanceof HidesKeyboard) {
                    ((HidesKeyboard) driver).hideKeyboard();
                }
            } catch (Exception e) {
                System.out.println("Note: Keyboard hide not available or needed");
            }
            WebElement loginButton = findElement(driver, wait, "login");
            loginButton.click();
            System.out.println("✓ Login button clicked successfully");

            // Step 4: Add expense
            System.out.println("Step 4: Adding expense...");
            WebElement addExpenseButton = findElement(driver, wait, "add_expense");
            addExpenseButton.click();
            System.out.println("✓ Add expense button clicked successfully");

            // Step 5: Select head/category
            System.out.println("Step 5: Selecting expense category...");
            selectExpenseCategory(driver, wait);
            System.out.println("✓ Expense category selected successfully");

            // Step 6: Enter amount
            System.out.println("Step 6: Entering amount...");
            WebElement amountField = findElement(driver, wait, "amount");
            amountField.sendKeys("100");
            System.out.println("✓ Amount entered successfully");

            // Step 7: Save expense
            System.out.println("Step 7: Saving expense...");
            WebElement saveButton = findElement(driver, wait, "save");
            saveButton.click();
            System.out.println("✓ Save button clicked successfully");

            // Step 8: Verify alert/validation message
            System.out.println("Step 8: Verifying validation message...");
            verifyValidationMessage(driver, wait);
            System.out.println("✓ Validation message verified successfully");
            
        } catch (Exception e) {
            System.err.println("❌ Error in test steps: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Find element based on platform-specific locators
     * @param driver - The AppiumDriver instance
     * @param wait - WebDriverWait instance
     * @param elementType - Type of element to find (email, password, login, etc.)
     * @return WebElement
     */
    private static WebElement findElement(AppiumDriver driver, WebDriverWait wait, String elementType) {
        
        By locator;
        
        if (PlatformConfig.ANDROID.equalsIgnoreCase(PLATFORM)) {
            // Android locators (using ID)
            switch (elementType.toLowerCase()) {
                case "email":
                    locator = By.id("login_email");
                    break;
                case "password":
                    locator = By.id("login_password");
                    break;
                case "login":
                    locator = By.id("login_login_btn");
                    break;
                case "add_expense":
                    locator = By.id("list_add_btn");
                    break;
                case "head":
                    locator = By.id("input_layout_head");
                    break;
                case "amount":
                    locator = By.id("add_amount");
                    break;
                case "save":
                    locator = By.id("layout_buttons");
                    break;
                default:
                    throw new IllegalArgumentException("Unknown element type: " + elementType);
            }
        } else {
            // iOS locators (using name/accessibility ID)
            switch (elementType.toLowerCase()) {
                case "email":
                    locator = By.name("login_email");
                    break;
                case "password":
                    locator = By.name("login_password");
                    break;
                case "login":
                    locator = By.name("login_login_btn");
                    break;
                case "add_expense":
                    locator = By.name("list_add_btn");
                    break;
                case "head":
                    locator = By.name("edit_head");
                    break;
                case "amount":
                    locator = By.name("edit_amount");
                    break;
                case "save":
                    locator = By.name("add_save_btn");
                    break;
                default:
                    throw new IllegalArgumentException("Unknown element type: " + elementType);
            }
        }
        
        System.out.println("Looking for element: " + elementType + " using locator: " + locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * Handle platform-specific expense category selection
     * @param driver - The AppiumDriver instance
     * @param wait - WebDriverWait instance
     */
    private static void selectExpenseCategory(AppiumDriver driver, WebDriverWait wait) {
        
        if (PlatformConfig.ANDROID.equalsIgnoreCase(PLATFORM)) {
            // Android: Click on head field, then select Flight option
            WebElement headField = findElement(driver, wait, "head");
            headField.click();
            
            WebElement flightOption = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@text=\"Flight\"]")));
            flightOption.click();
            
        } else {
            // iOS: Click on head field, then interact with picker
            WebElement headField = findElement(driver, wait, "head");
            headField.click();
            
            List<WebElement> picker = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@value=\"- Select -\"]")));
            if (!picker.isEmpty()) {
                picker.get(0).sendKeys("Flight");
            }
        }
    }
    
    /**
     * Verify platform-specific validation messages
     * @param driver - The AppiumDriver instance
     * @param wait - WebDriverWait instance
     */
    private static void verifyValidationMessage(AppiumDriver driver, WebDriverWait wait) {
        
        String expectedText;
        WebElement alertElement;
        
        if (PlatformConfig.ANDROID.equalsIgnoreCase(PLATFORM)) {
            // Android validation message
            expectedText = "Select Currency";
            alertElement = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@text='" + expectedText + "']")));
        } else {
            // iOS validation message
            expectedText = "Please enter valid category";
            alertElement = wait.until(
                ExpectedConditions.elementToBeClickable(By.name(expectedText)));
        }
        
        // Verify the alert text matches expected
        String actualText = alertElement.getText();
        boolean isTextCorrect = actualText.equalsIgnoreCase(expectedText);
        
        if (isTextCorrect) {
            System.out.println("✓ Validation message verified: '" + expectedText + "'");
        } else {
            throw new AssertionError("Validation message mismatch. Expected: '" + expectedText + "', Actual: '" + actualText + "'");
        }
    }
}
