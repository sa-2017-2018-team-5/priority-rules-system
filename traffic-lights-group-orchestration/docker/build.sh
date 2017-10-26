#!/bin/bash

cd ..
echo "Compiling the PRS Traffic Light"
mvn -q -DskipTests clean package assembly:single
echo "Done"
cp ./target/traffic-lights-group-orchestration-1.0-SNAPSHOT-jar-with-dependencies.jar ./docker/.

cd docker
docker build -t alteamfive/prs-tl-group-orchestration .

rm -rf traffic-lights-group-orchestration-1.0-SNAPSHOT-jar-with-dependencies.jar
