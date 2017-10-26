#!/bin/bash

cd ..
echo "Compiling the PRS City Traffic Lights Orchestration"
mvn -q -DskipTests clean package assembly:single
echo "Done"
cp ./target/city-traffic-lights-orchestration-1.0-SNAPSHOT-jar-with-dependencies.jar ./docker/.

cd docker
docker build -t alteamfive/prs-city-tl-orchestration .

rm -rf city-traffic-lights-orchestration-1.0-SNAPSHOT-jar-with-dependencies.jar
