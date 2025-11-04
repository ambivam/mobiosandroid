# iOS and Android Mobile Automation Instructions

## Overview
This guide explains how to use the unified Perfecto mobile automation framework that supports both Android and iOS platforms. The solution is designed to be simple and easy to understand for automation engineers with 5+ years of experience.

## Project Structure
```
src/test/java/com/perfecto/sample/
├── PlatformConfig.java                    # Configuration for platform-specific settings
├── UnifiedPerfectoSample.java             # Full-featured test with Perfecto Reportium
├── ModernUnifiedPerfectoSample.java       # Modern version with simplified reporting
└── (your additional test classes)

src/test/resources/apps/
├── app-V1.14.10-QA.apk                   # Android application
└── eStratis.ipa                          # iOS application

run-unified-tests.bat                      # Windows batch script to run tests
```

## Available Test Classes

### 1. ModernUnifiedPerfectoSample.java (Recommended)
- **Compatible with:** Appium 8.x, Selenium 4.x
- **Features:** Modern WebElement API, simplified console reporting
- **Dependencies:** Only requires standard Appium and Selenium libraries
- **Best for:** Quick setup and learning

### 2. UnifiedPerfectoSample.java (Advanced)
- **Compatible with:** Older Appium versions with MobileElement
- **Features:** Full Perfecto Reportium integration, detailed reporting
- **Dependencies:** Requires Perfecto Reportium SDK
- **Best for:** Production environments with comprehensive reporting needs

## Key Features
- **Single codebase** for both Android and iOS testing
- **Easy platform switching** by changing one variable
- **Centralized configuration** for app paths, credentials, and device settings
- **Platform-specific element handling** with automatic locator selection
- **Comprehensive reporting** with Perfecto's reporting system

## Quick Start Guide

### Step 1: Configure Your Environment

1. **Update Cloud Configuration**
   - Open `UnifiedPerfectoSample.java`
   - Replace `<<cloud name>>` with your Perfecto cloud name (e.g., "demo" for demo.perfectomobile.com)
   - Replace `<<security token>>` with your actual Perfecto security token

2. **Update Platform Configuration**
   - Open `PlatformConfig.java`
   - Update app paths to match your uploaded apps:
     ```java
     public static final String ANDROID_APP_PATH = "PRIVATE:apps/app-V1.14.10-QA.apk";
     public static final String IOS_APP_PATH = "PRIVATE:apps/eStratis.ipa";
     ```
   - Update app identifiers (package/bundle IDs):
     ```java
     public static final String ANDROID_APP_PACKAGE = "com.yourcompany.yourapp";
     public static final String IOS_BUNDLE_ID = "com.yourcompany.yourapp";
     ```

### Step 2: Choose Your Platform

In `UnifiedPerfectoSample.java`, change the PLATFORM variable:

**For Android Testing:**
```java
private static final String PLATFORM = "Android";
```

**For iOS Testing:**
```java
private static final String PLATFORM = "iOS";
```

### Step 3: Run Your Test

Execute the test using your preferred method:
- IDE: Right-click on `UnifiedPerfectoSample.java` → Run
- Maven: `mvn test -Dtest=UnifiedPerfectoSample`
- Command line: `java -cp [classpath] com.perfecto.sample.UnifiedPerfectoSample`

## Detailed Configuration Guide

### App Upload to Perfecto

1. **Upload Android APK:**
   ```bash
   # Using Perfecto CLI or web interface
   # Upload to: Repository → apps → app-V1.14.10-QA.apk
   ```

2. **Upload iOS IPA:**
   ```bash
   # Using Perfecto CLI or web interface  
   # Upload to: Repository → apps → eStratis.ipa
   ```

3. **Update paths in PlatformConfig.java:**
   ```java
   // Use PRIVATE: for your uploaded apps
   public static final String ANDROID_APP_PATH = "PRIVATE:apps/your-android-app.apk";
   public static final String IOS_APP_PATH = "PRIVATE:apps/your-ios-app.ipa";
   
   // Or use PUBLIC: for Perfecto's sample apps
   public static final String ANDROID_APP_PATH = "PUBLIC:ExpenseTracker/Native/android/ExpenseAppVer1.0.apk";
   public static final String IOS_APP_PATH = "PUBLIC:ExpenseTracker/Native/iOS/InvoiceApp1.0.ipa";
   ```

### Device Selection

Modify device patterns in `PlatformConfig.java`:

**Android Devices:**
```java
public static final String ANDROID_DEVICE_MODEL = "Galaxy S.*|LG.*|Pixel.*";
// Examples:
// "Galaxy S.*"           - Any Samsung Galaxy S series
// "Pixel.*"              - Any Google Pixel device  
// "Galaxy S20|Galaxy S21" - Specific models only
```

**iOS Devices:**
```java
public static final String IOS_DEVICE_MODEL = "iPhone.*";
// Examples:
// "iPhone.*"             - Any iPhone
// "iPhone 12.*|iPhone 13.*" - iPhone 12 and 13 series
// "iPad.*"               - Any iPad
```

### Test Credentials

Update test credentials in `PlatformConfig.java`:
```java
public static final String TEST_EMAIL = "your-test@email.com";
public static final String TEST_PASSWORD = "your-test-password";
```

## Understanding the Code Structure

### 1. Platform Detection
The framework automatically detects the platform and configures the appropriate driver:
```java
// Creates AndroidDriver for Android, IOSDriver for iOS
AppiumDriver<MobileElement> driver = createDriver(PLATFORM);
```

