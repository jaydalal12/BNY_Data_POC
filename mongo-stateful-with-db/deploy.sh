#!/usr/bin/env bash
. ./my-app-env.sh
../gs.sh pu deploy --properties=my-app-values.yaml --zones=my-app-mirror --property partitions=${SPACE_PARTITIONS} --property backups=${SPACE_BACKUPS} my-app-mirror target/my-app-mirror-0.1.jar
../gs.sh pu deploy --properties=my-app-values.yaml --zones=my-app-space --partitions=${SPACE_PARTITIONS} --ha=${SPACE_HA} my-app-space target/my-app-space-0.1.jar