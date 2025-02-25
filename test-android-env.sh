#!/bin/bash

# Test Android Development Environment Setup
# This script checks if the Android development environment is properly set up

echo "=== Testing Android Development Environment ==="
echo ""

# Check Java
echo "Checking Java installation..."
if command -v java &> /dev/null; then
    java_version=$(java -version 2>&1 | head -n 1)
    echo "✅ Java is installed: $java_version"
else
    echo "❌ Java is not installed or not in PATH"
fi

# Check JAVA_HOME
echo ""
echo "Checking JAVA_HOME..."
if [ -n "$JAVA_HOME" ]; then
    echo "✅ JAVA_HOME is set to: $JAVA_HOME"
    if [ -d "$JAVA_HOME" ]; then
        echo "   Directory exists"
    else
        echo "❌ Directory does not exist: $JAVA_HOME"
    fi
else
    echo "❌ JAVA_HOME is not set"
fi

# Check Android SDK
echo ""
echo "Checking Android SDK..."
if [ -n "$ANDROID_HOME" ]; then
    echo "✅ ANDROID_HOME is set to: $ANDROID_HOME"
    if [ -d "$ANDROID_HOME" ]; then
        echo "   Directory exists"
    else
        echo "❌ Directory does not exist: $ANDROID_HOME"
    fi
else
    echo "❌ ANDROID_HOME is not set"
fi

# Check Android SDK components
echo ""
echo "Checking Android SDK components..."

# Check platform-tools
if [ -d "$ANDROID_HOME/platform-tools" ]; then
    echo "✅ platform-tools found"
    if command -v "$ANDROID_HOME/platform-tools/adb" &> /dev/null; then
        adb_version=$("$ANDROID_HOME/platform-tools/adb" version | head -n 1)
        echo "   ADB version: $adb_version"
    else
        echo "❌ ADB not found or not executable"
    fi
else
    echo "❌ platform-tools not found"
fi

# Check build-tools
if [ -d "$ANDROID_HOME/build-tools" ]; then
    echo "✅ build-tools found"
    build_tools_versions=$(ls -1 "$ANDROID_HOME/build-tools" | sort -V)
    echo "   Available versions:"
    echo "$build_tools_versions" | sed 's/^/   - /'
else
    echo "❌ build-tools not found"
fi

# Check platforms
if [ -d "$ANDROID_HOME/platforms" ]; then
    echo "✅ platforms found"
    platforms=$(ls -1 "$ANDROID_HOME/platforms" | sort -V)
    echo "   Available platforms:"
    echo "$platforms" | sed 's/^/   - /'
else
    echo "❌ platforms not found"
fi

# Check emulator
if [ -d "$ANDROID_HOME/emulator" ]; then
    echo "✅ emulator found"
    if command -v "$ANDROID_HOME/emulator/emulator" &> /dev/null; then
        echo "   Available AVDs:"
        "$ANDROID_HOME/emulator/emulator" -list-avds | sed 's/^/   - /'
    else
        echo "❌ emulator not found or not executable"
    fi
else
    echo "❌ emulator not found"
fi

# Check Gradle
echo ""
echo "Checking Gradle..."
if command -v gradle &> /dev/null; then
    gradle_version=$(gradle --version | grep "Gradle " | head -n 1)
    echo "✅ Gradle is installed: $gradle_version"
else
    echo "❌ Gradle is not installed or not in PATH"
    echo "   (This is not critical as the project uses the Gradle wrapper)"
fi

# Check VS Code extensions
echo ""
echo "Checking VS Code extensions..."
if command -v code &> /dev/null; then
    echo "✅ VS Code is installed"
    
    # List of required extensions
    extensions=(
        "adelphes.android-dev-ext"
        "mathiasfrohlich.kotlin"
        "dotjoshjohnson.xml"
        "pkief.material-icon-theme"
        "christian-kohler.path-intellisense"
        "naco-siren.gradle-language"
        "mhutchie.git-graph"
    )
    
    # Check each extension
    for ext in "${extensions[@]}"; do
        if code --list-extensions | grep -q "$ext"; then
            echo "✅ Extension installed: $ext"
        else
            echo "❌ Extension not installed: $ext"
        fi
    done
else
    echo "❌ VS Code is not installed or not in PATH"
fi

echo ""
echo "=== Environment Test Complete ==="
