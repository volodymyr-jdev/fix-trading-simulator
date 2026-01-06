#!/bin/bash
set -e

# Configuration
CMD="docker"
IMAGE="qfj-builder"
CONTAINER="qfj-extractor"
VERSION="3.0.0-SNAPSHOT"
TEMP_DIR="./qfj_temp_build"
DEST_DIR="$HOME/.m2/repository/org/quickfixj"

# Cleanup container and temp directory on exit
trap '$CMD rm -f "$CONTAINER" >/dev/null 2>&1; rm -rf "$TEMP_DIR"' EXIT

echo "1. Building Image (Git Clone & Maven Build)"

$CMD build -t "$IMAGE" --build-arg VERSION="$VERSION" -f Dockerfile.quickfixj-builder .

echo "2. Extracting artifacts"

# Extract to a temp dir first
$CMD create --name "$CONTAINER" "$IMAGE"
$CMD cp "$CONTAINER:/root/.m2/repository/org/quickfixj" "$TEMP_DIR"

# Atomic replacement in .m2
echo "4. Installing to $DEST_DIR"
rm -rf "$DEST_DIR"
mkdir -p "$(dirname "$DEST_DIR")"
mv "$TEMP_DIR" "$DEST_DIR"

echo "Success."