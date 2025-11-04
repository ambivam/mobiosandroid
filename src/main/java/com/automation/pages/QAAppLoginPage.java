package com.automation.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

/**
 * Login Page for QA App v1.14.10
 * This page contains all elements and actions related to login functionality
 * 
 * How to find elements:
 * 1. Use Appium Inspector to identify elements
 * 2. Update the locators below based on your actual app
 * 3. Test each locator to ensure it works
 */
public class QAAppLoginPage extends BasePage {

    // Login form elements - Cross-platform locators
    // Username field
    @AndroidFindBy(id = "com.stratis.estaffing:id/userText")
    @iOSXCUITFindBy(accessibility = "usernameField")
    private WebElement usernameField;

    // Password field
    @AndroidFindBy(id = "com.stratis.estaffing:id/passwordText")    
    @iOSXCUITFindBy(accessibility = "passwordField")
    private WebElement passwordField;   

    // Login button
    @AndroidFindBy(id = "com.stratis.estaffing:id/text")
    @iOSXCUITFindBy(accessibility = "loginButton")
    private WebElement loginButton;

    // Alternative locators if IDs are not available
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Username' or @hint='Email']")
    private WebElement usernameFieldByHint;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Password']")
    private WebElement passwordFieldByHint;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@text,'Login') or contains(@text,'Sign In')]")
    private WebElement loginButtonByText;

    // Error and success messages
    @AndroidFindBy(id = "com.yourapp.qa:id/errorMessage")
    @iOSXCUITFindBy(accessibility = "errorMessage")
    private WebElement errorMessage;

    // Welcome message (success indicator)
    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.stratis.estaffing:id/subtitle\" and @text=\"Welcome to eStratis.\"]")
    @iOSXCUITFindBy(accessibility = "welcomeMessage")
    private WebElement welcomeMessage;
    

    // Loading indicator
    @AndroidFindBy(id = "com.yourapp.qa:id/loadingSpinner")
    private WebElement loadingSpinner;

    // Forgot password link
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Forgot Password')]")
    private WebElement forgotPasswordLink;

    /**
     * Enter username in the username field
     * @param username - username to enter
     */
    public void enterUsername(String username) {
        System.out.println("Entering username: " + username);
        
        // Try primary locator first, then fallback
        try {
            sendKeys(usernameField, username);
        } catch (Exception e) {
            System.out.println("Primary username locator failed, trying alternative...");
            sendKeys(usernameFieldByHint, username);
        }
    }

    /**
     * Enter password in the password field
     * @param password - password to enter
     */
    public void enterPassword(String password) {
        System.out.println("Entering password");
        
        try {
            sendKeys(passwordField, password);
        } catch (Exception e) {
            System.out.println("Primary password locator failed, trying alternative...");
            sendKeys(passwordFieldByHint, password);
        }
    }

    /**
     * Click the login button
     */
    public void clickLoginButton() {
        System.out.println("Clicking login button");
        
        try {
            click(loginButton);
        } catch (Exception e) {
            System.out.println("Primary login button locator failed, trying alternative...");
            click(loginButtonByText);
        }
    }

    /**
     * Complete login process with username and password
     * @param username - username to login with
     * @param password - password to login with
     */
    public void login(String username, String password) {
        System.out.println("Performing login with username: " + username);
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        waitForLoginToComplete();
    }

    /**
     * Wait for login process to complete (loading spinner to disappear)
     */
    public void waitForLoginToComplete() {
        System.out.println("Waiting for login to complete...");
        try {
            // Wait for loading spinner to disappear if present
            if (isElementDisplayed(loadingSpinner)) {
                System.out.println("Loading spinner detected, waiting...");
                Thread.sleep(3000); // Simple wait - can be improved with explicit wait
            }
        } catch (Exception e) {
            // Loading spinner not present, continue
        }
    }

    /**
     * Get error message text
     * @return error message text
     */
    public String getErrorMessage() {
        System.out.println("Getting error message");
        try {
            return getText(errorMessage);
        } catch (Exception e) {
            System.out.println("No error message found");
            return "";
        }
    }

    /**
     * Check if welcome message is displayed (indicates successful login)
     * @return true if welcome message is displayed
     */
    public boolean isWelcomeMessageDisplayed() {
        System.out.println("Checking for welcome message");
        return isElementDisplayed(welcomeMessage);
    }

    /**
     * Get welcome message text
     * @return welcome message text
     */
    public String getWelcomeMessage() {
        System.out.println("Getting welcome message");
        return getText(welcomeMessage);
    }

    /**
     * Check if login button is displayed (indicates we're on login page)
     * @return true if login button is displayed
     */
    public boolean isLoginButtonDisplayed() {
        try {
            return isElementDisplayed(loginButton);
        } catch (Exception e) {
            return isElementDisplayed(loginButtonByText);
        }
    }

    /**
     * Check if username field is displayed
     * @return true if username field is displayed
     */
    public boolean isUsernameFieldDisplayed() {
        try {
            return isElementDisplayed(usernameField);
        } catch (Exception e) {
            return isElementDisplayed(usernameFieldByHint);
        }
    }

    /**
     * Click forgot password link
     */
    public void clickForgotPassword() {
        System.out.println("Clicking forgot password link");
        click(forgotPasswordLink);
    }

    /**
     * Check if error message is displayed
     * @return true if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    /**
     * Clear username field
     */
    public void clearUsername() {
        System.out.println("Clearing username field");
        try {
            usernameField.clear();
        } catch (Exception e) {
            usernameFieldByHint.clear();
        }
    }

    /**
     * Clear password field
     */
    public void clearPassword() {
        System.out.println("Clearing password field");
        try {
            passwordField.clear();
        } catch (Exception e) {
            passwordFieldByHint.clear();
        }
    }
}
