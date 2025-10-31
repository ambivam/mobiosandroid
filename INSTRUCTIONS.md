# Mobile Test Execution Instructions

This guide provides detailed instructions for executing QA App tests on emulator and BrowserStack without using batch files.

## 🚀 Quick Commands (No Batch Files)

### Local Emulator Execution
```bash
# Basic execution (all tests)
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=emulator

# With specific tags
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=emulator -Dcucumber.filter.tags="@qa-app and @smoke"
```

### BrowserStack Execution
```bash
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=browserstack -Dcucumber.filter.tags="@qa-app and @smoke"
```

### Physical Device Execution
```bash
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=local -Dcucumber.filter.tags="@qa-app and @smoke"
```

## ✅ **Framework Status: READY**
- **Compilation**: ✅ All errors fixed
- **Step Definitions**: ✅ Duplicate issues resolved
- **Configuration**: ✅ Emulator settings updated
- **Ready to run**: ✅ Just need your APK and package name

---

## 📱 EMULATOR EXECUTION GUIDE

### Prerequisites

#### 1. Install Android Studio
- Download from: https://developer.android.com/studio
- Install with default settings
- Launch Android Studio at least once

#### 2. Create Android Virtual Device (AVD)
1. Open Android Studio
2. Click **Tools** → **AVD Manager**
3. Click **Create Virtual Device**
4. Select **Phone** → **Pixel 7** → **Next**
5. Select **API Level 33** (Android 13) → **Download** if needed → **Next**
6. Name: `Pixel_7_API_33`
7. Click **Finish**

#### 3. Verify Setup
```bash
# Check if emulator command is available
emulator -version

# List available AVDs
emulator -list-avds

# Should show: Pixel_7_API_33
```

### Step-by-Step Execution

#### Step 1: Prepare Your Environment
```bash
# Navigate to project directory
cd c:\mobileautomation

# Verify your APK is in place
dir src\test\resources\apps\app-V1.14.10-QA.apk

# ✅ Framework is ready - no compilation errors!
```

#### Step 2: Start Emulator (Optional - will auto-start if needed)
```bash
# Start emulator manually (optional)
emulator -avd Pixel_7_API_33

# Or start with specific settings
emulator -avd Pixel_7_API_33 -no-snapshot-save -wipe-data
```

#### Step 3: Verify Emulator Connection
```bash
# Check connected devices
adb devices

# Should show something like:
# emulator-5554    device
```

#### Step 4: Run Tests on Emulator
```bash
# Basic smoke tests
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=emulator

# Specific tags
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=emulator -Dcucumber.filter.tags="@smoke"

# Login tests only
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=emulator -Dcucumber.filter.tags="@qa-app and @login"

# All regression tests
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=emulator -Dcucumber.filter.tags="@regression"
```

### Emulator Configuration

The framework uses `android-emulator.properties`:
```properties
# Emulator Configuration (✅ Updated to match available emulator)
platform.name=Android
platform.version=16
device.name=Android
avd.name=Pixel_7_API_33
device.udid=emulator-5554
auto.grant.permissions=true
new.command.timeout=300
app.path=src/test/resources/apps/app-V1.14.10-QA.apk
```

### Troubleshooting Emulator

#### Issue: Emulator not starting
```bash
# Solution 1: Check AVD exists
emulator -list-avds

# Solution 2: Start with verbose logging
emulator -avd Pixel_7_API_33 -verbose

# Solution 3: Wipe data and restart
emulator -avd Pixel_7_API_33 -wipe-data
```

#### Issue: Emulator too slow
1. Increase RAM in AVD settings (4GB recommended)
2. Enable Hardware Acceleration:
   - Windows: Install HAXM or enable Hyper-V
   - Enable in AVD Manager → Advanced Settings

#### Issue: ADB connection problems
```bash
# Restart ADB
adb kill-server
adb start-server
adb devices
```

---

## ☁️ BROWSERSTACK EXECUTION GUIDE

### Prerequisites

#### 1. BrowserStack Account
- Sign up at: https://www.browserstack.com/
- Get username and access key from Account Settings

#### 2. Upload Your APK to BrowserStack
```bash
# Upload APK using curl
curl -u "YOUR_USERNAME:YOUR_ACCESS_KEY" \
  -X POST "https://api-cloud.browserstack.com/app-automate/upload" \
  -F "file=@src/test/resources/apps/app-V1.14.10-QA.apk"

# Response will contain app_url like: bs://abc123def456
```

