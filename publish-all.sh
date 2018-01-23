#!/bin/bash

# Administration client
cd administration-client/docker
./publish.sh

echo ""
cd ../..

# Driver client
cd driver-client/docker
./publish.sh

echo ""
cd ../..

# Backend
cd backend/docker
./publish.sh

echo ""
cd ../..

# Traffic Light
cd traffic-light/docker
./publish.sh

echo ""
cd ../..

# Traffic lights group orchestration
cd traffic-lights-group-orchestration/docker
./publish.sh

echo ""
cd ../..

# City traffic lights supervision
cd city-traffic-lights-supervision/docker
./publish.sh

echo ""
cd ../..


echo "All images published!"