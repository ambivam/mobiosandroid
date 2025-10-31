package com.automation.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    // Updated locators for com.stratis.estaffing package
    @AndroidFindBy(id = "com.stratis.estaffing:id/username")
    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@resource-id,'username') or contains(@text,'Username') or contains(@hint,'Username')]")
    @iOSXCUITFindBy(id = "username")
    private WebElement usernameField;

    @AndroidFindBy(id = "com.stratis.estaffing:id/password")
    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@resource-id,'password') or contains(@text,'Password') or contains(@hint,'Password')]")
    @iOSXCUITFindBy(id = "password")
    private WebElement passwordField;

    @AndroidFindBy(id = "com.stratis.estaffing:id/login_button")
    @AndroidFindBy(xpath = "//android.widget.Button[contains(@text,'Login') or contains(@text,'Sign In') or contains(@resource-id,'login')]")
    @iOSXCUITFindBy(id = "loginButton")
    private WebElement loginButton;

    @AndroidFindBy(id = "com.stratis.estaffing:id/error_message")
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Error') or contains(@text,'Invalid') or contains(@resource-id,'error')]")
    @iOSXCUITFindBy(id = "errorMessage")
    private WebElement errorMessage;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Welcome']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Welcome']")
    private WebElement welcomeMessage;

    public void enterUsername(String username) {
        sendKeys(usernameField, username);
    }

    public void enterPassword(String password) {
        sendKeys(passwordField, password);
    }

    public void clickLoginButton() {
        click(loginButton);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isWelcomeMessageDisplayed() {
        return isElementDisplayed(welcomeMessage);
    }

    public String getWelcomeMessage() {
        return getText(welcomeMessage);
    }

    public boolean isLoginButtonDisplayed() {
        return isElementDisplayed(loginButton);
    }

    public boolean isUsernameFieldDisplayed() {
        return isElementDisplayed(usernameField);
    }

    public boolean isPasswordFieldDisplayed() {
        return isElementDisplayed(passwordField);
    }
}
