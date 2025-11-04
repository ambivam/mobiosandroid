@echo off
echo Testing with Public App to isolate authentication issue...
echo.

REM Create temporary config with public app
echo # Perfecto Configuration - Public App Test > temp_public.properties
echo perfecto.cloud.name=trial >> temp_public.properties
echo perfecto.security.token=eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI2ZDM2NmJiNS01NDAyLTQ4MmMtYTVhOC1kODZhODk4MDYyZjIifQ.eyJpYXQiOjE3NjIyNzI2ODcsImp0aSI6ImIxYWE4NDc5LTUzNDctNGE3Yi1iYjUzLTYyZTI3NjA5NzU5NyIsImlzcyI6Imh0dHBzOi8vYXV0aDMucGVyZmVjdG9tb2JpbGUuY29tL2F1dGgvcmVhbG1zL3RyaWFsLXBlcmZlY3RvbW9iaWxlLWNvbSIsImF1ZCI6Imh0dHBzOi8vYXV0aDMucGVyZmVjdG9tb2JpbGUuY29tL2F1dGgvcmVhbG1zL3RyaWFsLXBlcmZlY3RvbW9iaWxlLWNvbSIsInN1YiI6ImZiOTA1YmU1LWU4YzEtNDdiNS1iNjBiLTA1M2Y3OGI1ZjdjMSIsInR5cCI6Ik9mZmxpbmUiLCJhenAiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsIm5vbmNlIjoiNGE5OGQ5YmYtNjJmNy00MTZkLTg5MDgtNjliZjhhYTZiZWUyIiwic2Vzc2lvbl9zdGF0ZSI6IjIyMTQwMjBiLWFiNjgtNDMxZC1iZTUwLTY1MmI5OGNlNDdjMiIsInNjb3BlIjoib3BlbmlkIG9mZmxpbmVfYWNjZXNzIHByb2ZpbGUgZW1haWwiLCJzaWQiOiIyMjE0MDIwYi1hYjY4LTQzMWQtYmU1MC02NTJiOThjZTQ3YzIifQ.Ch1hmZ-x68uGsolSLmklVKpMFHBfBW-GIUPY-zO3_vc >> temp_public.properties
echo perfecto.url=https://${perfecto.cloud.name}.perfectomobile.com/nexperience/perfectomobile/wd/hub >> temp_public.properties
echo perfecto.project=Public App Test >> temp_public.properties
echo perfecto.build=Test_1.0 >> temp_public.properties
echo perfecto.debug=true >> temp_public.properties
echo perfecto.video=true >> temp_public.properties
echo perfecto.network.logs=true >> temp_public.properties
echo perfecto.android.device.model=.* >> temp_public.properties
echo perfecto.android.os.version=.* >> temp_public.properties
echo perfecto.android.app.path=PUBLIC:ExpenseTracker/Native/ExpenseAppVer1.0.apk >> temp_public.properties
echo perfecto.android.app.package=io.perfecto.expense.tracker >> temp_public.properties
echo perfecto.ios.device.model=iPhone.* >> temp_public.properties
echo perfecto.ios.os.version=16 >> temp_public.properties
echo perfecto.ios.app.path=PUBLIC:ExpenseTracker/Native/InvoiceApp1.0.ipa >> temp_public.properties
echo perfecto.ios.bundle.id=io.perfecto.expense.tracker >> temp_public.properties
echo perfecto.new.command.timeout=300 >> temp_public.properties
echo perfecto.implicit.wait=10 >> temp_public.properties

REM Backup and replace config
copy src\test\resources\config\perfecto.properties perfecto.properties.backup
copy temp_public.properties src\test\resources\config\perfecto.properties

echo Running test with PUBLIC app...
mvn test -Dtest=UnifiedTestRunner -Dplatform=android -Dexecution.type=perfecto -q

REM Restore original config
copy perfecto.properties.backup src\test\resources\config\perfecto.properties
del temp_public.properties
del perfecto.properties.backup

echo.
echo If PUBLIC app also fails with 401, the issue is with your Perfecto account/token.
echo If PUBLIC app works, the issue is with your private app permissions.
pause
