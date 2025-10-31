package com.automation.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import java.util.List;

public class HomePage extends BasePage {

    @AndroidFindBy(id = "com.example.app:id/welcome_text")
    @iOSXCUITFindBy(id = "welcomeText")
    private WebElement welcomeText;

    @AndroidFindBy(id = "com.example.app:id/menu_button")
    @iOSXCUITFindBy(id = "menuButton")
    private WebElement menuButton;

    @AndroidFindBy(id = "com.example.app:id/profile_button")
    @iOSXCUITFindBy(id = "profileButton")
    private WebElement profileButton;

    @AndroidFindBy(id = "com.example.app:id/settings_button")
    @iOSXCUITFindBy(id = "settingsButton")
    private WebElement settingsButton;

    @AndroidFindBy(id = "com.example.app:id/logout_button")
    @iOSXCUITFindBy(id = "logoutButton")
    private WebElement logoutButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.example.app:id/item_title']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[contains(@name,'item_')]")
    private List<WebElement> menuItems;

    @AndroidFindBy(id = "com.example.app:id/search_box")
    @iOSXCUITFindBy(id = "searchBox")
    private WebElement searchBox;

    @AndroidFindBy(id = "com.example.app:id/search_button")
    @iOSXCUITFindBy(id = "searchButton")
    private WebElement searchButton;

    public String getWelcomeText() {
        return getText(welcomeText);
    }

    public boolean isWelcomeTextDisplayed() {
        return isElementDisplayed(welcomeText);
    }

    public void clickMenuButton() {
        click(menuButton);
    }

    public void clickProfileButton() {
        click(profileButton);
    }

    public void clickSettingsButton() {
        click(settingsButton);
    }

    public void clickLogoutButton() {
        click(logoutButton);
    }

    public int getMenuItemsCount() {
        return menuItems.size();
    }

    public void clickMenuItemByIndex(int index) {
        if (index >= 0 && index < menuItems.size()) {
            click(menuItems.get(index));
        } else {
            throw new IndexOutOfBoundsException("Menu item index " + index + " is out of bounds");
        }
    }

    public void clickMenuItemByText(String itemText) {
        for (WebElement item : menuItems) {
            if (getText(item).equals(itemText)) {
                click(item);
                return;
            }
        }
        throw new RuntimeException("Menu item with text '" + itemText + "' not found");
    }

    public void performSearch(String searchTerm) {
        sendKeys(searchBox, searchTerm);
        click(searchButton);
    }

    public boolean isSearchBoxDisplayed() {
        return isElementDisplayed(searchBox);
    }

    public void navigateToProfile() {
        clickMenuButton();
        waitForPageToLoad();
        clickProfileButton();
    }

    public void navigateToSettings() {
        clickMenuButton();
        waitForPageToLoad();
        clickSettingsButton();
    }

    public void logout() {
        clickMenuButton();
        waitForPageToLoad();
        clickLogoutButton();
    }
}
