@echo off
echo Running modern Perfecto test...
mvn clean test -Dtest=UnifiedTestRunner -Dplatform=android -Dexecution.type=perfecto
pause
