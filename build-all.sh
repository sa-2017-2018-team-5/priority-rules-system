#!/bin/bash

# Administration client
cd administration-client/docker
./build.sh

cd ../..

# Driver client
cd driver-client/docker
./build.sh

cd ../..

# Backend
cd backend/docker
./build.sh

cd ../..