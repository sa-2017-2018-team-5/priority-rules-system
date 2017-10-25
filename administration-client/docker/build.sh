#!/bin/bash

cd ..
echo "Compiling the PRS Administration Client"
mvn -q -DskipTests clean package assembly:single
echo "Done"
cp ./target/administration-client-1.0-SNAPSHOT-jar-with-dependencies.jar ./docker/.

cd docker
docker build -t alteamfive/prs-administration-client .

rm -rf administration-client-1.0-SNAPSHOT-jar-with-dependencies.jar
