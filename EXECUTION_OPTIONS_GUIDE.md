# Mobile Test Execution Options Guide

This guide explains how to run your QA App tests on different environments with simple flag switching.

## 🚀 Quick Start Commands

```bash
# Physical Device (Default)
run-tests-enhanced.bat android local

# Android Emulator
run-tests-enhanced.bat android emulator

# BrowserStack Cloud
run-tests-enhanced.bat android browserstack
```

## 📱 Execution Types Explained

### 1. **LOCAL** - Physical Device
**Best for**: Real device testing, performance testing, hardware-specific features

**Prerequisites**:
- Android device connected via USB
- USB Debugging enabled
- Device appears in `adb devices`

**Configuration**: Uses `android.properties`

**Example**:
```bash
run-tests-enhanced.bat android local "@smoke"
```

### 2. **EMULATOR** - Android Virtual Device
**Best for**: Development, CI/CD, testing multiple OS versions

**Prerequisites**:
- Android Studio installed
- AVD created (recommended: Pixel_7_API_33)
- Emulator running or will auto-start

**Configuration**: Uses `android-emulator.properties`

**Setup Steps**:
```bash
# 1. Check setup
mvn test -Dtest=EmulatorSetupTest

# 2. Run tests
run-tests-enhanced.bat android emulator "@regression"
```

### 3. **BROWSERSTACK** - Cloud Testing
**Best for**: Cross-device testing, CI/CD, parallel execution

**Prerequisites**:
- BrowserStack account
- APK uploaded to BrowserStack
- Credentials configured

**Configuration**: Uses `browserstack.properties`

**Setup Steps**:
```bash
# 1. Upload APK to BrowserStack
curl -u "username:key" -X POST "https://api-cloud.browserstack.com/app-automate/upload" -F "file=@app-V1.14.10-QA.apk"

# 2. Update browserstack.properties with app URL
# 3. Run tests
run-tests-enhanced.bat android browserstack "@smoke"
```

## ⚙️ Configuration Files

### Physical Device (`android.properties`)
```properties
platform.name=Android
platform.version=13.0
device.name=Pixel 7
automation.name=UiAutomator2
app.package=com.yourapp.qa
app.activity=.MainActivity
app.path=src/test/resources/apps/app-V1.14.10-QA.apk
appium.server.url=http://localhost:4723
```

### Emulator (`android-emulator.properties`)
```properties
platform.name=Android
platform.version=13.0
device.name=Pixel_7_API_33
automation.name=UiAutomator2
app.package=com.yourapp.qa
app.activity=.MainActivity
app.path=src/test/resources/apps/app-V1.14.10-QA.apk

# Emulator specific
avd.name=Pixel_7_API_33
device.udid=emulator-5554
auto.grant.permissions=true
new.command.timeout=300
```

### BrowserStack (`browserstack.properties`)
```properties
browserstack.username=YOUR_USERNAME
browserstack.access.key=YOUR_ACCESS_KEY
browserstack.url=https://hub-cloud.browserstack.com/wd/hub

browserstack.android.device=Google Pixel 7
browserstack.android.os.version=13.0
browserstack.android.app.url=bs://your-app-id

browserstack.project=QA App Testing
browserstack.build=Build 1.0
```

## 🎯 Execution Examples

### Smoke Tests
```bash
# Physical device
run-tests-enhanced.bat android local "@smoke"

# Emulator
run-tests-enhanced.bat android emulator "@smoke"

# BrowserStack
run-tests-enhanced.bat android browserstack "@smoke"
```

### Regression Tests
```bash
# Run all regression tests on emulator
run-tests-enhanced.bat android emulator "@regression"

# Run login tests only
run-tests-enhanced.bat android local "@qa-app and @login"
```

### Parallel Execution (BrowserStack)
```bash
# Multiple devices simultaneously
run-tests-enhanced.bat android browserstack "@smoke"
```

## 🔧 Setup Instructions

### 1. Physical Device Setup
```bash
# Enable Developer Options on your Android device
# Enable USB Debugging
# Connect device via USB

# Verify connection
adb devices

# Should show your device
```

