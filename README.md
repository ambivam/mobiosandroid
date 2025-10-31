# Mobile Automation Framework

A comprehensive Appium-based mobile test automation framework supporting both Android and iOS platforms with BrowserStack integration, Cucumber BDD, Extent Reports, and Page Object Model.

## Features

- ✅ **Cross-Platform Support**: Android and iOS
- ✅ **BrowserStack Integration**: Cloud-based testing
- ✅ **Cucumber BDD**: Behavior-driven development
- ✅ **Extent Reports**: Rich HTML reporting
- ✅ **Page Object Model**: Maintainable test structure
- ✅ **TestNG Integration**: Powerful test execution
- ✅ **Logging**: Comprehensive logging with Log4j2
- ✅ **Screenshot Capture**: Automatic screenshots on failure
- ✅ **Configuration Management**: Environment-specific configurations

## Project Structure

```
mobile-automation-framework/
├── src/
│   ├── main/java/com/automation/
│   │   ├── config/
│   │   │   └── ConfigManager.java
│   │   ├── driver/
│   │   │   └── DriverFactory.java
│   │   ├── pages/
│   │   │   ├── BasePage.java
│   │   │   └── LoginPage.java
│   │   └── utils/
│   │       ├── CommonUtils.java
│   │       └── ExtentReportManager.java
│   └── test/
│       ├── java/com/automation/
│       │   ├── hooks/
│       │   │   └── Hooks.java
│       │   ├── listeners/
│       │   │   └── TestListener.java
│       │   ├── runners/
│       │   │   └── TestRunner.java
│       │   └── steps/
│       │       └── LoginSteps.java
│       └── resources/
│           ├── config/
│           │   ├── android.properties
│           │   ├── ios.properties
│           │   └── browserstack.properties
│           ├── features/
│           │   └── Login.feature
│           ├── extent.properties
│           ├── extent-config.xml
│           ├── log4j2.xml
│           └── testng.xml
├── test-output/
├── pom.xml
└── README.md
```

## Prerequisites

1. **Java 11** or higher
2. **Maven 3.6+**
3. **Appium Server** (for local execution)
4. **Android SDK** (for Android testing)
5. **Xcode** (for iOS testing on macOS)
6. **BrowserStack Account** (for cloud testing)

## Setup Instructions

### 1. Clone the Repository
```bash
git clone <repository-url>
cd mobile-automation-framework
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Configure Test Environment

#### For Local Testing:
- Update `src/test/resources/config/android.properties` with your Android app details
- Update `src/test/resources/config/ios.properties` with your iOS app details
- Place your APK/IPA files in `src/test/resources/apps/` directory

#### For BrowserStack Testing:
- Update `src/test/resources/config/browserstack.properties` with your BrowserStack credentials
- Upload your apps to BrowserStack and update the app URLs

### 4. Start Appium Server (for local testing)
```bash
appium --port 4723
```

## Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run with Specific Platform
```bash
# Android Local
mvn clean test -Dplatform=android -Dexecution.type=local

# iOS Local
mvn clean test -Dplatform=ios -Dexecution.type=local

# BrowserStack
mvn clean test -Dplatform=android -Dexecution.type=browserstack
```

### Run Specific Test Tags
```bash
# Smoke tests only
mvn clean test -Dcucumber.filter.tags="@smoke"

# Regression tests only
mvn clean test -Dcucumber.filter.tags="@regression"

# Positive tests only
mvn clean test -Dcucumber.filter.tags="@positive"
```

### Run with TestNG XML
```bash
mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml
```

## Configuration

### Android Configuration (`android.properties`)
```properties
platform.name=Android
platform.version=13.0
device.name=Pixel 7
automation.name=UiAutomator2
app.package=com.example.app
app.activity=.MainActivity
app.path=src/test/resources/apps/android-app.apk
```

### iOS Configuration (`ios.properties`)
```properties
platform.name=iOS
platform.version=16.0
device.name=iPhone 14
automation.name=XCUITest
bundle.id=com.example.app
app.path=src/test/resources/apps/ios-app.ipa
```

### BrowserStack Configuration (`browserstack.properties`)
```properties
browserstack.username=YOUR_USERNAME
browserstack.access.key=YOUR_ACCESS_KEY
browserstack.url=https://hub-cloud.browserstack.com/wd/hub
browserstack.android.device=Google Pixel 7
browserstack.ios.device=iPhone 14
```

## Writing Tests

### 1. Create Page Objects
Extend `BasePage` class and use `@AndroidFindBy` and `@iOSXCUITFindBy` annotations:

```java
public class LoginPage extends BasePage {
    @AndroidFindBy(id = "com.example.app:id/username")
    @iOSXCUITFindBy(id = "username")
    private WebElement usernameField;
    
    public void enterUsername(String username) {
        sendKeys(usernameField, username);
    }
}
```

### 2. Create Step Definitions
```java
@Given("user is on the login screen")
public void userIsOnTheLoginScreen() {
    Assert.assertTrue(loginPage.isLoginButtonDisplayed());
}
```

### 3. Create Feature Files
```gherkin
@smoke
Feature: User Login
  Scenario: Successful login
    Given user is on the login screen
    When user logs in with username "testuser" and password "testpass"
    Then login should be successful
```

## Reports

### Extent Reports
- Location: `test-output/extent-reports/`
- Rich HTML reports with screenshots
- Test execution timeline
- System information

### Cucumber Reports
- Location: `test-output/cucumber-reports/`
- HTML, JSON, and XML formats

### Logs
- Location: `test-output/logs/`
- Detailed execution logs
- Rolling file appender

## Best Practices

1. **Page Object Model**: Keep page elements and actions separate
2. **Wait Strategies**: Use explicit waits instead of Thread.sleep()
3. **Test Data**: Externalize test data using examples in feature files
4. **Error Handling**: Implement proper exception handling
5. **Screenshots**: Automatic screenshots on test failures
6. **Logging**: Use appropriate log levels for debugging

## Troubleshooting

### Common Issues

1. **Driver Not Found**: Ensure Appium server is running for local tests
2. **Element Not Found**: Verify element locators for your app
3. **BrowserStack Connection**: Check credentials and network connectivity
4. **Build Failures**: Ensure all dependencies are properly installed

### Debug Mode
Enable debug logging by updating `log4j2.xml`:
```xml
<Logger name="com.automation" level="DEBUG" additivity="false">
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new features
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For questions and support:
- Create an issue in the repository
- Check the troubleshooting section
- Review the logs in `test-output/logs/`
