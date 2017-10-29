#!/bin/bash

docker run --rm -d -p 8080:8080 --env-file=./resources/settings.env alteamfive/prs-backend