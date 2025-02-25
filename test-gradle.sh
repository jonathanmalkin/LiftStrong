#!/bin/bash

# Source the .zshrc file to load the environment variables
source ~/.zshrc

# Print the current Gradle JVM arguments
echo "Current Gradle JVM arguments:"
grep "org.gradle.jvmargs" ~/.gradle/gradle.properties

# Try running a simple Gradle command
echo ""
echo "Testing Gradle command:"
./gradlew --version

echo ""
echo "Test complete"
