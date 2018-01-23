#!/bin/bash

cd ..
echo "Compiling the PRS City Traffic Lights Supervision"
mvn -q -DskipTests clean package assembly:single
echo "Done"
cp ./target/city-traffic-lights-supervision-1.0-SNAPSHOT-jar-with-dependencies.jar ./docker/.

cd docker
docker build -t alteamfive/prs-city-tl-supervision .

rm -rf city-traffic-lights-supervision-1.0-SNAPSHOT-jar-with-dependencies.jar
