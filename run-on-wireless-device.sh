#!/bin/bash

# Script to build, install, and run the LiftStrong app on an Android device using wireless debugging
# Usage: ./run-on-wireless-device.sh [ip_address:port]
# If ip_address:port is not provided, it will use the first wirelessly connected device or guide through setup

# Source .zshrc to ensure environment variables are set
source ~/.zshrc

# Set colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

# App package name
PACKAGE_NAME="com.liftstrong"

# Function to display usage
usage() {
    echo -e "${BLUE}Usage: $0 [ip_address:port]${NC}"
    echo -e "If ip_address:port is not provided, it will use the first wirelessly connected device"
    echo -e "or guide you through the wireless debugging setup process."
}

# Function to check if a device is connected wirelessly
is_wireless_device_connected() {
    local wireless_count=$($ANDROID_HOME/platform-tools/adb devices | grep -v "List" | grep -v "^$" | grep -E "[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+" | wc -l)
    if [ "$wireless_count" -gt 0 ]; then
        return 0
    else
        return 1
    fi
}

# Function to check if a specific wireless device is connected
is_specific_wireless_device_connected() {
    local device_addr=$1
    $ANDROID_HOME/platform-tools/adb devices | grep -q "$device_addr"
    return $?
}

# Function to get the first wirelessly connected device
get_first_wireless_device() {
    $ANDROID_HOME/platform-tools/adb devices | grep -v "List" | grep -v "^$" | grep -E "[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+" | head -1 | awk '{print $1}'
}

# Function to list all wirelessly connected devices
list_wireless_devices() {
    echo -e "${YELLOW}Wirelessly connected devices:${NC}"
    $ANDROID_HOME/platform-tools/adb devices | grep -v "List" | grep -v "^$" | grep -E "[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+"
}

# Function to guide through wireless debugging setup
setup_wireless_debugging() {
    echo -e "${YELLOW}Setting up wireless debugging for Android 11+ (including your Android 15 device)...${NC}"
    echo -e "${CYAN}Please follow these steps on your Pixel 7:${NC}"
    echo -e "1. Go to ${BLUE}Settings > System > Developer options${NC}"
    echo -e "2. Enable ${BLUE}Wireless debugging${NC}"
    echo -e "3. Tap on ${BLUE}Wireless debugging${NC} to open its settings"
    echo -e "4. Tap on ${BLUE}Pair device with pairing code${NC}"
    echo -e "5. You'll see a pairing code on your device"
    echo -e ""
    
    # Ask for IP address, port, and pairing code
    echo -e "${YELLOW}Enter the IP address and port shown on your device (e.g., 192.168.1.10:37000):${NC}"
    read -p "> " ip_port
    
    echo -e "${YELLOW}Enter the pairing code shown on your device:${NC}"
    read -p "> " pairing_code
    
    # Pair the device
    echo -e "${YELLOW}Pairing with device at $ip_port...${NC}"
    $ANDROID_HOME/platform-tools/adb pair $ip_port $pairing_code
    
    if [ $? -ne 0 ]; then
        echo -e "${RED}Pairing failed! Please check the IP address, port, and pairing code.${NC}"
        return 1
    fi
    
    # Extract just the IP address (without the pairing port)
    ip_address=$(echo $ip_port | cut -d':' -f1)
    
    # The connection port is usually 5555
    echo -e "${YELLOW}Connecting to device at $ip_address:5555...${NC}"
    $ANDROID_HOME/platform-tools/adb connect $ip_address:5555
    
    if [ $? -ne 0 ]; then
        echo -e "${RED}Connection failed! Please check your network connection.${NC}"
        return 1
    fi
    
    echo -e "${GREEN}Successfully connected to device wirelessly!${NC}"
    return 0
}

# Function to connect to a wireless device
connect_wireless_device() {
    local device_addr=$1
    echo -e "${YELLOW}Connecting to device at $device_addr...${NC}"
    $ANDROID_HOME/platform-tools/adb connect $device_addr
    
    if [ $? -ne 0 ]; then
        echo -e "${RED}Connection failed! Please check the IP address and port.${NC}"
        return 1
    fi
    
    echo -e "${GREEN}Successfully connected to device at $device_addr!${NC}"
    return 0
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
    local device_addr=$1
    echo -e "${YELLOW}Installing app on device $device_addr...${NC}"
    $ANDROID_HOME/platform-tools/adb -s "$device_addr" install -r ./app/build/outputs/apk/debug/app-debug.apk
    if [ $? -ne 0 ]; then
        echo -e "${RED}Installation failed!${NC}"
        exit 1
    fi
    echo -e "${GREEN}Installation successful!${NC}"
}

