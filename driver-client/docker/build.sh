#!/bin/bash

cd ..
echo "Compiling the PRS Driver Client"
mvn -q -DskipTests clean package assembly:single
echo "Done"
cp ./target/driver-client-1.0-SNAPSHOT-jar-with-dependencies.jar ./docker/.

cd docker
docker build -t alteamfive/prs-driver-client .

rm -rf driver-client-1.0-SNAPSHOT-jar-with-dependencies.jar
