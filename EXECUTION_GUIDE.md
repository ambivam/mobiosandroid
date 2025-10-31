# Test Execution Guide

This guide provides detailed instructions for running tests with the Mobile Automation Framework.

## Quick Start

### 1. Basic Test Execution
```bash
# Run all smoke tests on Android locally
mvn clean test -Dplatform=android -Dexecution.type=local -Dcucumber.filter.tags="@smoke"

# Run all tests on iOS with BrowserStack
mvn clean test -Dplatform=ios -Dexecution.type=browserstack

# Run regression tests
mvn clean test -Dcucumber.filter.tags="@regression"
```

### 2. Using Batch Scripts (Windows)
```cmd
# Run smoke tests on Android locally
run-tests.bat android local @smoke

# Run all tests on iOS with BrowserStack
run-tests.bat ios browserstack

# Run regression tests with default settings
run-tests.bat android local @regression
```

### 3. Using Shell Scripts (Linux/Mac)
```bash
# Make script executable
chmod +x run-tests.sh

# Run smoke tests on Android locally
./run-tests.sh android local @smoke

# Run all tests on iOS with BrowserStack
./run-tests.sh ios browserstack

# Run regression tests
./run-tests.sh android local @regression
```

## Configuration Parameters

### Platform Options
- `android` - Run tests on Android devices
- `ios` - Run tests on iOS devices

### Execution Type Options
- `local` - Run on local Appium server
- `browserstack` - Run on BrowserStack cloud

### Tag Options
- `@smoke` - Critical functionality tests
- `@regression` - Full regression suite
- `@positive` - Happy path scenarios
- `@negative` - Error handling scenarios

## Advanced Execution

### 1. Parallel Execution
```xml
<!-- In testng.xml -->
<suite name="Parallel Tests" parallel="tests" thread-count="2">
```

```bash
mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml
```

### 2. Custom Test Suites
Create custom TestNG XML files for different test suites:

```xml
<!-- smoke-suite.xml -->
<suite name="Smoke Test Suite">
    <parameter name="tags" value="@smoke"/>
    <test name="Smoke Tests">
        <classes>
            <class name="com.automation.runners.TestRunner"/>
        </classes>
    </test>
</suite>
```

Run with:
```bash
mvn clean test -DsuiteXmlFile=smoke-suite.xml
```

### 3. Environment-Specific Execution
```bash
# Development environment
mvn clean test -Denv=dev -Dplatform=android

# Staging environment
mvn clean test -Denv=staging -Dplatform=ios

# Production environment
mvn clean test -Denv=prod -Dexecution.type=browserstack
```

## BrowserStack Setup

### 1. Account Configuration
1. Sign up for BrowserStack account
2. Get username and access key from Account Settings
3. Update `src/test/resources/config/browserstack.properties`:
```properties
browserstack.username=your_username
browserstack.access.key=your_access_key
```

### 2. App Upload
```bash
# Upload Android app
curl -u "username:access_key" -X POST "https://api-cloud.browserstack.com/app-automate/upload" -F "file=@/path/to/app.apk"

# Upload iOS app
curl -u "username:access_key" -X POST "https://api-cloud.browserstack.com/app-automate/upload" -F "file=@/path/to/app.ipa"
```

Update app URLs in browserstack.properties:
```properties
browserstack.android.app.url=bs://your-android-app-id
browserstack.ios.app.url=bs://your-ios-app-id
```

## Local Appium Setup

### 1. Install Appium
```bash
npm install -g appium
npm install -g appium-doctor

# Verify installation
appium-doctor --android
appium-doctor --ios
```

### 2. Start Appium Server
```bash
# Default port
appium

# Custom port
appium --port 4724

# With logging
appium --log-level debug --log appium.log
```

### 3. Device Setup

#### Android:
1. Enable Developer Options
2. Enable USB Debugging
3. Connect device via USB
4. Verify: `adb devices`

#### iOS:
1. Enable Developer Mode
2. Trust development certificates
3. Connect device via USB
4. Verify: `idevice_id -l`

## Debugging Tests

### 1. Enable Debug Logging
Update `src/test/resources/log4j2.xml`:
```xml
<Logger name="com.automation" level="DEBUG" additivity="false">
```

### 2. Appium Inspector
```bash
# Start Appium with inspector
appium --allow-cors
```
Open Appium Inspector and connect to inspect elements.

### 3. Screenshot on Failure
Screenshots are automatically captured on test failures:
- Location: `test-output/screenshots/`
- Attached to Extent Reports

### 4. Video Recording (BrowserStack)
Enable in browserstack.properties:
```properties
browserstack.video=true
browserstack.debug=true
```

## Report Analysis

### 1. Extent Reports
- **Location**: `test-output/extent-reports/`
- **Features**: 
  - Test execution timeline
  - Screenshots on failure
  - System information
  - Test statistics

### 2. Cucumber Reports
- **HTML**: `test-output/cucumber-reports/cucumber-html-reports/`
- **JSON**: `test-output/cucumber-reports/Cucumber.json`
- **XML**: `test-output/cucumber-reports/Cucumber.xml`

### 3. Logs
- **Application Logs**: `test-output/logs/automation.log`
- **Rolling Logs**: `test-output/logs/automation-rolling.log`

## CI/CD Integration

### 1. Jenkins Pipeline
```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test -Dplatform=android -Dexecution.type=browserstack'
            }
        }
        stage('Reports') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'test-output/extent-reports',
                    reportFiles: '*.html',
                    reportName: 'Extent Report'
                ])
            }
        }
    }
}
```

### 2. GitHub Actions
```yaml
name: Mobile Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 11
        uses: actions/setup-java@v2
        with:
          java-version: '11'
          distribution: 'adopt'
      - name: Run tests
        run: mvn clean test -Dplatform=android -Dexecution.type=browserstack
      - name: Upload reports
        uses: actions/upload-artifact@v2
        with:
          name: test-reports
          path: test-output/
```

## Troubleshooting

### Common Issues

1. **Appium Server Not Running**
   ```
   Error: Could not start a new session
   Solution: Start Appium server with 'appium' command
   ```

2. **Element Not Found**
   ```
   Error: NoSuchElementException
   Solution: Verify element locators using Appium Inspector
   ```

3. **BrowserStack Authentication**
   ```
   Error: Invalid username or access key
   Solution: Check credentials in browserstack.properties
   ```

4. **App Not Found**
   ```
   Error: App file not found
   Solution: Verify app path in configuration files
   ```

### Debug Commands
```bash
# Check Appium server status
curl http://localhost:4723/wd/hub/status

# List connected Android devices
adb devices

# List connected iOS devices (Mac only)
idevice_id -l

# Check BrowserStack app uploads
curl -u "username:access_key" -X GET "https://api-cloud.browserstack.com/app-automate/recent_apps"
```

## Performance Tips

1. **Use Explicit Waits**: Avoid Thread.sleep()
2. **Optimize Locators**: Use ID > Name > XPath
3. **Parallel Execution**: Run tests in parallel when possible
4. **Resource Cleanup**: Always quit drivers in teardown
5. **App State**: Reset app state between tests if needed

## Best Practices

1. **Test Independence**: Each test should be independent
2. **Data Management**: Use external test data files
3. **Page Objects**: Keep page logic separate from test logic
4. **Error Handling**: Implement proper exception handling
5. **Reporting**: Use meaningful test names and descriptions