#### 3. Configure BrowserStack Properties
Edit `src/test/resources/config/browserstack.properties`:
```properties
# Your BrowserStack credentials
browserstack.username=YOUR_ACTUAL_USERNAME
browserstack.access.key=YOUR_ACTUAL_ACCESS_KEY
browserstack.url=https://hub-cloud.browserstack.com/wd/hub

# Android Configuration
browserstack.android.device=Google Pixel 7
browserstack.android.os.version=13.0
browserstack.android.app.url=bs://YOUR_APP_ID_FROM_UPLOAD

# Project Settings
browserstack.project=QA App v1.14.10 Testing
browserstack.build=Build_1.0
browserstack.debug=true
browserstack.video=true
browserstack.network.logs=true
```

### Step-by-Step BrowserStack Execution

#### Step 1: Verify Configuration
```bash
# Test connection (optional)
curl -u "YOUR_USERNAME:YOUR_ACCESS_KEY" \
  https://api-cloud.browserstack.com/app-automate/plan.json
```

#### Step 2: Run Tests on BrowserStack
```bash
# Basic smoke tests
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=browserstack

# Specific test scenarios
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=browserstack -Dcucumber.filter.tags="@smoke"

# Login functionality tests
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=browserstack -Dcucumber.filter.tags="@qa-app and @login"

# Full regression suite
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=browserstack -Dcucumber.filter.tags="@regression"
```

#### Step 3: Monitor Execution
- Visit BrowserStack Dashboard: https://app-automate.browserstack.com/
- View live sessions, videos, and logs
- Download session recordings

### BrowserStack Advanced Options

#### Multiple Device Testing
Update `browserstack.properties` for different devices:
```properties
# For Samsung Galaxy S22
browserstack.android.device=Samsung Galaxy S22
browserstack.android.os.version=12.0

# For Google Pixel 6
browserstack.android.device=Google Pixel 6
browserstack.android.os.version=12.0
```

#### Parallel Execution
```bash
# Run tests in parallel (requires BrowserStack plan)
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=browserstack -DthreadCount=3
```

### Troubleshooting BrowserStack

#### Issue: Authentication failed
```bash
# Verify credentials
curl -u "USERNAME:ACCESS_KEY" https://api-cloud.browserstack.com/app-automate/plan.json
```

#### Issue: App not found
1. Re-upload APK to BrowserStack
2. Update `browserstack.android.app.url` with new app ID
3. Ensure app ID starts with `bs://`

#### Issue: Session timeout
1. Check BrowserStack dashboard for error logs
2. Increase timeout in `browserstack.properties`:
```properties
new.command.timeout=600
command.timeout=600
```

---

## 🎯 Test Execution Examples

### Smoke Tests (Quick validation)
```bash
# Emulator
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=emulator -Dcucumber.filter.tags="@smoke"

# BrowserStack
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=browserstack -Dcucumber.filter.tags="@smoke"
```

### Login Tests Only
```bash
# Emulator
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=emulator -Dcucumber.filter.tags="@qa-app and @login"

# BrowserStack
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=browserstack -Dcucumber.filter.tags="@qa-app and @login"
```

### Regression Tests (Full suite)
```bash
# Emulator
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=emulator -Dcucumber.filter.tags="@regression"

# BrowserStack
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=browserstack -Dcucumber.filter.tags="@regression"
```

### Custom Tag Combinations
```bash
# High priority tests only
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=emulator -Dcucumber.filter.tags="@high-priority"

# Negative test cases
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=emulator -Dcucumber.filter.tags="@negative"

# UI tests only
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=emulator -Dcucumber.filter.tags="@ui"
```

---

## 📊 Reports and Results

### Generated Reports
After test execution, reports are available at:
- **Extent Reports**: `test-output/extent-reports/`
- **Cucumber Reports**: `test-output/cucumber-reports/qa-app/`
- **Screenshots**: `test-output/screenshots/`

### Viewing Reports
```bash
# Open Extent Report (Windows)
start test-output/extent-reports/TestReport_*.html

# Open Cucumber Report
start test-output/cucumber-reports/qa-app/index.html
```

