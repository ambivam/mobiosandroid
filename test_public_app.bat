@echo off
echo Testing with Public Demo App to isolate authentication issue...
echo.
echo Temporarily switching to public app...

REM Backup current config
copy src\test\resources\config\perfecto.properties src\test\resources\config\perfecto.properties.backup

REM Create temp config with public app
echo # Perfecto Configuration - Public App Test > temp_perfecto.properties
echo perfecto.cloud.name=trial >> temp_perfecto.properties
echo perfecto.security.token=eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI2ZDM2NmJiNS01NDAyLTQ4MmMtYTVhOC1kODZhODk4MDYyZjIifQ.eyJpYXQiOjE3NjIyNjc3MTQsImp0aSI6Ijc1MjQ4YTcwLTdmYWQtNDM0NS1iODMxLTRmMmMwMDJjNmE1NCIsImlzcyI6Imh0dHBzOi8vYXV0aDMucGVyZmVjdG9tb2JpbGUuY29tL2F1dGgvcmVhbG1zL3RyaWFsLXBlcmZlY3RvbW9iaWxlLWNvbSIsImF1ZCI6Imh0dHBzOi8vYXV0aDMucGVyZmVjdG9tb2JpbGUuY29tL2F1dGgvcmVhbG1zL3RyaWFsLXBlcmZlY3RvbW9iaWxlLWNvbSIsInN1YiI6ImZiOTA1YmU1LWU4YzEtNDdiNS1iNjBiLTA1M2Y3OGI1ZjdjMSIsInR5cCI6Ik9mZmxpbmUiLCJhenAiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsIm5vbmNlIjoiZGEyZTQ0YTktN2Y5Zi00MjRkLTkzOGItYmFmZTUxNGYyZWI5Iiwic2Vzc2lvbl9zdGF0ZSI6IjlmNzI0ZDI2LTI5Y2YtNGI0Ny1iNmI1LTI4MjU0NWQxMjkxOCIsInNjb3BlIjoib3BlbmlkIG9mZmxpbmVfYWNjZXNzIHByb2ZpbGUgZW1haWwiLCJzaWQiOiI5ZjcyNGQyNi0yOWNmLTRiNDctYjZiNS0yODI1NDVkMTI5MTgifQ.R92QxTp0yE9behMuD42dh_wAf6RCMsR6aiztuJJgH40 >> temp_perfecto.properties
echo perfecto.url=https://${perfecto.cloud.name}.perfectomobile.com/nexperience/perfectomobile/wd/hub >> temp_perfecto.properties
echo perfecto.project=Public App Test >> temp_perfecto.properties
echo perfecto.build=Test_1.0 >> temp_perfecto.properties
echo perfecto.debug=true >> temp_perfecto.properties
echo perfecto.video=true >> temp_perfecto.properties
echo perfecto.network.logs=true >> temp_perfecto.properties
echo perfecto.android.device.model=.* >> temp_perfecto.properties
echo perfecto.android.os.version=.* >> temp_perfecto.properties
echo perfecto.android.app.path=PUBLIC:ExpenseTracker/Native/ExpenseAppVer1.0.apk >> temp_perfecto.properties
echo perfecto.android.app.package=io.perfecto.expense.tracker >> temp_perfecto.properties
echo perfecto.ios.device.model=iPhone.* >> temp_perfecto.properties
echo perfecto.ios.os.version=16 >> temp_perfecto.properties
echo perfecto.ios.app.path=PUBLIC:ExpenseTracker/Native/InvoiceApp1.0.ipa >> temp_perfecto.properties
echo perfecto.ios.bundle.id=io.perfecto.expense.tracker >> temp_perfecto.properties
echo perfecto.new.command.timeout=300 >> temp_perfecto.properties
echo perfecto.implicit.wait=10 >> temp_perfecto.properties

REM Replace config temporarily
copy temp_perfecto.properties src\test\resources\config\perfecto.properties

echo Running test with public app...
mvn test -Dtest=UnifiedTestRunner -Dplatform=android -Dexecution.type=perfecto -q

REM Restore original config
copy src\test\resources\config\perfecto.properties.backup src\test\resources\config\perfecto.properties
del temp_perfecto.properties
del src\test\resources\config\perfecto.properties.backup

echo.
echo Test completed. Check results above.
pause
