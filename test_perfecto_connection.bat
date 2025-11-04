@echo off
echo Testing Perfecto Connection...
echo.
echo Current Configuration:
echo Cloud: trial-perfectomobile-com.perfectomobile.com
echo App: Private:app-V1.14.10-QA.apk
echo Package: com.stratis.estaffing
echo.
mvn test -Dtest=PerfectoAndroidTestRunner
pause
