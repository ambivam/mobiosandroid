@echo off
echo ===============================================
echo Mobile Automation Framework Test Execution
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
    set TAGS=@smoke
)

echo Platform: %PLATFORM%
echo Execution Type: %EXECUTION_TYPE%
echo Tags: %TAGS%
echo.

echo Cleaning previous test results...
if exist test-output rmdir /s /q test-output
mkdir test-output\logs
mkdir test-output\screenshots
mkdir test-output\extent-reports
mkdir test-output\cucumber-reports

echo Starting test execution...
mvn clean test -Dplatform=%PLATFORM% -Dexecution.type=%EXECUTION_TYPE% -Dcucumber.filter.tags="%TAGS%"

echo.
echo Test execution completed!
echo Check reports at:
echo - Extent Reports: test-output\extent-reports\
echo - Cucumber Reports: test-output\cucumber-reports\
echo - Logs: test-output\logs\

pause
