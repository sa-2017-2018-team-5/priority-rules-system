#!/bin/bash

# Administration client
cd administration-client/docker
./build.sh
./publish.sh

echo ""
cd ../..

# Driver client
cd driver-client/docker
./build.sh
./publish.sh

echo ""
cd ../..

# Backend
cd backend/docker
./build.sh
./publish.sh

echo ""
cd ../..

echo "Build finished"