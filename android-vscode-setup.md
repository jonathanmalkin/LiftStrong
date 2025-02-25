# Android Development with VS Code for LiftStrong

This document provides instructions for setting up and using Visual Studio Code for Android development on the LiftStrong project, with a focus on automated build and run processes.

## Environment Setup

The following environment has been configured for Android development:

- **JDK 17**: Installed at `/usr/local/opt/openjdk@17`
- **Android SDK**: Installed at `~/Library/Android/sdk`
- **Gradle**: Version 8.12.1
- **VS Code**: Configured with Android development extensions
- **Android Emulator**: "Medium_Phone_API_35" is available

## Environment Variables

The following environment variables have been added to your `~/.zshrc` file:

```bash
export ANDROID_HOME=$HOME/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/platform-tools
export PATH=$PATH:$ANDROID_HOME/emulator
export PATH=$PATH:$ANDROID_HOME/tools/bin
export JAVA_HOME=/usr/local/opt/openjdk@17
```

To apply these changes to your current terminal session, run:

```bash
source ~/.zshrc
```

## VS Code Extensions

The following VS Code extensions have been installed:

- **Android Extension Pack** (adelphes.android-dev-ext): Comprehensive Android development tools
- **Kotlin Language** (mathiasfrohlich.kotlin): Syntax highlighting and IntelliSense for Kotlin
- **XML Tools** (dotjoshjohnson.xml): For Android layout files
- **Material Icon Theme** (pkief.material-icon-theme): Better visualization of Android project files
- **Path Intellisense** (christian-kohler.path-intellisense): Auto-completes filenames
- **Gradle Language Support** (naco-siren.gradle-language): For build.gradle files
- **Git Graph** (mhutchie.git-graph): Visualize Git history

## VS Code Configuration

The following configuration files have been created in the `.vscode` directory:

- **settings.json**: Contains VS Code settings specific to Android and Kotlin development
- **launch.json**: Defines launch configurations for debugging the app on emulators and physical devices
- **tasks.json**: Defines tasks for building, installing, and running the app
- **keybindings.json**: Defines keyboard shortcuts for common Android development tasks
- **README.md**: Documentation for the VS Code setup

## Automation Scripts

Several shell scripts have been created to automate the Android development workflow:

- **run-on-emulator.sh**: Builds, installs, and runs the app on an emulator
  ```bash
  ./run-on-emulator.sh [emulator_name]
  ```

- **run-on-device.sh**: Builds, installs, and runs the app on a physical device
  ```bash
  ./run-on-device.sh [device_id]
  ```

- **show-logs.sh**: Shows filtered logs for the app
  ```bash
  ./show-logs.sh [options] [device_id]
  # Options:
  #   -c, --clear    Clear logs before showing
  #   -f, --filter   Specify a custom filter
  #   -a, --all      Show all logs (no filter)
  ```

- **clean-build.sh**: Cleans and rebuilds the project
  ```bash
  ./clean-build.sh [options]
  # Options:
  #   -d, --debug    Build debug APK (default)
  #   -r, --release  Build release APK
  ```

- **test-android-env.sh**: Tests the Android development environment
  ```bash
  ./test-android-env.sh
  ```

## Gradle Optimization

Gradle performance has been optimized with settings in `~/.gradle/gradle.properties`.

## Testing the Environment

To test if your environment is correctly set up, run:

```bash
./test-android-env.sh
```

This script will check:
- Java installation and JAVA_HOME
- Android SDK installation and ANDROID_HOME
- Android SDK components (platform-tools, build-tools, platforms, emulator)
- Gradle installation
- VS Code extensions

## Automated Development Workflow

### One-Command Build and Run

To build and run the app on an emulator with a single command:

```bash
./run-on-emulator.sh
```

To build and run the app on a physical device with a single command:

```bash
./run-on-device.sh
```

### VS Code Tasks

You can also use VS Code tasks for automation:

1. Open the Command Palette (Ctrl+Shift+P or Cmd+Shift+P)
2. Type "Tasks: Run Task"
3. Select one of the following tasks:

#### Build Tasks
- **Build Debug APK**: Builds the debug APK
- **Build Release APK**: Builds the release APK
- **Clean Project**: Cleans the project
- **Clean and Build Debug**: Cleans and builds the debug APK
- **Clean and Build Release**: Cleans and builds the release APK

#### Device Tasks
- **Install on Device**: Installs the debug APK on a connected device
- **List Connected Devices**: Lists all connected devices

#### Emulator Tasks
- **List Emulators**: Lists all available Android emulators
- **Run Emulator**: Launches the specified Android emulator

#### Logging Tasks
- **Show App Logs**: Shows logs filtered for the app
- **Clear Logs**: Clears the log buffer
- **Show All Logs**: Shows all logs without filtering

#### Combined Workflow Tasks
- **Build and Run on Device**: Builds and runs the app on a connected device
- **Build and Run on Emulator**: Builds and runs the app on the default emulator
- **Build and Run on Specific Emulator**: Builds and runs the app on a specified emulator
- **Clean, Build and Run on Device**: Cleans, builds, and runs the app on a device
- **Clean, Build and Run on Emulator**: Cleans, builds, and runs the app on an emulator

### Keyboard Shortcuts

For even faster development, use these keyboard shortcuts:

- **Ctrl+F5**: Build Debug APK
- **Ctrl+Shift+F5**: Clean and Build Debug
- **Ctrl+F6**: Build and Run on Emulator
- **Ctrl+Shift+F6**: Build and Run on Device
- **Ctrl+F7**: Launch App on Emulator (with debugger)
- **Ctrl+Shift+F7**: Launch App on Device (with debugger)
- **Ctrl+L**: Show App Logs
- **Ctrl+Shift+L**: Clear Logs

### Advanced Debugging

For debugging with more options:

1. Start the emulator or connect a physical device
2. Open the Run and Debug view (Ctrl+Shift+D or Cmd+Shift+D)
3. Choose one of the following configurations:
   - **Launch App on Emulator**: Builds and launches the app on an emulator
   - **Launch App on Device**: Builds and launches the app on a connected device
   - **Clean, Build and Launch on Emulator**: Cleans, builds, and launches the app on an emulator
   - **Clean, Build and Launch on Device**: Cleans, builds, and launches the app on a device
   - **Attach to Running App**: Attaches the debugger to an already running app
   - **Start Emulator and Launch App**: Starts the emulator and then launches the app
   - **Clean, Build, Start Emulator and Launch App**: Cleans, builds, starts the emulator, and launches the app
4. Click the green play button or press F5

## Troubleshooting

If you encounter issues:

1. **Environment Variables Not Set**: Run `source ~/.zshrc` to reload your environment variables
2. **ADB Not Found**: Ensure the Android SDK platform-tools are in your PATH
3. **Emulator Not Starting**: Check if the AVD exists using `$ANDROID_HOME/emulator/emulator -list-avds`
4. **Build Failures**: Check Gradle settings and ensure all dependencies are properly configured
5. **VS Code Extensions Not Working**: Restart VS Code after installing extensions
6. **Script Permissions**: Ensure all scripts are executable with `chmod +x *.sh`
7. **Device Not Detected**: Run `adb devices` to check if your device is properly connected

## Additional Resources

- [Android Developer Documentation](https://developer.android.com/docs)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [VS Code Documentation](https://code.visualstudio.com/docs)
- [Android Studio Documentation](https://developer.android.com/studio/intro) (for AVD Manager and SDK Manager)
