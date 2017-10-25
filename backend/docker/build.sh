#!/bin/bash

#Preparing environment
cd ..
echo "Compiling the Priority Rules System Backend..."
mvn -q -DskipTests clean package
echo "Done."
cp ./target/priority-rules-backend.war ./docker

# building the docker image
cd docker
docker build -t alteamfive/prs-backend .

# cleaning up the environment
rm -rf priority-rules-backend.war