# Function to launch the app on a specific device
launch_app() {
    local device_addr=$1
    echo -e "${YELLOW}Launching app on device $device_addr...${NC}"
    $ANDROID_HOME/platform-tools/adb -s "$device_addr" shell am start -n "$PACKAGE_NAME/$PACKAGE_NAME.ui.MainActivity"
    if [ $? -ne 0 ]; then
        echo -e "${RED}Launch failed!${NC}"
        exit 1
    fi
    echo -e "${GREEN}App launched successfully!${NC}"
}

# Function to show logs for a specific device
show_logs() {
    local device_addr=$1
    echo -e "${YELLOW}Showing logs for $PACKAGE_NAME on device $device_addr...${NC}"
    echo -e "${YELLOW}Press Ctrl+C to stop viewing logs${NC}"
    $ANDROID_HOME/platform-tools/adb -s "$device_addr" logcat -v time | grep "$PACKAGE_NAME"
}

# Function to check wireless connection stability
check_connection_stability() {
    local device_addr=$1
    echo -e "${YELLOW}Checking connection stability to $device_addr...${NC}"
    
    # Try to get device properties as a connection test
    local result=$($ANDROID_HOME/platform-tools/adb -s "$device_addr" shell getprop ro.product.model 2>&1)
    
    if [[ $result == *"error"* || $result == *"failed"* || $result == *"unable"* ]]; then
        echo -e "${RED}Connection to $device_addr appears unstable!${NC}"
        echo -e "${YELLOW}Attempting to reconnect...${NC}"
        $ANDROID_HOME/platform-tools/adb disconnect "$device_addr"
        sleep 2
        $ANDROID_HOME/platform-tools/adb connect "$device_addr"
        
        # Check again
        result=$($ANDROID_HOME/platform-tools/adb -s "$device_addr" shell getprop ro.product.model 2>&1)
        if [[ $result == *"error"* || $result == *"failed"* || $result == *"unable"* ]]; then
            echo -e "${RED}Reconnection failed. Please check your network and device.${NC}"
            return 1
        fi
    fi
    
    echo -e "${GREEN}Connection to $device_addr is stable (Device: $result)${NC}"
    return 0
}

# Function to display troubleshooting tips
show_troubleshooting_tips() {
    echo -e "${YELLOW}Wireless Debugging Troubleshooting Tips:${NC}"
    echo -e "1. Ensure your computer and Android device are on the same WiFi network"
    echo -e "2. Check that Developer Options and Wireless Debugging are enabled on your device"
    echo -e "3. Try restarting the ADB server with: ${BLUE}adb kill-server && adb start-server${NC}"
    echo -e "4. Verify that no firewall is blocking the connection"
    echo -e "5. Try reconnecting the device with: ${BLUE}adb disconnect <ip:port> && adb connect <ip:port>${NC}"
    echo -e "6. If all else fails, try connecting via USB first, then switch to wireless"
}

# Main script execution starts here

# Check if Android SDK is set up
if [ -z "$ANDROID_HOME" ]; then
    echo -e "${RED}ANDROID_HOME is not set. Please set up your environment variables.${NC}"
    exit 1
fi

# Get device address from argument or use the first connected wireless device
if [ -n "$1" ]; then
    DEVICE_ADDR=$1
    # Check if the specified device is connected
    if ! is_specific_wireless_device_connected "$DEVICE_ADDR"; then
        echo -e "${YELLOW}Device $DEVICE_ADDR is not connected. Attempting to connect...${NC}"
        connect_wireless_device "$DEVICE_ADDR"
        if [ $? -ne 0 ]; then
            echo -e "${RED}Could not connect to $DEVICE_ADDR${NC}"
            show_troubleshooting_tips
            exit 1
        fi
    fi
else
    # Check if any wireless device is connected
    if is_wireless_device_connected; then
        DEVICE_ADDR=$(get_first_wireless_device)
        echo -e "${YELLOW}Using wirelessly connected device: $DEVICE_ADDR${NC}"
    else
        echo -e "${YELLOW}No wirelessly connected devices found.${NC}"
        echo -e "${YELLOW}Would you like to set up wireless debugging? (y/n)${NC}"
        read -p "> " setup_choice
        
        if [[ $setup_choice == "y" || $setup_choice == "Y" ]]; then
            setup_wireless_debugging
            if [ $? -ne 0 ]; then
                show_troubleshooting_tips
                exit 1
            fi
            DEVICE_ADDR=$(get_first_wireless_device)
        else
            echo -e "${RED}Wireless debugging setup cancelled.${NC}"
            exit 1
        fi
    fi
fi

# Check connection stability
check_connection_stability "$DEVICE_ADDR"
if [ $? -ne 0 ]; then
    show_troubleshooting_tips
    exit 1
fi

# Build, install, and launch the app
build_app
install_app "$DEVICE_ADDR"
launch_app "$DEVICE_ADDR"

# Show logs
show_logs "$DEVICE_ADDR"
