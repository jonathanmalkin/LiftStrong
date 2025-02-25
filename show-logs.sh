#!/bin/bash

# Script to show logs for the LiftStrong app
# Usage: ./show-logs.sh [device_id]
# If device_id is not provided, it will use the first connected device or emulator

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
    echo -e "If device_id is not provided, it will use the first connected device or emulator"
}

# Function to check if any device or emulator is connected
is_any_device_connected() {
    local device_count=$($ANDROID_HOME/platform-tools/adb devices | grep -v "List" | grep -v "^$" | wc -l)
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

# Function to get the first connected device or emulator
get_first_device() {
    $ANDROID_HOME/platform-tools/adb devices | grep -v "List" | grep -v "^$" | head -1 | awk '{print $1}'
}

# Function to list all connected devices and emulators
list_devices() {
    echo -e "${YELLOW}Connected devices and emulators:${NC}"
    $ANDROID_HOME/platform-tools/adb devices | grep -v "List" | grep -v "^$"
}

# Function to clear logs
clear_logs() {
    local device_id=$1
    echo -e "${YELLOW}Clearing logs on device $device_id...${NC}"
    $ANDROID_HOME/platform-tools/adb -s "$device_id" logcat -c
    echo -e "${GREEN}Logs cleared!${NC}"
}

# Function to show logs for a specific device
show_logs() {
    local device_id=$1
    local filter=$2
    
    echo -e "${YELLOW}Showing logs for device $device_id...${NC}"
    echo -e "${YELLOW}Press Ctrl+C to stop viewing logs${NC}"
    
    if [ -n "$filter" ]; then
        echo -e "${YELLOW}Filtering logs for: $filter${NC}"
        $ANDROID_HOME/platform-tools/adb -s "$device_id" logcat -v time | grep "$filter"
    else
        echo -e "${YELLOW}Showing all logs${NC}"
        $ANDROID_HOME/platform-tools/adb -s "$device_id" logcat -v time
    fi
}

# Main script execution starts here

# Check if Android SDK is set up
if [ -z "$ANDROID_HOME" ]; then
    echo -e "${RED}ANDROID_HOME is not set. Please set up your environment variables.${NC}"
    exit 1
fi

# Check if any device is connected
if ! is_any_device_connected; then
    echo -e "${RED}No devices or emulators connected!${NC}"
    echo -e "${YELLOW}Please connect a device or start an emulator.${NC}"
    exit 1
fi

# Parse command line options
CLEAR_LOGS=false
FILTER="$PACKAGE_NAME"
DEVICE_ID=""

while [[ $# -gt 0 ]]; do
    case $1 in
        -c|--clear)
            CLEAR_LOGS=true
            shift
            ;;
        -f|--filter)
            FILTER="$2"
            shift 2
            ;;
        -a|--all)
            FILTER=""
            shift
            ;;
        -h|--help)
            usage
            exit 0
            ;;
        *)
            DEVICE_ID="$1"
            shift
            ;;
    esac
done

# If no device ID is provided, use the first connected device
if [ -z "$DEVICE_ID" ]; then
    DEVICE_ID=$(get_first_device)
    echo -e "${YELLOW}Using device: $DEVICE_ID${NC}"
else
    # Check if the specified device is connected
    if ! is_specific_device_connected "$DEVICE_ID"; then
        echo -e "${RED}Device $DEVICE_ID is not connected!${NC}"
        list_devices
        exit 1
    fi
fi

# Clear logs if requested
if [ "$CLEAR_LOGS" = true ]; then
    clear_logs "$DEVICE_ID"
fi

# Show logs
show_logs "$DEVICE_ID" "$FILTER"
