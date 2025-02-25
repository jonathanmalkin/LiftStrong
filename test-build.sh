#!/bin/bash

# Source .zshrc to ensure environment variables are set
source ~/.zshrc

# Set colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to build the app
build_app() {
    echo -e "${YELLOW}Building app...${NC}"
    ./gradlew assembleDebug
    if [ $? -ne 0 ]; then
        echo -e "${RED}Build failed!${NC}"
        exit 1
    fi
    echo -e "${GREEN}Build successful!${NC}"
}

# Main script execution starts here

# Check if Android SDK is set up
if [ -z "$ANDROID_HOME" ]; then
    echo -e "${RED}ANDROID_HOME is not set. Please set up your environment variables.${NC}"
    exit 1
fi

# Build the app
build_app

echo -e "${GREEN}Test completed successfully!${NC}"
