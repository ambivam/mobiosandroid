# Simple Mobile Automation Framework

A straightforward Appium mobile test automation framework designed for test automation engineers with 5+ years of experience. This framework focuses on simplicity and ease of understanding.

## 🚀 Quick Start

### Prerequisites
- Java 11+
- Maven 3.6+
- Appium Server (for local testing)
- BrowserStack account (for cloud testing)

### Run Tests
```bash
# Run tests locally on Android
mvn clean test -Dplatform=android -Dexecution.type=local

# Run tests on BrowserStack
mvn clean test -Dplatform=android -Dexecution.type=browserstack

# Run specific tags
mvn clean test -Dcucumber.filter.tags="@smoke"
```

## 📁 Framework Structure

```
src/
├── main/java/com/automation/
│   ├── config/ConfigManager.java          # Simple properties file loader
│   ├── driver/DriverFactory.java          # Driver creation (Android/iOS/BrowserStack)
│   ├── pages/BasePage.java                # Common page actions
│   └── utils/SimpleExtentReportManager.java # Basic reporting
└── test/
    ├── java/com/automation/
    │   ├── hooks/Hooks.java                # Test setup/teardown
    │   ├── steps/SimpleLoginSteps.java     # Cucumber step definitions
    │   └── runners/TestRunner.java         # Test execution
    └── resources/
        ├── config/                         # Platform configurations
        ├── features/Login.feature          # BDD test scenarios
        └── testng.xml                      # TestNG configuration
```

## 🔧 Key Components

### 1. ConfigManager
Simple configuration loader - no singleton pattern:
```java
ConfigManager config = new ConfigManager();
config.loadConfig("android");
String deviceName = config.getProperty("device.name");
```

### 2. DriverFactory
Straightforward driver creation:
```java
// Create driver
DriverFactory.createDriver("android", "local");

// Get driver
AppiumDriver driver = DriverFactory.getDriver();

// Quit driver
DriverFactory.quitDriver();
```

### 3. BasePage
Basic page object with common actions:
```java
public class LoginPage extends BasePage {
    @AndroidFindBy(id = "username")
    private WebElement usernameField;
    
    public void enterUsername(String username) {
        sendKeys(usernameField, username);
    }
}
```

### 4. Simple Reporting
Easy-to-use extent reporting:
```java
SimpleExtentReportManager.createTest("Login Test");
SimpleExtentReportManager.logPass("Test passed");
SimpleExtentReportManager.captureScreenshot("test_name");
```

## ⚙️ Configuration Files

### Android (`android.properties`)
```properties
platform.name=Android
platform.version=13.0
device.name=Pixel 7
automation.name=UiAutomator2
app.package=com.example.app
app.activity=.MainActivity
appium.server.url=http://localhost:4723
implicit.wait=10
explicit.wait=20
```

### BrowserStack (`browserstack.properties`)
```properties
browserstack.username=YOUR_USERNAME
browserstack.access.key=YOUR_ACCESS_KEY
browserstack.url=https://hub-cloud.browserstack.com/wd/hub
browserstack.android.device=Google Pixel 7
browserstack.android.os.version=13.0
browserstack.android.app.url=bs://your-app-id
```

## 📝 Writing Tests

### 1. Create Page Objects
```java
public class LoginPage extends BasePage {
    @AndroidFindBy(id = "username")
    @iOSXCUITFindBy(id = "username")
    private WebElement usernameField;
    
    public void enterUsername(String username) {
        sendKeys(usernameField, username);
    }
    
    public boolean isLoginButtonDisplayed() {
        return isElementDisplayed(loginButton);
    }
}
```

### 2. Write Step Definitions
```java
@Given("user is on the login screen")
public void userIsOnTheLoginScreen() {
    System.out.println("User is on the login screen");
    Assert.assertTrue(loginPage.isLoginButtonDisplayed());
}

@When("user enters username {string}")
public void userEntersUsername(String username) {
    loginPage.enterUsername(username);
}
```

### 3. Create Feature Files
```gherkin
@smoke
Feature: Login Functionality
  Scenario: Successful login
    Given user is on the login screen
    When user logs in with username "testuser" and password "testpass"
    Then login should be successful
```

## 🎯 Key Simplifications

### What We Removed:
- ❌ Complex singleton patterns
- ❌ ThreadLocal variables  
- ❌ Excessive logging frameworks
- ❌ Complex error handling
- ❌ Abstract classes where not needed
- ❌ Over-engineered design patterns

### What We Kept:
- ✅ Page Object Model
- ✅ Cucumber BDD
- ✅ Cross-platform support
- ✅ BrowserStack integration
- ✅ Extent Reports
- ✅ Screenshot on failure
- ✅ Configuration management

## 🏃‍♂️ Running Tests

### Local Execution
1. Start Appium server: `appium`
2. Connect your device
3. Run tests: `mvn clean test -Dplatform=android`

### BrowserStack Execution
1. Update credentials in `browserstack.properties`
2. Upload your app to BrowserStack
3. Run tests: `mvn clean test -Dexecution.type=browserstack`

### Using Scripts
```bash
# Windows
run-tests.bat android local @smoke

# Linux/Mac
./run-tests.sh android local @smoke
```

## 📊 Reports

- **Extent Reports**: `test-output/extent-reports/`
- **Screenshots**: `test-output/screenshots/`
- **Logs**: Console output with System.out.println

## 🔍 Debugging

### Common Issues:
1. **Driver not found**: Check if Appium server is running
2. **Element not found**: Verify locators in your app
3. **BrowserStack issues**: Check credentials and app URL

### Debug Tips:
- Use `System.out.println()` for debugging
- Check console output for error messages
- Verify configuration files are correct
- Use Appium Inspector to find elements

## 🎓 For 5+ Years Experience Developers

This framework is designed to be:
- **Easy to understand** - No complex patterns
- **Quick to modify** - Simple, straightforward code
- **Easy to debug** - Clear error messages and logging
- **Maintainable** - Clean separation of concerns
- **Extensible** - Add new features easily

The code is intentionally simple and follows basic Java principles that any experienced automation engineer can quickly understand and modify.

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch
3. Make changes (keep it simple!)
4. Test your changes
5. Submit a pull request

Remember: **Simplicity is the ultimate sophistication!**
