package com.automation.pages;

import com.automation.driver.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BasePage {
    protected AppiumDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    protected void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void click(WebElement element) {
        waitForElementToBeClickable(element);
        element.click();
        System.out.println("Clicked on element");
    }

    protected void sendKeys(WebElement element, String text) {
        waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
        System.out.println("Entered text: " + text);
    }

    protected String getText(WebElement element) {
        waitForElementToBeVisible(element);
        String text = element.getText();
        System.out.println("Retrieved text: " + text);
        return text;
    }

    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void scrollToElement(WebElement element) {
        driver.executeScript("arguments[0].scrollIntoView(true);", element);
        System.out.println("Scrolled to element");
    }

    public String takeScreenshot(String testName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = testName + "_" + timestamp + ".png";
            
            File destFile = new File("test-output/screenshots/" + fileName);
            destFile.getParentFile().mkdirs();
            
            FileUtils.copyFile(sourceFile, destFile);
            System.out.println("Screenshot saved: " + destFile.getAbsolutePath());
            
            return destFile.getAbsolutePath();
        } catch (IOException e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }

    protected void waitForPageToLoad() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
