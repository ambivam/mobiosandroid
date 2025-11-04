@echo off
echo Testing Perfecto Connection with Fixed URL...
echo Expected URL: https://trial.perfectomobile.com/nexperience/perfectomobile/wd/hub
echo.
mvn test -Dtest=UnifiedTestRunner -Dplatform=android -Dexecution.type=perfecto -Dcucumber.filter.tags="@smoke" -q
pause