---

## 📋 **Next Steps**

1. **Place your APK**: Copy `app-V1.14.10-QA.apk` to `src/test/resources/apps/`
2. **Find package name**: Use methods below to get your app's package name
3. **Update package name**: Edit `android-emulator.properties` with your app's package
4. **Analyze your app**: Run `mvn test -Dtest=QAAppAnalysisTest`
5. **Update locators**: Modify `QAAppLoginPage.java` with correct element locators
6. **Run tests**: Execute with your preferred execution type

### 🔍 **Finding Your App Package Name**

#### Method 1: Install APK and Check (Recommended)
```bash
# Install your APK
adb install src/test/resources/apps/app-V1.14.10-QA.apk

# Find the package name
adb shell pm list packages | findstr -i qa
# or check recent installations
adb shell pm list packages -3
```

#### Method 2: Using Android Studio (Detailed Steps)
1. **Open Android Studio**
2. **Go to Build Menu**: Click **Build** → **Analyze APK...**
3. **Browse for APK**: Navigate to `c:\mobileautomation\src\test\resources\apps\app-V1.14.10-QA.apk`
4. **Select the APK** and click **OK**
5. **View AndroidManifest.xml**: 
   - In the APK Analyzer window, click on **AndroidManifest.xml**
   - Look for the `<manifest>` tag at the top
   - Find the `package` attribute: `<manifest package="com.example.qaapp" ...>`
6. **Copy the package name**: The value in quotes after `package=` is your app's package name
7. **Optional**: You can also see the main activity by looking for `<activity android:name=".MainActivity"` or similar

**Example of what you'll see:**
```xml
<manifest package="com.yourcompany.qaapp" 
          android:versionCode="1" 
          android:versionName="1.14.10">
```
The package name would be: `com.yourcompany.qaapp`

#### Method 3: Online APK Analyzer
- Use online tools like `apkanalyzer.com`
- Upload your APK to view package information

#### Method 4: If you have aapt available
```bash
aapt dump badging src/test/resources/apps/app-V1.14.10-QA.apk | findstr package
```

Once you find the package name, update `android-emulator.properties`:
```properties
app.package=com.your.actual.package.name
app.activity=.MainActivity
```

---

## 🔧 Setup Verification Commands

### Check Emulator Setup
```bash
mvn test -Dtest=EmulatorSetupTest
```

### Analyze Your App Structure
```bash
mvn test -Dtest=QAAppAnalysisTest -Dplatform=android
```

### Verify All Configurations
```bash
# Check if all config files exist
dir src\test\resources\config\*.properties

# Should show:
# android.properties
# android-emulator.properties
# browserstack.properties
```

---

## 💡 Tips for 4-5 Years Experience Developers

### 1. **Development Workflow**
```bash
# Quick feedback during development
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=emulator -Dcucumber.filter.tags="@smoke"
```

### 2. **CI/CD Integration**
```bash
# For Jenkins/GitHub Actions
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=browserstack -Dcucumber.filter.tags="@regression" -Dmaven.test.failure.ignore=true
```

### 3. **Debugging Failed Tests**
```bash
# Run single scenario for debugging
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=emulator -Dcucumber.filter.tags="@qa-app and @login" -Dcucumber.options="--name 'Successful login'"
```

### 4. **Performance Testing**
```bash
# Use physical device for performance tests
mvn clean test -Dtest=QAAppTestRunner -Dplatform=android -Dexecution.type=local -Dcucumber.filter.tags="@performance"
```

---

## 🚨 Common Issues and Solutions

### Maven Command Not Found
```bash
# Add Maven to PATH or use full path
C:\apache-maven-3.8.6\bin\mvn clean test ...
```

### Java Version Issues
```bash
# Check Java version (requires Java 11+)
java -version

# Set JAVA_HOME if needed
set JAVA_HOME=C:\Program Files\Java\jdk-11.0.16
```

### Appium Server Not Running
```bash
# Install and start Appium
npm install -g appium
appium

# Or use Appium Desktop
```

### Port Already in Use
```bash
# Kill process using port 4723
netstat -ano | findstr :4723
taskkill /PID <process_id> /F

# Start Appium on different port
appium --port 4724
```

---

**Remember**: No batch files needed! Use Maven commands directly for maximum control and flexibility.