### 2. Element Location Strategy
The framework uses different locator strategies for each platform:

**Android (uses ID):**
```java
case "email":
    locator = By.id("login_email");
    break;
```

**iOS (uses Name/Accessibility ID):**
```java
case "email":
    locator = By.name("login_email");
    break;
```

### 3. Platform-Specific Actions
Some actions require different approaches:

**Category Selection:**
- **Android:** Click dropdown → Select option by text
- **iOS:** Click field → Interact with picker wheel

**Alert Verification:**
- **Android:** Find by text attribute
- **iOS:** Find by name/accessibility ID

## Customizing for Your Application

### Adding New Test Steps

1. **Add new element types** in the `findElement()` method:
```java
case "your_new_element":
    if (ANDROID) {
        locator = By.id("android_element_id");
    } else {
        locator = By.name("ios_element_name");
    }
    break;
```

2. **Add new test actions** in the `runTestSteps()` method:
```java
reportiumClient.stepStart("Your new step");
MobileElement newElement = findElement(driver, wait, "your_new_element");
newElement.click(); // or other actions
reportiumClient.stepEnd();
```

### Handling Different App Flows

If your Android and iOS apps have different workflows:

```java
private static void handlePlatformSpecificFlow(AppiumDriver<MobileElement> driver, WebDriverWait wait) {
    if (PlatformConfig.ANDROID.equalsIgnoreCase(PLATFORM)) {
        // Android-specific flow
        MobileElement androidElement = findElement(driver, wait, "android_specific_element");
        androidElement.click();
    } else {
        // iOS-specific flow  
        MobileElement iosElement = findElement(driver, wait, "ios_specific_element");
        iosElement.swipe(SwipeElementDirection.UP, 1000);
    }
}
```

## Best Practices

### 1. Element Identification
- **Android:** Prefer `resource-id` over other attributes
- **iOS:** Use `accessibility-id` or `name` attributes
- **Both:** Avoid XPath when possible for better performance

### 2. Wait Strategies
```java
// Good: Explicit waits with expected conditions
WebDriverWait wait = new WebDriverWait(driver, 30);
wait.until(ExpectedConditions.elementToBeClickable(locator));

// Avoid: Thread.sleep() - makes tests slower and unreliable
```

### 3. Error Handling
```java
try {
    // Test steps
} catch (Exception e) {
    // Log the error with context
    System.err.println("Failed at step: " + currentStep + ", Error: " + e.getMessage());
    // Take screenshot for debugging
    // Report failure to Perfecto
    throw e;
}
```

### 4. Configuration Management
- Keep all platform-specific configurations in `PlatformConfig.java`
- Use constants instead of hardcoded values
- Document any app-specific requirements

## Troubleshooting

### Common Issues

1. **App Not Found Error**
   - Verify app path in `PlatformConfig.java`
   - Check if app is uploaded to Perfecto repository
   - Ensure correct PRIVATE: or PUBLIC: prefix

2. **Element Not Found Error**
   - Verify element locators using Perfecto's Object Spy
   - Check if element IDs/names are correct for your app version
   - Add explicit waits for dynamic elements

3. **Driver Initialization Failed**
   - Verify cloud name and security token
   - Check device availability in your Perfecto cloud
   - Ensure device model pattern matches available devices

4. **Platform-Specific Issues**
   - **Android:** Check if app package name is correct
   - **iOS:** Verify bundle ID and ensure app is properly signed

### Debug Tips

1. **Enable verbose logging:**
```java
capabilities.setCapability("takesScreenshot", true);
capabilities.setCapability("screenshotOnError", true);
```

2. **Use Perfecto's reporting:**
```java
// Add debug information to reports
reportiumClient.stepStart("Debug: Checking element visibility");
// Your debug code here
reportiumClient.stepEnd();
```

3. **Local debugging:**
```java
// Add console output for debugging
System.out.println("Current platform: " + PLATFORM);
System.out.println("Looking for element: " + elementType);
```

## Advanced Features

### Running Tests in Parallel

For parallel execution across platforms:

```java
// Create separate test classes or use TestNG parallel execution
@Test(groups = {"android"})
public void testAndroid() {
    // Set PLATFORM = "Android" 
}

@Test(groups = {"ios"})  
public void testIOS() {
    // Set PLATFORM = "iOS"
}
```

### Data-Driven Testing

Integrate with TestNG/JUnit for data-driven tests:

```java
@DataProvider
public Object[][] testData() {
    return new Object[][] {
        {"Android", "test1@email.com", "password1"},
        {"iOS", "test2@email.com", "password2"}
    };
}

@Test(dataProvider = "testData")
public void testMultiplePlatforms(String platform, String email, String password) {
    // Use parameters in your test
}
```

## Support and Resources

- **Perfecto Documentation:** [https://developers.perfectomobile.com/](https://developers.perfectomobile.com/)
- **Appium Documentation:** [http://appium.io/docs/](http://appium.io/docs/)
- **Sample Apps:** Available in Perfecto's PUBLIC repository

## Summary

This unified framework provides:
- ✅ Single codebase for both platforms
- ✅ Easy platform switching
- ✅ Centralized configuration
- ✅ Comprehensive reporting
- ✅ Best practices implementation
- ✅ Easy customization for your specific apps

The solution is designed to be maintainable and understandable for automation engineers, allowing teams to efficiently test both Android and iOS applications with minimal code duplication.
