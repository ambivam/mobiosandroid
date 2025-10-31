# Quick Reference - No Batch Files

## ✅ **Framework Status: READY TO USE**
- **Compilation**: ✅ All errors fixed
- **Step Definitions**: ✅ Duplicate issues resolved  
- **Configuration**: ✅ Emulator settings updated
- **Tests**: ✅ Ready to run with your APK

## 🚀 Essential Commands

### Emulator Execution
```bash
# Basic execution (all tests)
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=emulator

# Smoke tests on emulator
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=emulator -Dcucumber.filter.tags="@smoke"

# All login tests on emulator
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=emulator -Dcucumber.filter.tags="@qa-app and @login"

# Regression tests on emulator
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=emulator -Dcucumber.filter.tags="@regression"
```

### BrowserStack Execution
```bash
# Smoke tests on BrowserStack
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=browserstack -Dcucumber.filter.tags="@smoke"

# All login tests on BrowserStack
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=browserstack -Dcucumber.filter.tags="@qa-app and @login"

# Regression tests on BrowserStack
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=browserstack -Dcucumber.filter.tags="@regression"
```

## 🔧 Setup Commands

### First Time Setup
```bash
# Check emulator setup
mvn test -Dtest=EmulatorSetupTest

# Analyze your app
mvn test -Dtest=QAAppAnalysisTest -Dplatform=android

# Verify devices
adb devices
```

### Emulator Management
```bash
# List AVDs
emulator -list-avds

# Start emulator
emulator -avd Pixel_7_API_33

# Check devices
adb devices
```

## 📱 Configuration Files to Update

### For Emulator: `android-emulator.properties`
```properties
# ✅ Updated configuration (working)
app.package=com.yourapp.qa  # ← Update with your actual package name
app.activity=.MainActivity
avd.name=Pixel_7_API_33
device.udid=emulator-5554
platform.version=16
```

## 🔍 **Finding Your App Package Name**

### Quick Methods:
```bash
# Method 1: Install and check (Recommended)
adb install src/test/resources/apps/app-V1.14.10-QA.apk
adb shell pm list packages | findstr -i qa

# Method 2: If aapt is available
aapt dump badging src/test/resources/apps/app-V1.14.10-QA.apk | findstr package
```

### Alternative Methods:

#### Android Studio (GUI Method):
1. Open Android Studio
2. **Build** → **Analyze APK...**
3. Select your `app-V1.14.10-QA.apk`
4. Click **AndroidManifest.xml**
5. Look for: `<manifest package="com.yourapp.name" ...>`
6. Copy the package name from the `package` attribute

#### Online Tools:
- Use **apkanalyzer.com** to upload and analyze your APK
- View package information without installing tools

## 🔧 Actual Desired Capabilities Used

### Emulator Capabilities (Appium 2.0 Format)
```json
{
  "platformName": "Android",
  "appium:automationName": "UiAutomator2",
  "appium:udid": "emulator-5554",
  "appium:deviceName": "Android",
  "appium:platformVersion": "16",
  "appium:appPackage": "com.yourapp.qa",
  "appium:appActivity": ".MainActivity",
  "appium:avd": "Pixel_7_API_33",
  "appium:noReset": false,
  "appium:fullReset": false,
  "appium:autoGrantPermissions": true,
  "appium:autoAcceptAlerts": true,
  "appium:newCommandTimeout": 300,
  "appium:appWaitTimeout": 30000,
  "appium:appWaitActivity": "*",
  "appium:app": "/path/to/app-V1.14.10-QA.apk"
}
```

### For BrowserStack: `browserstack.properties`
```properties
browserstack.username=YOUR_USERNAME
browserstack.access.key=YOUR_ACCESS_KEY
browserstack.android.app.url=bs://YOUR_APP_ID
```

## 🎯 Available Test Tags

- `@smoke` - Quick validation tests
- `@regression` - Full test suite
- `@login` - Login functionality
- `@negative` - Error scenarios
- `@high-priority` - Critical tests
- `@qa-app` - All QA app tests

## 📊 Reports Location

- Extent Reports: `test-output/extent-reports/`
- Screenshots: `test-output/screenshots/`
- Cucumber Reports: `test-output/cucumber-reports/qa-app/`
