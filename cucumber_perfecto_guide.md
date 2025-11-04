# Cucumber BDD Tests with Perfecto Cross-Platform Support

## 🎯 **Overview**

Your Cucumber BDD tests now support **both Android and iOS platforms** on **multiple execution environments**:
- ✅ **Local Android Emulator**
- ✅ **Perfecto Cloud (Android & iOS)**
- ✅ **BrowserStack (Android & iOS)**

The same BDD scenarios from `QAAppLogin.feature` can run on any platform with just a parameter change!

---

## 🚀 **Quick Execution Commands**

### **Perfecto Cloud Execution**

#### Android on Perfecto:
```bash
# Using dedicated runner
mvn clean test -Dtest=PerfectoAndroidTestRunner

# Using unified runner
mvn clean test -Dtest=UnifiedTestRunner -Dplatform=android -Dexecution.type=perfecto
```

#### iOS on Perfecto:
```bash
# Using dedicated runner
mvn clean test -Dtest=PerfectoiOSTestRunner

# Using unified runner
mvn clean test -Dtest=UnifiedTestRunner -Dplatform=ios -Dexecution.type=perfecto
```

### **Local Emulator Execution**
```bash
# Android emulator
mvn clean test -Dtest=UnifiedTestRunner -Dplatform=android -Dexecution.type=emulator

# Original QA App runner (Android emulator)
mvn clean test -Dtest=QAAppTestRunner
```

### **BrowserStack Execution**
```bash
# Android on BrowserStack
mvn clean test -Dtest=UnifiedTestRunner -Dplatform=android -Dexecution.type=browserstack

# iOS on BrowserStack
mvn clean test -Dtest=UnifiedTestRunner -Dplatform=ios -Dexecution.type=browserstack
```

---

## ⚙️ **Configuration Setup**

### **1. Perfecto Configuration**

Edit `src/test/resources/config/perfecto.properties`:

```properties
# Your Perfecto Cloud Details
perfecto.cloud.name=your-cloud-name
perfecto.security.token=your-security-token-here

# Android App Configuration
perfecto.android.device.model=Galaxy S.*|Pixel.*
perfecto.android.os.version=13
perfecto.android.app.path=PRIVATE:apps/app-V1.14.10-QA.apk
perfecto.android.app.package=com.yourcompany.qaapp

# iOS App Configuration  
perfecto.ios.device.model=iPhone.*
perfecto.ios.os.version=16
perfecto.ios.app.path=PRIVATE:apps/eStratis.ipa
perfecto.ios.bundle.id=com.yourcompany.qaapp
```

### **2. Upload Your Apps to Perfecto**

1. **Login to Perfecto Cloud**
2. **Go to Repository → Upload**
3. **Upload your apps:**
   - `app-V1.14.10-QA.apk` (Android)
   - `eStratis.ipa` (iOS)
4. **Note the paths** (usually `PRIVATE:apps/filename`)

---

## 📱 **Available Test Runners**

### **1. UnifiedTestRunner** (Recommended)
- **Most flexible** - supports all platforms and execution types
- **Usage**: Pass platform and execution type as parameters
- **Example**: `mvn test -Dtest=UnifiedTestRunner -Dplatform=ios -Dexecution.type=perfecto`

### **2. PerfectoAndroidTestRunner**
- **Dedicated** Android Perfecto runner
- **Pre-configured** for Android on Perfecto
- **Usage**: `mvn test -Dtest=PerfectoAndroidTestRunner`

### **3. PerfectoiOSTestRunner**
- **Dedicated** iOS Perfecto runner
- **Pre-configured** for iOS on Perfecto
- **Usage**: `mvn test -Dtest=PerfectoiOSTestRunner`

### **4. QAAppTestRunner** (Original)
- **Android emulator** only
- **Legacy runner** for backward compatibility
- **Usage**: `mvn test -Dtest=QAAppTestRunner`

---

## 🎭 **Test Scenarios Available**

All scenarios from `QAAppLogin.feature` work on both platforms:

### **Smoke Tests** (@smoke)
- ✅ Successful login with valid credentials
- ✅ Failed login with invalid username
- ✅ Failed login with invalid password

### **Regression Tests** (@regression)
- ✅ Empty username validation
- ✅ Empty password validation
- ✅ Multiple login attempts
- ✅ Login form field validation
- ✅ Forgot password functionality

### **UI Tests** (@ui)
- ✅ Login with different credential combinations
- ✅ Accessibility validation

### **Performance Tests** (@performance)
- ✅ Login page load time validation

---

## 🏷️ **Tag-Based Execution**

