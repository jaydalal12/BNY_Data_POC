#!/usr/bin/env bash
if [ -e target ]; then
    echo "Purging existing files from target..."
    rm -r target
fi
mvn package
mkdir target
mv my-app-space/target/my-app-space-0.1.jar target/
mv my-app-mirror/target/my-app-mirror-0.1.jar target/