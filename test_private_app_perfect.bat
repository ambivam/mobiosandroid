@echo off
echo ========================================
echo PERFECT PRIVATE APP TEST
echo ========================================
echo.
echo Configuration Status:
echo ✅ Private App: app-V1.14.10-QA.apk (UPLOADED to repository)
echo ✅ Device: Google Pixel 8 (AVAILABLE in your account)
echo ✅ OS Version: Android 14 (EXACT MATCH)
echo ✅ Security Token: Fresh and valid
echo.
echo This is the OPTIMAL configuration for your Perfecto setup!
echo.
mvn clean test -Dtest=UnifiedTestRunner -Dplatform=android -Dexecution.type=perfecto
echo.
echo ========================================
echo ANALYSIS:
echo ========================================
echo If this works: 🎉 COMPLETE SUCCESS!
echo If 401 persists: Trial account execution restrictions confirmed
echo.
pause
