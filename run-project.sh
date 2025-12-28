#!/bin/bash

COMPOSE_FILE="./docker-compose.yml"

trap 'echo "Stopping..."; docker-compose -f "$COMPOSE_FILE" down' EXIT

docker-compose -f "$COMPOSE_FILE" up --build --remove-orphans