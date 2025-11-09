package com.automation.utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * eStratis Element Inspector
 * Run this utility to find the correct locators for username and password fields
 */
public class eStratisElementInspector {
    
    private AppiumDriver driver;
    
    public eStratisElementInspector(AppiumDriver driver) {
        this.driver = driver;
    }
    
    /**
     * Inspect and find username field using multiple strategies
     */
    public void inspectUsernameField() {
        System.out.println("=== INSPECTING USERNAME FIELD ===");
        
        // Strategy 1: Find by common iOS text field attributes
        findByStrategy("XPath - Text Fields", "//XCUIElementTypeTextField");
        
        // Strategy 2: Find by placeholder/value containing username
        findByStrategy("XPath - Username Value", "//*[@value='Please enter your username']");
        findByStrategy("XPath - Username Placeholder", "//*[@placeholder='Username']");
        findByStrategy("XPath - Username Label", "//*[contains(@value,'username')]");
        
        // Strategy 3: Find by accessibility id
        findByStrategy("Accessibility ID", "usernameField");
        findByStrategy("Accessibility ID Alt", "username");
        findByStrategy("Accessibility ID Alt2", "Username");
        
        // Strategy 4: Find by iOS class chain
        findByClassChain();
        
        // Strategy 5: Find by iOS predicate
        findByPredicate();
        
        // Strategy 6: Inspect all text fields and their attributes
        inspectAllTextFields();
    }
    
    private void findByStrategy(String strategyName, String locator) {
        try {
            System.out.println("\n--- " + strategyName + " ---");
            System.out.println("Locator: " + locator);
            
            List<WebElement> elements = driver.findElements(By.xpath(locator));
            if (!elements.isEmpty()) {
                System.out.println("✅ FOUND " + elements.size() + " element(s)");
                for (int i = 0; i < elements.size(); i++) {
                    WebElement element = elements.get(i);
                    printElementDetails(element, i + 1);
                }
            } else {
                System.out.println("❌ No elements found");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    private void findByClassChain() {
        System.out.println("\n--- iOS Class Chain ---");
        String[] classChains = {
            "**/XCUIElementTypeTextField[1]",
            "**/XCUIElementTypeTextField[`value CONTAINS 'username'`]",
            "**/XCUIElementTypeTextField[`placeholder CONTAINS 'username'`]",
            "**/XCUIElementTypeStaticText[`label == 'Username'`]/..//XCUIElementTypeTextField"
        };
        
        for (String chain : classChains) {
            try {
                System.out.println("Class Chain: " + chain);
                List<WebElement> elements = driver.findElements(By.xpath("-ios class chain:" + chain));
                if (!elements.isEmpty()) {
                    System.out.println("✅ FOUND with class chain");
                    printElementDetails(elements.get(0), 1);
                } else {
                    System.out.println("❌ Not found");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }
    
    private void findByPredicate() {
        System.out.println("\n--- iOS Predicate ---");
        String[] predicates = {
            "type == 'XCUIElementTypeTextField'",
            "type == 'XCUIElementTypeTextField' AND value CONTAINS[c] 'username'",
            "type == 'XCUIElementTypeTextField' AND placeholder CONTAINS[c] 'username'",
            "type == 'XCUIElementTypeTextField' AND value == 'Please enter your username'"
        };
        
        for (String predicate : predicates) {
            try {
                System.out.println("Predicate: " + predicate);
                List<WebElement> elements = driver.findElements(By.xpath("-ios predicate string:" + predicate));
                if (!elements.isEmpty()) {
                    System.out.println("✅ FOUND with predicate");
                    printElementDetails(elements.get(0), 1);
                } else {
                    System.out.println("❌ Not found");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }
    
    private void inspectAllTextFields() {
        System.out.println("\n=== ALL TEXT FIELDS INSPECTION ===");
        try {
            List<WebElement> textFields = driver.findElements(By.xpath("//XCUIElementTypeTextField"));
            System.out.println("Found " + textFields.size() + " text fields:");
            
            for (int i = 0; i < textFields.size(); i++) {
                System.out.println("\n--- Text Field " + (i + 1) + " ---");
                printElementDetails(textFields.get(i), i + 1);
            }
        } catch (Exception e) {
            System.out.println("❌ Error inspecting text fields: " + e.getMessage());
        }
    }
    
    private void printElementDetails(WebElement element, int index) {
        try {
            System.out.println("Element " + index + ":");
            System.out.println("  Text: '" + element.getText() + "'");
            System.out.println("  Value: '" + element.getAttribute("value") + "'");
            System.out.println("  Placeholder: '" + element.getAttribute("placeholder") + "'");
            System.out.println("  Name: '" + element.getAttribute("name") + "'");
            System.out.println("  Label: '" + element.getAttribute("label") + "'");
            System.out.println("  AccessibilityId: '" + element.getAttribute("accessibilityIdentifier") + "'");
            System.out.println("  Type: '" + element.getAttribute("type") + "'");
            System.out.println("  Enabled: '" + element.getAttribute("enabled") + "'");
            System.out.println("  Visible: '" + element.getAttribute("visible") + "'");
            System.out.println("  Rect: '" + element.getRect() + "'");
        } catch (Exception e) {
            System.out.println("  Error getting element details: " + e.getMessage());
        }
    }
    
    /**
     * Generate recommended locators based on inspection
     */
    public void generateRecommendedLocators() {
        System.out.println("\n=== RECOMMENDED LOCATORS ===");
        System.out.println("Based on inspection, try these locators in order of preference:");
        System.out.println();
        System.out.println("1. XPath with value attribute:");
        System.out.println("   @iOSXCUITFindBy(xpath = \"//*[@value='Please enter your username']\")");
        System.out.println();
        System.out.println("2. XPath with placeholder:");
        System.out.println("   @iOSXCUITFindBy(xpath = \"//*[@placeholder='Username']\")");
        System.out.println();
        System.out.println("3. Accessibility ID:");
        System.out.println("   @iOSXCUITFindBy(accessibility = \"usernameField\")");
        System.out.println();
        System.out.println("4. iOS Predicate:");
        System.out.println("   @iOSXCUITFindBy(iOSNsPredicate = \"type == 'XCUIElementTypeTextField' AND value CONTAINS[c] 'username'\")");
        System.out.println();
        System.out.println("5. iOS Class Chain:");
        System.out.println("   @iOSXCUITFindBy(iOSClassChain = \"**/XCUIElementTypeTextField[1]\")");
    }
}
