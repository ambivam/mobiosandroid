@echo off
echo Running Perfecto Android Test with Debug Info...
mvn clean test -Dtest=UnifiedTestRunner -Dplatform=android -Dexecution.type=perfecto -X
pause
