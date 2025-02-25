#!/bin/bash

# Script to build, install, and run the LiftStrong app on a physical Android device
# Usage: ./run-on-device.sh [device_id]
# If device_id is not provided, it will use the first connected device

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
    echo -e "${BLUE}Usage: $0 [device_id]${NC}"
    echo -e "If device_id is not provided, it will use the first connected device"
}

# Function to check if a device is connected
is_device_connected() {
    local device_count=$($ANDROID_HOME/platform-tools/adb devices | grep -v "List" | grep -v "^$" | grep -v "emulator" | wc -l)
    if [ "$device_count" -gt 0 ]; then
        return 0
    else
        return 1
    fi
}

# Function to check if a specific device is connected
is_specific_device_connected() {
    local device_id=$1
    $ANDROID_HOME/platform-tools/adb devices | grep -q "$device_id"
    return $?
}

# Function to get the first connected device
get_first_device() {
    $ANDROID_HOME/platform-tools/adb devices | grep -v "List" | grep -v "^$" | grep -v "emulator" | head -1 | awk '{print $1}'
}

# Function to list all connected devices
list_devices() {
    echo -e "${YELLOW}Connected devices:${NC}"
    $ANDROID_HOME/platform-tools/adb devices | grep -v "List" | grep -v "^$" | grep -v "emulator"
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

# Function to install the app on a specific device
install_app() {
    local device_id=$1
    echo -e "${YELLOW}Installing app on device $device_id...${NC}"
    $ANDROID_HOME/platform-tools/adb -s "$device_id" install -r ./app/build/outputs/apk/debug/app-debug.apk
    if [ $? -ne 0 ]; then
        echo -e "${RED}Installation failed!${NC}"
        exit 1
    fi
    echo -e "${GREEN}Installation successful!${NC}"
}

# Function to launch the app on a specific device
launch_app() {
    local device_id=$1
    echo -e "${YELLOW}Launching app on device $device_id...${NC}"
    $ANDROID_HOME/platform-tools/adb -s "$device_id" shell am start -n "$PACKAGE_NAME/$PACKAGE_NAME.ui.MainActivity"
    if [ $? -ne 0 ]; then
        echo -e "${RED}Launch failed!${NC}"
        exit 1
    fi
    echo -e "${GREEN}App launched successfully!${NC}"
}

# Function to show logs for a specific device
show_logs() {
    local device_id=$1
    echo -e "${YELLOW}Showing logs for $PACKAGE_NAME on device $device_id...${NC}"
    echo -e "${YELLOW}Press Ctrl+C to stop viewing logs${NC}"
    $ANDROID_HOME/platform-tools/adb -s "$device_id" logcat -v time | grep "$PACKAGE_NAME"
}

# Main script execution starts here

# Check if Android SDK is set up
if [ -z "$ANDROID_HOME" ]; then
    echo -e "${RED}ANDROID_HOME is not set. Please set up your environment variables.${NC}"
    exit 1
fi

# Check if any device is connected
if ! is_device_connected; then
    echo -e "${RED}No physical devices connected!${NC}"
    echo -e "${YELLOW}Please connect a device and enable USB debugging.${NC}"
    exit 1
fi

# Get device ID from argument or use the first connected device
if [ -n "$1" ]; then
    DEVICE_ID=$1
    # Check if the specified device is connected
    if ! is_specific_device_connected "$DEVICE_ID"; then
        echo -e "${RED}Device $DEVICE_ID is not connected!${NC}"
        list_devices
        exit 1
    fi
else
    DEVICE_ID=$(get_first_device)
    echo -e "${YELLOW}Using device: $DEVICE_ID${NC}"
fi

# Build, install, and launch the app
build_app
install_app "$DEVICE_ID"
launch_app "$DEVICE_ID"

# Show logs
show_logs "$DEVICE_ID"
