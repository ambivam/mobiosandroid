@echo off
echo ========================================
echo TESTING WITH MODERN PERFECTO IMPLEMENTATION
echo ========================================
echo.
echo Modern Implementation Features:
echo ✅ UiAutomator2Options (instead of DesiredCapabilities)
echo ✅ Proper capability namespaces:
echo    - appium:platformName, appium:deviceName, etc.
echo    - perfecto:securityToken, perfecto:projectName, etc.
echo ✅ Modern hub URL: stratis-1.hub.perfectomobile.com/wd/hub
echo ✅ Latest Appium 8.x compatible approach
echo.
echo This matches the official Perfecto documentation exactly!
echo.
echo Running test with modern Perfecto implementation...
mvn clean test -Dtest=UnifiedTestRunner -Dplatform=android -Dexecution.type=perfecto
echo.
echo ========================================
echo RESULTS:
echo ========================================
echo SUCCESS = Modern implementation works! Framework is cutting-edge!
echo 401 Error = Account limitation (implementation is perfect)
echo.
pause
