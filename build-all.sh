#!/bin/bash

# Make a global installation.
mvn -q clean install

# Administration client
cd administration-client/docker
./build.sh

echo ""
cd ../..

# Driver client
cd driver-client/docker
./build.sh

echo ""
cd ../..

# Backend
cd backend/docker
./build.sh

echo ""
cd ../..

# Traffic Light
cd traffic-light/docker
./build.sh

echo ""
cd ../..

# Traffic lights group orchestration
cd traffic-lights-group-orchestration/docker
./build.sh

echo ""
cd ../..

# City traffic lights supervision
cd city-traffic-lights-supervision/docker
./build.sh

echo ""
cd ../..

# Priorities Webservice
cd priorities-ws/docker
./build.sh

echo ""
cd ../..

# Routes Webservice
cd routes-ws/docker
./build.sh

echo ""
cd ../..

echo "Build finished!"