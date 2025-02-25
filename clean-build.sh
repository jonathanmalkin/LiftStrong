#!/bin/bash

# Script to clean and rebuild the LiftStrong app
# Usage: ./clean-build.sh

# Source .zshrc to ensure environment variables are set
source ~/.zshrc

# Set colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to display usage
usage() {
    echo -e "${BLUE}Usage: $0 [options]${NC}"
    echo -e "Options:"
    echo -e "  -h, --help     Show this help message"
    echo -e "  -d, --debug    Build debug APK (default)"
    echo -e "  -r, --release  Build release APK"
}

# Function to clean the project
clean_project() {
    echo -e "${YELLOW}Cleaning project...${NC}"
    ./gradlew clean
    if [ $? -ne 0 ]; then
        echo -e "${RED}Clean failed!${NC}"
        exit 1
    fi
    echo -e "${GREEN}Clean successful!${NC}"
}

# Function to build debug APK
build_debug() {
    echo -e "${YELLOW}Building debug APK...${NC}"
    ./gradlew assembleDebug
    if [ $? -ne 0 ]; then
        echo -e "${RED}Build failed!${NC}"
        exit 1
    fi
    echo -e "${GREEN}Debug build successful!${NC}"
    echo -e "${GREEN}APK location: ./app/build/outputs/apk/debug/app-debug.apk${NC}"
}

# Function to build release APK
build_release() {
    echo -e "${YELLOW}Building release APK...${NC}"
    ./gradlew assembleRelease
    if [ $? -ne 0 ]; then
        echo -e "${RED}Build failed!${NC}"
        exit 1
    fi
    echo -e "${GREEN}Release build successful!${NC}"
    echo -e "${GREEN}APK location: ./app/build/outputs/apk/release/app-release.apk${NC}"
}

# Main script execution starts here

# Parse command line options
BUILD_TYPE="debug"

while [[ $# -gt 0 ]]; do
    case $1 in
        -h|--help)
            usage
            exit 0
            ;;
        -d|--debug)
            BUILD_TYPE="debug"
            shift
            ;;
        -r|--release)
            BUILD_TYPE="release"
            shift
            ;;
        *)
            echo -e "${RED}Unknown option: $1${NC}"
            usage
            exit 1
            ;;
    esac
done

# Clean the project
clean_project

# Build the app
if [ "$BUILD_TYPE" = "debug" ]; then
    build_debug
else
    build_release
fi

echo -e "${GREEN}Clean and build completed successfully!${NC}"
