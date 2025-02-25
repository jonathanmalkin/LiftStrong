#!/bin/bash

# Script to build, install, and run the LiftStrong app on an Android emulator
# Usage: ./run-on-emulator.sh [emulator_name]
# If emulator_name is not provided, it will use the default emulator

# Source .zshrc to ensure environment variables are set
source ~/.zshrc

# Set colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# App package name
PACKAGE_NAME="com.liftstrong"

# Function to display usage
usage() {
    echo -e "${BLUE}Usage: $0 [emulator_name]${NC}"
    echo -e "If emulator_name is not provided, it will use the default emulator (Medium_Phone_API_35)"
}

# Function to check if an emulator is running
is_emulator_running() {
    $ANDROID_HOME/platform-tools/adb devices | grep -q "emulator"
    return $?
}

# Function to check if a specific emulator is running
is_specific_emulator_running() {
    local emulator_name=$1
    $ANDROID_HOME/emulator/emulator -list-running-avds | grep -q "$emulator_name"
    return $?
}

# Function to start an emulator
start_emulator() {
    local emulator_name=$1
    echo -e "${YELLOW}Starting emulator $emulator_name...${NC}"
    $ANDROID_HOME/emulator/emulator -avd "$emulator_name" -no-snapshot-load -no-boot-anim &
    
    # Wait for emulator to boot
    echo -e "${YELLOW}Waiting for emulator to boot...${NC}"
    $ANDROID_HOME/platform-tools/adb wait-for-device
    
    # Wait for the system to be ready
    while [ "$($ANDROID_HOME/platform-tools/adb shell getprop sys.boot_completed 2>/dev/null)" != "1" ]; do
        sleep 2
    done
    
    echo -e "${GREEN}Emulator is ready!${NC}"
}

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

# Function to install the app
install_app() {
    echo -e "${YELLOW}Installing app...${NC}"
    $ANDROID_HOME/platform-tools/adb install -r ./app/build/outputs/apk/debug/app-debug.apk
    if [ $? -ne 0 ]; then
        echo -e "${RED}Installation failed!${NC}"
        exit 1
    fi
    echo -e "${GREEN}Installation successful!${NC}"
}

# Function to launch the app
launch_app() {
    echo -e "${YELLOW}Launching app...${NC}"
    $ANDROID_HOME/platform-tools/adb shell am start -n "$PACKAGE_NAME/$PACKAGE_NAME.ui.MainActivity"
    if [ $? -ne 0 ]; then
        echo -e "${RED}Launch failed!${NC}"
        exit 1
    fi
    echo -e "${GREEN}App launched successfully!${NC}"
}

# Function to show logs
show_logs() {
    echo -e "${YELLOW}Showing logs for $PACKAGE_NAME...${NC}"
    echo -e "${YELLOW}Press Ctrl+C to stop viewing logs${NC}"
    $ANDROID_HOME/platform-tools/adb logcat -v time | grep "$PACKAGE_NAME"
}

# Main script execution starts here

# Check if Android SDK is set up
if [ -z "$ANDROID_HOME" ]; then
    echo -e "${RED}ANDROID_HOME is not set. Please set up your environment variables.${NC}"
    exit 1
fi

# Get emulator name from argument or use default
EMULATOR_NAME=${1:-"Medium_Phone_API_35"}

# Check if the specified emulator exists
if ! $ANDROID_HOME/emulator/emulator -list-avds | grep -q "$EMULATOR_NAME"; then
    echo -e "${RED}Emulator $EMULATOR_NAME does not exist!${NC}"
    echo -e "${YELLOW}Available emulators:${NC}"
    $ANDROID_HOME/emulator/emulator -list-avds
    exit 1
fi

# Check if the emulator is already running
if is_emulator_running; then
    if is_specific_emulator_running "$EMULATOR_NAME"; then
        echo -e "${GREEN}Emulator $EMULATOR_NAME is already running.${NC}"
    else
        echo -e "${YELLOW}Another emulator is running. Please close it first.${NC}"
        exit 1
    fi
else
    start_emulator "$EMULATOR_NAME"
fi

# Build, install, and launch the app
build_app
install_app
launch_app

# Show logs
show_logs
