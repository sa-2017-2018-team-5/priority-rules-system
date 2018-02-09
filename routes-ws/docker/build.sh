#!/bin/bash

cd ..
echo "Compiling the Routes Webservice..."
mvn -q -DskipTests clean package
echo "Done."
cp ./target/prs-routes.war ./docker

cd docker
docker build -t alteamfive/prs-routes .

rm -rf prs-routes.war
