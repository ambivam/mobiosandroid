@echo off
echo ===============================================
echo QA App v1.14.10 Test Execution
echo ===============================================

set PLATFORM=%1
set EXECUTION_TYPE=%2
set TAGS=%3

if "%PLATFORM%"=="" (
    set PLATFORM=android
)

if "%EXECUTION_TYPE%"=="" (
    set EXECUTION_TYPE=local
)

if "%TAGS%"=="" (
    set TAGS="@qa-app and @smoke"
)

echo Platform: %PLATFORM%
echo Execution Type: %EXECUTION_TYPE%
echo Tags: %TAGS%
echo.

echo ===============================================
echo Available Execution Types:
echo - local      : Run on connected physical device
echo - emulator   : Run on Android emulator
echo - browserstack : Run on BrowserStack cloud
echo ===============================================
echo.

echo Cleaning previous test results...
if exist test-output rmdir /s /q test-output
mkdir test-output\logs
mkdir test-output\screenshots
mkdir test-output\extent-reports
mkdir test-output\cucumber-reports

echo.
echo STEP 1: Analyzing QA App (Optional - run first time only)
echo To analyze your app structure, run:
echo mvn test -Dtest=QAAppAnalysisTest -Dplatform=%PLATFORM%
echo.

echo STEP 2: Running QA App Login Tests
mvn clean test -Dtest=QAAppTestRunner -Dplatform=%PLATFORM% -Dexecution.type=%EXECUTION_TYPE% -Dcucumber.filter.tags=%TAGS%

echo.
echo Test execution completed!
echo Check reports at:
echo - Extent Reports: test-output\extent-reports\
echo - Cucumber Reports: test-output\cucumber-reports\qa-app\
echo - Screenshots: test-output\screenshots\

pause
