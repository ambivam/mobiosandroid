@echo off
echo ========================================
echo Unified Perfecto Mobile Automation
echo ========================================

echo.
echo Building project and downloading dependencies...
call mvn clean compile

if %ERRORLEVEL% neq 0 (
    echo ERROR: Build failed. Please check your Maven configuration.
    pause
    exit /b 1
)

echo.
echo Choose platform to test:
echo 1. Android
echo 2. iOS
echo 3. Both (sequential)
echo.
set /p choice="Enter your choice (1-3): "

if "%choice%"=="1" (
    echo.
    echo Running Android tests...
    call mvn test -Dtest=UnifiedPerfectoSample -Dplatform=Android
) else if "%choice%"=="2" (
    echo.
    echo Running iOS tests...
    call mvn test -Dtest=UnifiedPerfectoSample -Dplatform=iOS
) else if "%choice%"=="3" (
    echo.
    echo Running Android tests first...
    call mvn test -Dtest=UnifiedPerfectoSample -Dplatform=Android
    echo.
    echo Running iOS tests...
    call mvn test -Dtest=UnifiedPerfectoSample -Dplatform=iOS
) else (
    echo Invalid choice. Please run the script again.
)

echo.
echo Test execution completed.
echo Check the console output above for report URLs.
pause
