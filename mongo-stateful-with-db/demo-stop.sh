#!/usr/bin/env bash
echo "Undeploying services (processing units)..."
./undeploy.sh

echo "Killing GSCs with zones my-app-space, my-app-mirror"
../gs.sh container kill --zones=my-app-space,my-app-mirror

echo "Stopping Mongo DB..."
./demo-db/shutdown.sh

echo "Demo stop completed"