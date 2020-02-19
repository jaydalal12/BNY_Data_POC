@echo off
echo Undeploying services (processing units)...
call undeploy.bat

echo Killing GSCs with zones my-app-space, my-app-mirror
call ..\gs container kill --zones=my-app-space,my-app-mirror

echo Stopping HSQL DB...
call demo-db\shutdown.bat

echo Demo stop completed
pause