#!/bin/bash

cd ..
echo "Compiling the Priority Rules System Backend..."
mvn -q -DskipTests clean package
echo "Done."
cp ./target/priority-rules-backend.war ./docker

cd docker
docker build -t alteamfive/prs-backend .

rm -rf priority-rules-backend.war
