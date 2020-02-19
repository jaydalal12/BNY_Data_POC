#!/usr/bin/env bash
. ./my-app-env.sh

echo "Building services (processing units)..."
./build.sh

echo "Starting Mongo DB..."
xterm -e ./demo-db/run.sh &

echo "Creating container for mirror service (processing unit)..."
../gs.sh container create --zone=my-app-mirror --memory=256m localhost
echo "Creating $SPACE_INSTANCES containers for space service (processing unit)..."
../gs.sh container create --count=$SPACE_INSTANCES --zone=my-app-space --memory=512m localhost

echo "Deploying services (processing units)..."
./deploy.sh

echo "Demo start completed"
