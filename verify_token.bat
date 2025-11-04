@echo off
echo Verifying Perfecto Token and Connection...
echo.
echo Testing with minimal capabilities...
echo URL: https://trial.perfectomobile.com/nexperience/perfectomobile/wd/hub
echo.
mvn test -Dtest=PerfectoAndroidTestRunner -q
echo.
echo If you see 401 errors, please:
echo 1. Check if your Perfecto trial is still active
echo 2. Generate a fresh security token
echo 3. Verify app access permissions in Perfecto dashboard
pause
