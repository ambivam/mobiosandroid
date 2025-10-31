package com.automation.utils;

import com.automation.driver.DriverFactory;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Utility class to help analyze your QA app and find element locators
 * This is especially useful for new apps where you need to discover elements
 */
public class AppAnalyzer {

    private static AppiumDriver driver;

    public static void setDriver() {
        driver = DriverFactory.getDriver();
    }

    /**
     * Print all elements on current screen with their properties
     * This helps you understand the app structure
     */
    public static void analyzeCurrentScreen() {
        System.out.println("=== ANALYZING CURRENT SCREEN ===");
        
        try {
            // Find all elements
            List<WebElement> allElements = driver.findElements(By.xpath("//*"));
            System.out.println("Total elements found: " + allElements.size());
            
            int count = 0;
            for (WebElement element : allElements) {
                count++;
                if (count > 20) break; // Limit output to first 20 elements
                
                try {
                    String tagName = element.getTagName();
                    String text = element.getText();
                    String resourceId = element.getAttribute("resource-id");
                    String contentDesc = element.getAttribute("content-desc");
                    String className = element.getAttribute("class");
                    
                    System.out.println("\n--- Element " + count + " ---");
                    System.out.println("Tag: " + tagName);
                    System.out.println("Class: " + className);
                    System.out.println("Resource-ID: " + resourceId);
                    System.out.println("Text: " + text);
                    System.out.println("Content-Desc: " + contentDesc);
                    
                } catch (Exception e) {
                    System.out.println("Could not get details for element " + count);
                }
            }
        } catch (Exception e) {
            System.out.println("Error analyzing screen: " + e.getMessage());
        }
    }

    /**
     * Find potential login elements based on common patterns
     */
    public static void findLoginElements() {
        System.out.println("\n=== SEARCHING FOR LOGIN ELEMENTS ===");
        
        // Look for username/email fields
        findElementsByPattern("Username/Email Fields:", new String[]{
            "//android.widget.EditText[contains(@hint,'username') or contains(@hint,'Username')]",
            "//android.widget.EditText[contains(@hint,'email') or contains(@hint,'Email')]",
            "//android.widget.EditText[contains(@text,'username') or contains(@text,'Username')]",
            "//*[contains(@resource-id,'username') or contains(@resource-id,'email')]"
        });

        // Look for password fields
        findElementsByPattern("Password Fields:", new String[]{
            "//android.widget.EditText[contains(@hint,'password') or contains(@hint,'Password')]",
            "//android.widget.EditText[@password='true']",
            "//*[contains(@resource-id,'password')]"
        });

        // Look for login buttons
        findElementsByPattern("Login Buttons:", new String[]{
            "//android.widget.Button[contains(@text,'Login') or contains(@text,'Sign In')]",
            "//android.widget.Button[contains(@text,'LOGIN') or contains(@text,'SIGN IN')]",
            "//*[contains(@resource-id,'login') or contains(@resource-id,'signin')]"
        });

        // Look for error message areas
        findElementsByPattern("Error Message Areas:", new String[]{
            "//*[contains(@resource-id,'error') or contains(@resource-id,'message')]",
            "//android.widget.TextView[contains(@text,'error') or contains(@text,'Error')]"
        });
    }

    /**
     * Helper method to find elements by XPath patterns
     */
    private static void findElementsByPattern(String category, String[] xpaths) {
        System.out.println("\n" + category);
        
        for (String xpath : xpaths) {
            try {
                List<WebElement> elements = driver.findElements(By.xpath(xpath));
                if (!elements.isEmpty()) {
                    System.out.println("  ✓ Found " + elements.size() + " element(s) with: " + xpath);
                    
                    for (int i = 0; i < Math.min(elements.size(), 3); i++) {
                        WebElement element = elements.get(i);
                        String resourceId = element.getAttribute("resource-id");
                        String text = element.getText();
                        String hint = element.getAttribute("hint");
                        
                        System.out.println("    - Resource ID: " + resourceId);
                        System.out.println("    - Text: " + text);
                        System.out.println("    - Hint: " + hint);
                    }
                }
            } catch (Exception e) {
                // XPath didn't match, continue
            }
        }
    }

    /**
     * Get page source for detailed analysis
     */
    public static void getPageSource() {
        System.out.println("\n=== PAGE SOURCE ===");
        try {
            String pageSource = driver.getPageSource();
            System.out.println(pageSource);
        } catch (Exception e) {
            System.out.println("Error getting page source: " + e.getMessage());
        }
    }

    /**
     * Test if specific element exists
     */
    public static boolean testElementExists(String locatorType, String locatorValue) {
        try {
            WebElement element;
            switch (locatorType.toLowerCase()) {
                case "id":
                    element = driver.findElement(By.id(locatorValue));
                    break;
                case "xpath":
                    element = driver.findElement(By.xpath(locatorValue));
                    break;
                case "class":
                    element = driver.findElement(By.className(locatorValue));
                    break;
                default:
                    System.out.println("Unsupported locator type: " + locatorType);
                    return false;
            }
            
            System.out.println("✓ Element found: " + locatorType + " = " + locatorValue);
            System.out.println("  Text: " + element.getText());
            System.out.println("  Displayed: " + element.isDisplayed());
            System.out.println("  Enabled: " + element.isEnabled());
            return true;
            
        } catch (Exception e) {
            System.out.println("✗ Element not found: " + locatorType + " = " + locatorValue);
            return false;
        }
    }

    /**
     * Generate suggested locators for common login elements
     */
    public static void generateSuggestedLocators() {
        System.out.println("\n=== SUGGESTED LOCATORS FOR YOUR PAGE OBJECT ===");
        
        System.out.println("// Try these locators in your QAAppLoginPage.java:");
        System.out.println("// Replace 'com.yourapp.qa' with your actual package name");
        System.out.println();
        
        System.out.println("// Username field options:");
        System.out.println("@AndroidFindBy(id = \"com.yourapp.qa:id/username\")");
        System.out.println("@AndroidFindBy(id = \"com.yourapp.qa:id/email\")");
        System.out.println("@AndroidFindBy(xpath = \"//android.widget.EditText[contains(@hint,'Username')]\")");
        System.out.println();
        
        System.out.println("// Password field options:");
        System.out.println("@AndroidFindBy(id = \"com.yourapp.qa:id/password\")");
        System.out.println("@AndroidFindBy(xpath = \"//android.widget.EditText[@password='true']\")");
        System.out.println();
        
        System.out.println("// Login button options:");
        System.out.println("@AndroidFindBy(id = \"com.yourapp.qa:id/loginButton\")");
        System.out.println("@AndroidFindBy(id = \"com.yourapp.qa:id/btnLogin\")");
        System.out.println("@AndroidFindBy(xpath = \"//android.widget.Button[contains(@text,'Login')]\")");
        System.out.println();
        
        System.out.println("// Error message options:");
        System.out.println("@AndroidFindBy(id = \"com.yourapp.qa:id/errorMessage\")");
        System.out.println("@AndroidFindBy(id = \"com.yourapp.qa:id/txtError\")");
        System.out.println("@AndroidFindBy(xpath = \"//android.widget.TextView[contains(@text,'error')]\")");
    }
}