### 2. Emulator Setup
```bash
# Check if setup is ready
mvn test -Dtest=EmulatorSetupTest

# Create AVD in Android Studio:
# Tools > AVD Manager > Create Virtual Device
# Choose: Pixel 7, API 33, Name: Pixel_7_API_33

# Verify AVD exists
emulator -list-avds
```

### 3. BrowserStack Setup
```bash
# 1. Sign up at browserstack.com
# 2. Get username and access key from Account Settings
# 3. Upload your APK
curl -u "username:key" -X POST \
  "https://api-cloud.browserstack.com/app-automate/upload" \
  -F "file=@src/test/resources/apps/app-V1.14.10-QA.apk"

# 4. Update browserstack.properties with:
#    - Your credentials
#    - App URL from upload response
```

## 🚨 Troubleshooting

### Physical Device Issues
```bash
# Device not detected
adb kill-server && adb start-server
adb devices

# Permission denied
# Enable USB Debugging in Developer Options
# Allow USB debugging when prompted on device
```

### Emulator Issues
```bash
# Emulator not starting
emulator -avd Pixel_7_API_33 -verbose

# Slow emulator
# Increase RAM in AVD settings (4GB recommended)
# Enable Hardware Acceleration (HAXM/Hyper-V)

# Connection timeout
adb kill-server && adb start-server
```

### BrowserStack Issues
```bash
# Authentication failed
# Check username/access key in browserstack.properties

# App not found
# Verify app URL starts with "bs://"
# Re-upload APK if needed

# Session timeout
# Check BrowserStack dashboard for session logs
```

## 📊 Comparison Table

| Feature | Physical Device | Emulator | BrowserStack |
|---------|----------------|----------|--------------|
| **Speed** | Fast | Medium | Medium |
| **Real Hardware** | ✅ | ❌ | ✅ |
| **Multiple Devices** | Limited | Limited | ✅ |
| **CI/CD Friendly** | ❌ | ✅ | ✅ |
| **Cost** | Free | Free | Paid |
| **Network Testing** | ✅ | Limited | ✅ |
| **Camera/Sensors** | ✅ | Limited | ✅ |
| **Parallel Execution** | ❌ | Limited | ✅ |

## 🎓 Best Practices for 4-5 Years Experience

### 1. **Development Phase**
- Use **emulator** for quick feedback
- Use **physical device** for final validation

### 2. **CI/CD Pipeline**
- Use **emulator** for PR validation
- Use **BrowserStack** for release testing

### 3. **Test Strategy**
```bash
# Daily development
run-tests-enhanced.bat android emulator "@smoke"

# Before release
run-tests-enhanced.bat android browserstack "@regression"

# Performance testing
run-tests-enhanced.bat android local "@performance"
```

### 4. **Debugging**
- **Emulator**: Easy to debug, can use Android Studio tools
- **Physical Device**: Real-world conditions
- **BrowserStack**: Check session recordings and logs

## 🔄 Switching Between Environments

The framework automatically handles configuration switching:

```java
// In DriverFactory.java
if ("browserstack".equalsIgnoreCase(executionType)) {
    driver = createBrowserStackDriver(platform, config);
} else if ("emulator".equalsIgnoreCase(executionType)) {
    driver = createEmulatorDriver(platform, config);
} else {
    driver = createLocalDriver(platform, config);
}
```

No code changes needed - just change the execution type parameter!

## 📋 Quick Reference

```bash
# Help
run-tests-enhanced.bat help

# Analysis (first time)
mvn test -Dtest=QAAppAnalysisTest

# Emulator setup check
mvn test -Dtest=EmulatorSetupTest

# Test execution
run-tests-enhanced.bat [platform] [execution_type] [tags]

# Examples
run-tests-enhanced.bat android local
run-tests-enhanced.bat android emulator "@smoke"
run-tests-enhanced.bat android browserstack "@regression"
```

**Remember**: Start simple with local/emulator, then scale to BrowserStack for comprehensive testing!
