#!/bin/bash

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

# City traffic lights orchestration
cd city-traffic-lights-orchestration/docker
./build.sh

echo ""
cd ../..


echo "Build finished!"