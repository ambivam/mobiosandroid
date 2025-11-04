@echo off
echo Running Perfecto Android Test...
mvn clean test -Dtest=UnifiedTestRunner -Dplatform=android -Dexecution.type=perfecto
pause