Run specific test categories using Cucumber tags:

```bash
# Smoke tests only
mvn test -Dtest=UnifiedTestRunner -Dplatform=android -Dexecution.type=perfecto -Dcucumber.filter.tags="@smoke"

# Regression tests only
mvn test -Dtest=UnifiedTestRunner -Dplatform=ios -Dexecution.type=perfecto -Dcucumber.filter.tags="@regression"

# High priority tests only
mvn test -Dtest=UnifiedTestRunner -Dplatform=android -Dexecution.type=perfecto -Dcucumber.filter.tags="@high-priority"

# Negative test cases
mvn test -Dtest=UnifiedTestRunner -Dplatform=ios -Dexecution.type=perfecto -Dcucumber.filter.tags="@negative"

# Login functionality only
mvn test -Dtest=UnifiedTestRunner -Dplatform=android -Dexecution.type=perfecto -Dcucumber.filter.tags="@login"
```

---

## 🔧 **Cross-Platform Element Handling**

The `QAAppLoginPage` now supports both platforms with dual locators:

### **Android Locators:**
```java
@AndroidFindBy(id = "com.stratis.estaffing:id/userText")
private WebElement usernameField;
```

### **iOS Locators:**
```java
@iOSXCUITFindBy(accessibility = "usernameField")
private WebElement usernameField;
```

### **Fallback Strategy:**
- Primary locators tried first
- Alternative locators used if primary fails
- Graceful error handling with detailed logging

---

## 📊 **Reports and Results**

### **Generated Reports:**
- **Extent Reports**: `test-output/extent-reports/`
- **Cucumber HTML**: `test-output/cucumber-reports/`
- **JSON Reports**: `test-output/cucumber-reports/*.json`
- **Screenshots**: Automatically captured on failures

### **Platform-Specific Reports:**
- **Android Perfecto**: `test-output/cucumber-reports/perfecto-android/`
- **iOS Perfecto**: `test-output/cucumber-reports/perfecto-ios/`
- **Unified**: `test-output/cucumber-reports/unified/`

---

## 🚨 **Troubleshooting**

### **Common Issues:**

#### **1. Perfecto Authentication Failed**
```bash
# Solution: Update credentials in perfecto.properties
perfecto.cloud.name=your-actual-cloud-name
perfecto.security.token=your-actual-token
```

#### **2. App Not Found on Perfecto**
```bash
# Solution: Verify app paths in perfecto.properties
perfecto.android.app.path=PRIVATE:apps/app-V1.14.10-QA.apk
perfecto.ios.app.path=PRIVATE:apps/eStratis.ipa
```

#### **3. Element Not Found**
```bash
# Solution: Update locators in QAAppLoginPage.java
# Use Perfecto Object Spy to find correct element identifiers
```

#### **4. iOS Locators Not Working**
```bash
# Solution: Update iOS accessibility IDs in QAAppLoginPage.java
@iOSXCUITFindBy(accessibility = "your-actual-accessibility-id")
```

---

## 💡 **Best Practices**

### **1. Platform Selection Strategy**
- **Development/Debug**: Use local emulator
- **Cross-platform validation**: Use Perfecto
- **CI/CD Pipeline**: Use BrowserStack or Perfecto
- **Performance testing**: Use real devices on Perfecto

### **2. Test Organization**
- **@smoke**: Quick validation tests
- **@regression**: Comprehensive test suite
- **@high-priority**: Critical functionality
- **@negative**: Error handling validation

### **3. Execution Strategy**
```bash
# Quick feedback during development
mvn test -Dtest=UnifiedTestRunner -Dplatform=android -Dexecution.type=emulator -Dcucumber.filter.tags="@smoke"

# Full cross-platform validation
mvn test -Dtest=UnifiedTestRunner -Dplatform=android -Dexecution.type=perfecto -Dcucumber.filter.tags="@regression"
mvn test -Dtest=UnifiedTestRunner -Dplatform=ios -Dexecution.type=perfecto -Dcucumber.filter.tags="@regression"
```

---

## 🎉 **Summary**

You now have a **unified BDD testing framework** that supports:

✅ **Same test scenarios** run on both Android and iOS  
✅ **Multiple execution environments** (Emulator, Perfecto, BrowserStack)  
✅ **Flexible test runners** for different needs  
✅ **Cross-platform element handling** with fallback strategies  
✅ **Tag-based test execution** for targeted testing  
✅ **Comprehensive reporting** with screenshots  
✅ **Easy configuration** via properties files  

**Your eStratis app can now be tested consistently across both platforms using the same BDD scenarios!** 🚀
