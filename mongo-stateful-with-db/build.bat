@echo off
if exist target (
    echo Purging existing files from target...	
	rd /s /q target
)
call mvn package
md target
move my-app-space\target\my-app-space-0.1.jar target\
move my-app-mirror\target\my-app-mirror-0.1.jar target\