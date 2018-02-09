#!/bin/bash

cd ..
echo "Compiling the TL Webservice..."
mvn -q -DskipTests clean package
echo "Done."
cp ./target/prs-traffic-lights.war ./docker

cd docker
docker build -t alteamfive/prs-traffic-lights .

rm -rf prs-traffic-lights.war
