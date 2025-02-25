# VS Code Setup for LiftStrong Android Development

This directory contains configuration files for Visual Studio Code to optimize the development experience for the LiftStrong Android app.

## Configuration Files

- **settings.json**: Contains VS Code settings specific to Android and Kotlin development.
- **launch.json**: Defines launch configurations for debugging the app on emulators and physical devices.
- **tasks.json**: Defines tasks for building, installing, and running the app.
- **keybindings.json**: Defines keyboard shortcuts for common Android development tasks.

## Automation Scripts

Several shell scripts have been created to automate the Android development workflow:

- **run-on-emulator.sh**: Builds, installs, and runs the app on an emulator.
- **run-on-device.sh**: Builds, installs, and runs the app on a physical device.
- **show-logs.sh**: Shows filtered logs for the app.
- **clean-build.sh**: Cleans and rebuilds the project.

## Environment Setup

The following environment variables should be set in your shell profile (already added to ~/.zshrc):

```bash
export ANDROID_HOME=$HOME/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/platform-tools
export PATH=$PATH:$ANDROID_HOME/emulator
export PATH=$PATH:$ANDROID_HOME/tools/bin
export JAVA_HOME=/usr/local/opt/openjdk@17
```

## Installed Extensions

The following VS Code extensions have been installed to enhance Android development:

- Android Extension Pack (adelphes.android-dev-ext)
- Kotlin Language (mathiasfrohlich.kotlin)
- XML Tools (dotjoshjohnson.xml)
- Material Icon Theme (pkief.material-icon-theme)
- Path Intellisense (christian-kohler.path-intellisense)
- Gradle Language Support (naco-siren.gradle-language)
- Git Graph (mhutchie.git-graph)

## Available Tasks

You can run these tasks from the VS Code Command Palette (Ctrl+Shift+P or Cmd+Shift+P) by typing "Tasks: Run Task" and selecting one of the following:

### Build Tasks
- **Build Debug APK**: Builds the debug APK using Gradle.
- **Build Release APK**: Builds the release APK using Gradle.
- **Clean Project**: Cleans the project using Gradle.
- **Clean and Build Debug**: Cleans and builds the debug APK.
- **Clean and Build Release**: Cleans and builds the release APK.

### Device Tasks
- **Install on Device**: Installs the debug APK on a connected device.
- **List Connected Devices**: Lists all connected devices.

### Emulator Tasks
- **List Emulators**: Lists all available Android emulators.
- **Run Emulator**: Launches the specified Android emulator.

### Logging Tasks
- **Show App Logs**: Shows logs filtered for the app.
- **Clear Logs**: Clears the log buffer.
- **Show All Logs**: Shows all logs without filtering.

### Combined Workflow Tasks
- **Build and Run on Device**: Builds and runs the app on a connected device.
- **Build and Run on Emulator**: Builds and runs the app on the default emulator.
- **Build and Run on Specific Emulator**: Builds and runs the app on a specified emulator.
- **Clean, Build and Run on Device**: Cleans, builds, and runs the app on a device.
- **Clean, Build and Run on Emulator**: Cleans, builds, and runs the app on an emulator.

## Keyboard Shortcuts

The following keyboard shortcuts have been defined for common tasks:

- **Ctrl+F5**: Build Debug APK
- **Ctrl+Shift+F5**: Clean and Build Debug
- **Ctrl+F6**: Build and Run on Emulator
- **Ctrl+Shift+F6**: Build and Run on Device
- **Ctrl+F7**: Launch App on Emulator (with debugger)
- **Ctrl+Shift+F7**: Launch App on Device (with debugger)
- **Ctrl+L**: Show App Logs
- **Ctrl+Shift+L**: Clear Logs

## Debugging

To debug the app:

1. Start an emulator or connect a physical device.
2. Select the "Run and Debug" view in VS Code (Ctrl+Shift+D or Cmd+Shift+D).
3. Choose one of the following configurations:
   - **Launch App on Emulator**: Builds and launches the app on an emulator.
   - **Launch App on Device**: Builds and launches the app on a connected device.
   - **Clean, Build and Launch on Emulator**: Cleans, builds, and launches the app on an emulator.
   - **Clean, Build and Launch on Device**: Cleans, builds, and launches the app on a device.
   - **Attach to Running App**: Attaches the debugger to an already running app.
   - **Start Emulator and Launch App**: Starts the emulator and then launches the app.
   - **Clean, Build, Start Emulator and Launch App**: Cleans, builds, starts the emulator, and launches the app.
4. Click the green play button or press F5 to start debugging.

## Gradle Optimization

Gradle performance has been optimized with settings in ~/.gradle/gradle.properties.

## Emulator Management

The default emulator is set to "Medium_Phone_API_35". You can change this in the tasks.json file or select a different emulator when prompted.

## Troubleshooting

If you encounter issues:

1. Ensure your environment variables are set correctly by running `source ~/.zshrc`.
2. Verify that Android SDK and JDK are properly installed by running `./test-android-env.sh`.
3. Check that the device or emulator is connected and recognized by ADB with `adb devices`.
4. Restart VS Code if extensions are not working as expected.
5. Check the terminal output for error messages when running tasks.
