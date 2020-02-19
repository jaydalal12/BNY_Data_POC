@echo off
call my-app-env.bat

echo Building servicess (processing units)...
call build.bat

echo Starting HSQL DB...
start "HSQL DB (demo)" demo-db\run.bat

echo Creating container for mirror service (processing unit)...
call ..\gs container create --zone=my-app-mirror --memory=256m localhost
echo Creating %SPACE_INSTANCES% containers for space service (processing unit)...
call ..\gs container create --count=%SPACE_INSTANCES% --zone=my-app-space --memory=512m localhost

echo Deploying services (processing units)...
call deploy.bat

echo Demo start completed
pause