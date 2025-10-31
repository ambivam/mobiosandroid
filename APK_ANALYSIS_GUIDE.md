# QA App Analysis Guide

This guide will help you analyze your `app-V1.14.10-QA.apk` and create proper page objects for a 4-5 years experienced test automation developer.

## 🚀 Quick Setup

### Step 1: Prepare Your APK
1. Copy `app-V1.14.10-QA.apk` to `src/test/resources/apps/`
2. Install the app on your device/emulator manually first to understand the flow

### Step 2: Find Your App Package Name
```bash
# Method 1: Using aapt (Android Asset Packaging Tool)
aapt dump badging src/test/resources/apps/app-V1.14.10-QA.apk | grep package

# Method 2: Install app and check with adb
adb shell pm list packages | grep -i qa

# Method 3: Check app info on device
# Settings > Apps > Your QA App > Advanced > App details
```

### Step 3: Update Configuration
Update `src/test/resources/config/android.properties`:
```properties
# Replace with your actual package name
app.package=com.yourcompany.qaapp
app.activity=.MainActivity  # or .LoginActivity
```

## 🔍 Analysis Process

### Step 1: Run Analysis Test
```bash
# Start Appium server
appium

# Run analysis test
mvn test -Dtest=QAAppAnalysisTest -Dplatform=android
```

### Step 2: Review Console Output
The test will print:
- All elements on the screen
- Potential login elements
- Suggested locators
- Element properties

### Step 3: Use Appium Inspector (Recommended)
1. Start Appium Desktop
2. Start Inspector with these capabilities:
```json
{
  "platformName": "Android",
  "deviceName": "Your Device",
  "app": "path/to/app-V1.14.10-QA.apk",
  "automationName": "UiAutomator2"
}
```
3. Navigate to login screen
4. Click on elements to see their properties

## 🎯 What to Look For

### Login Elements
Look for these element types:

#### Username/Email Field
- **Resource ID**: `username`, `email`, `user_input`, `login_username`
- **Hint text**: "Username", "Email", "Enter username"
- **Class**: `android.widget.EditText`

#### Password Field
- **Resource ID**: `password`, `pwd`, `login_password`
- **Hint text**: "Password", "Enter password"
- **Attribute**: `password="true"`

#### Login Button
- **Resource ID**: `login`, `btn_login`, `sign_in`, `loginButton`
- **Text**: "Login", "Sign In", "Submit"
- **Class**: `android.widget.Button`

#### Error Messages
- **Resource ID**: `error`, `error_message`, `login_error`
- **Class**: `android.widget.TextView`

## 📝 Update Page Object

Based on your findings, update `QAAppLoginPage.java`:

```java
// Example - replace with your actual locators
@AndroidFindBy(id = "com.yourcompany.qaapp:id/et_username")
private WebElement usernameField;

@AndroidFindBy(id = "com.yourcompany.qaapp:id/et_password")
private WebElement passwordField;

@AndroidFindBy(id = "com.yourcompany.qaapp:id/btn_login")
private WebElement loginButton;
```

## 🧪 Test Your Locators

### Method 1: Use AppAnalyzer
```java
// In your test
AppAnalyzer.testElementExists("id", "com.yourcompany.qaapp:id/et_username");
```

### Method 2: Simple Test
```java
@Test
public void testLocators() {
    QAAppLoginPage loginPage = new QAAppLoginPage();
    
    // Test if elements are found
    Assert.assertTrue(loginPage.isUsernameFieldDisplayed());
    Assert.assertTrue(loginPage.isLoginButtonDisplayed());
}
```

## 🔧 Common Issues & Solutions

### Issue 1: Package Name Not Found
**Solution**: 
- Check if app is installed: `adb shell pm list packages`
- Use `aapt dump badging your-app.apk` to get package info

### Issue 2: Elements Not Found
**Solutions**:
- Use Appium Inspector to verify locators
- Try XPath instead of ID: `//android.widget.EditText[contains(@hint,'Username')]`
- Check if elements are in different activities

### Issue 3: App Doesn't Start
**Solutions**:
- Verify APK path is correct
- Check if app is compatible with your device/emulator
- Try installing manually first

### Issue 4: Slow Element Loading
**Solutions**:
- Add explicit waits in BasePage
- Use `waitForElementToBeVisible()` before interactions

## 📋 Locator Strategy Priority

For 4-5 years experience developers, use this priority:

1. **Resource ID** (Most reliable)
   ```java
   @AndroidFindBy(id = "com.app:id/username")
   ```

2. **Accessibility ID** (Good for cross-platform)
   ```java
   @AndroidFindBy(accessibility = "username_field")
   ```

3. **XPath with attributes** (Flexible)
   ```java
   @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Username']")
   ```

4. **XPath with text** (Last resort)
   ```java
   @AndroidFindBy(xpath = "//android.widget.Button[contains(@text,'Login')]")
   ```

## 🎯 Best Practices

### 1. Multiple Locators
```java
// Primary locator
@AndroidFindBy(id = "com.app:id/username")
private WebElement usernameField;

// Fallback locator
@AndroidFindBy(xpath = "//android.widget.EditText[@hint='Username']")
private WebElement usernameFieldFallback;

public void enterUsername(String username) {
    try {
        sendKeys(usernameField, username);
    } catch (Exception e) {
        sendKeys(usernameFieldFallback, username);
    }
}
```

### 2. Descriptive Method Names
```java
public boolean isOnLoginScreen() {
    return isUsernameFieldDisplayed() && isLoginButtonDisplayed();
}

public void performCompleteLogin(String username, String password) {
    enterUsername(username);
    enterPassword(password);
    clickLoginButton();
    waitForLoginToComplete();
}
```

### 3. Clear Error Handling
```java
public String getErrorMessage() {
    try {
        return getText(errorMessage);
    } catch (Exception e) {
        System.out.println("No error message displayed");
        return "";
    }
}
```

## 🚀 Next Steps

1. **Run Analysis**: Execute `QAAppAnalysisTest`
2. **Update Locators**: Modify `QAAppLoginPage.java` with correct locators
3. **Test Locators**: Run simple tests to verify elements are found
4. **Run Login Tests**: Execute `QAAppLogin.feature` scenarios
5. **Iterate**: Refine locators based on test results

## 📞 Troubleshooting Commands

```bash
# Check connected devices
adb devices

# Check if app is installed
adb shell pm list packages | grep qa

# Get app info
adb shell dumpsys package com.yourapp.qa

# Clear app data
adb shell pm clear com.yourapp.qa

# Uninstall app
adb uninstall com.yourapp.qa

# Install app
adb install src/test/resources/apps/app-V1.14.10-QA.apk
```

Remember: **Start simple, test often, and iterate based on what works with your specific app!**
