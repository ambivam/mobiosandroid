@echo off
echo ===============================================
echo Enhanced Mobile Test Execution Script
echo QA App v1.14.10 - Multiple Execution Options
echo ===============================================

:: Check if help is requested
if "%1"=="help" goto :help
if "%1"=="--help" goto :help
if "%1"=="-h" goto :help

set PLATFORM=%1
set EXECUTION_TYPE=%2
set TAGS=%3

:: Set defaults
if "%PLATFORM%"=="" (
    set PLATFORM=android
)

if "%EXECUTION_TYPE%"=="" (
    set EXECUTION_TYPE=local
)

if "%TAGS%"=="" (
    set TAGS="@qa-app and @smoke"
)

echo Current Configuration:
echo - Platform: %PLATFORM%
echo - Execution Type: %EXECUTION_TYPE%
echo - Tags: %TAGS%
echo.

:: Validate execution type
if "%EXECUTION_TYPE%"=="local" goto :valid_execution
if "%EXECUTION_TYPE%"=="emulator" goto :valid_execution
if "%EXECUTION_TYPE%"=="browserstack" goto :valid_execution

echo ERROR: Invalid execution type '%EXECUTION_TYPE%'
echo Valid options: local, emulator, browserstack
goto :help

:valid_execution

:: Show execution type specific information
if "%EXECUTION_TYPE%"=="local" (
    echo ===============================================
    echo RUNNING ON PHYSICAL DEVICE
    echo Prerequisites:
    echo - Connect Android device via USB
    echo - Enable USB Debugging
    echo - Verify: adb devices
    echo ===============================================
)

if "%EXECUTION_TYPE%"=="emulator" (
    echo ===============================================
    echo RUNNING ON ANDROID EMULATOR
    echo Prerequisites:
    echo - Android Studio installed
    echo - AVD created: Pixel_7_API_33
    echo - Emulator should be running or will auto-start
    echo - Verify: emulator -list-avds
    echo ===============================================
)

if "%EXECUTION_TYPE%"=="browserstack" (
    echo ===============================================
    echo RUNNING ON BROWSERSTACK CLOUD
    echo Prerequisites:
    echo - Update browserstack.properties with credentials
    echo - Upload APK to BrowserStack
    echo - Update app URL in browserstack.properties
    echo ===============================================
)

echo.
echo Cleaning previous test results...
if exist test-output rmdir /s /q test-output
mkdir test-output\logs 2>nul
mkdir test-output\screenshots 2>nul
mkdir test-output\extent-reports 2>nul
mkdir test-output\cucumber-reports 2>nul

echo.
echo Starting test execution...
echo Command: mvn clean test -Dtest=QAAppTestRunner -Dplatform=%PLATFORM% -Dexecution.type=%EXECUTION_TYPE% -Dcucumber.filter.tags=%TAGS%
echo.

mvn clean test -Dtest=QAAppTestRunner -Dplatform=%PLATFORM% -Dexecution.type=%EXECUTION_TYPE% -Dcucumber.filter.tags=%TAGS%

echo.
echo ===============================================
echo Test execution completed!
echo.
echo Reports available at:
echo - Extent Reports: test-output\extent-reports\
echo - Cucumber Reports: test-output\cucumber-reports\qa-app\
echo - Screenshots: test-output\screenshots\
echo ===============================================

goto :end

:help
echo.
echo ===============================================
echo USAGE: run-tests-enhanced.bat [platform] [execution_type] [tags]
echo ===============================================
echo.
echo PARAMETERS:
echo   platform        : android ^| ios (default: android)
echo   execution_type  : local ^| emulator ^| browserstack (default: local)
echo   tags           : Cucumber tags (default: "@qa-app and @smoke")
echo.
echo EXECUTION TYPES:
echo   local          : Run on connected physical device
echo                   - Requires USB connected Android device
echo                   - USB debugging enabled
echo.
echo   emulator       : Run on Android emulator
echo                   - Requires Android Studio with AVD
echo                   - AVD name: Pixel_7_API_33
echo.
echo   browserstack   : Run on BrowserStack cloud
echo                   - Requires BrowserStack account
echo                   - Update credentials in browserstack.properties
echo.
echo EXAMPLES:
echo   run-tests-enhanced.bat
echo   run-tests-enhanced.bat android local
echo   run-tests-enhanced.bat android emulator "@smoke"
echo   run-tests-enhanced.bat android browserstack "@regression"
echo.
echo ANALYSIS (First time setup):
echo   mvn test -Dtest=QAAppAnalysisTest -Dplatform=android
echo.
echo PREREQUISITES CHECK:
echo   Physical Device : adb devices
echo   Emulator       : emulator -list-avds
echo   BrowserStack   : Check browserstack.properties
echo ===============================================

:end
pause
