#!/bin/bash
set -e

COMPOSE_FILE="./docker-compose.yml"
BUILDER_IMAGE="qfj-builder"
BUILDER_CONTAINER="qfj-extractor"

echo "Building QuickFIX/J from source..."
docker build -t "$BUILDER_IMAGE" -f Dockerfile.quickfixj-builder .

echo "Extracting artifacts to local maven repositories..."
docker rm -f "$BUILDER_CONTAINER" 2>/dev/null || true
docker create --name "$BUILDER_CONTAINER" "$BUILDER_IMAGE"

mkdir -p broker-back-end/local-m2/org/quickfixj
mkdir -p exchange-back-end/local-m2/org/quickfixj

docker cp "$BUILDER_CONTAINER":/root/.m2/repository/org/quickfixj/. broker-back-end/local-m2/org/quickfixj/
docker cp "$BUILDER_CONTAINER":/root/.m2/repository/org/quickfixj/. exchange-back-end/local-m2/org/quickfixj/

docker rm -f "$BUILDER_CONTAINER"

trap 'echo "Stopping..."; docker-compose -f "$COMPOSE_FILE" down; rm -rf broker-back-end/local-m2 exchange-back-end/local-m2' EXIT

docker-compose -f "$COMPOSE_FILE" up --build --remove-orphans