#!/bin/bash

cd ..
echo "Compiling the Priorities Webservice..."
mvn -q -DskipTests clean package
echo "Done."
cp ./target/prs-priorities.war ./docker

cd docker
docker build -t alteamfive/prs-priorities .

rm -rf prs-priorities.war
