# Technical Context for LiftStrong

## Technologies Used

### Programming Language
- **Kotlin**: Primary programming language for Android development, offering modern features like null safety, extension functions, and coroutines.

### Android Architecture Components
- **ViewModel**: Manages UI-related data in a lifecycle-conscious way, surviving configuration changes.
- **LiveData**: Observable data holder class that respects Android lifecycle.
- **Room**: Abstraction layer over SQLite for robust database access.
- **Navigation Component**: Handles in-app navigation with a focus on fragment transactions.
- **Data Binding**: Binds UI components in layouts to data sources in the app.
- **Hilt**: Dependency injection library built on top of Dagger, simplifying DI in Android.

### Asynchronous Programming
- **Coroutines**: Kotlin's solution for asynchronous programming, used for database operations, network requests, and other background tasks.
- **Flow**: Built on top of coroutines, used for handling streams of data asynchronously.

### Data Storage
- **Room Persistence Library**: Primary data storage solution, providing an abstraction layer over SQLite.
- **Preferences DataStore**: For storing user preferences and settings.

### UI Components
- **Material Design Components**: For consistent, modern UI elements.
- **ConstraintLayout**: Primary layout for flexible UI design.
- **RecyclerView**: For displaying lists of data efficiently.
- **ViewPager2**: For swipeable views like workout day navigation.

### Charting
- **MPAndroidChart**: Library for creating interactive and visually appealing charts for progress tracking.

## Development Setup

### Required Tools
- **Android Studio**: Primary IDE for Android development.
- **Visual Studio Code**: Secondary IDE with extensions for Android development.
- **JDK 17**: Java Development Kit.
- **Android SDK**: Software Development Kit for Android.
- **Gradle**: Build automation tool.
- **Git**: Version control system.

### VS Code Configuration
- **Extensions**:
  - Android Extension Pack: Comprehensive Android development tools
  - Kotlin Language: Syntax highlighting and IntelliSense for Kotlin
  - XML Tools: For Android layout files
  - Material Icon Theme: Better visualization of Android project files
  - Path Intellisense: Auto-completes filenames
  - Gradle Language Support: For build.gradle files
  - Git Graph: Visualize Git history

- **Project Settings**:
  - Custom settings.json with Android-specific configurations
  - launch.json for debugging on emulators and physical devices
  - tasks.json with build, install, and run tasks
  - Gradle optimization settings

- **Environment Variables**:
  - ANDROID_HOME: Points to Android SDK location
  - JAVA_HOME: Points to JDK 17 installation
  - PATH: Includes Android SDK tools and platform-tools

### Project Structure
The project follows the standard Android project structure with some customizations:

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/liftstrong/
│   │   │   ├── data/           # Data layer (repositories, data sources)
│   │   │   │   ├── local/      # Local data sources (Room database, preferences)
│   │   │   │   ├── model/      # Data models and entities
│   │   │   │   └── repository/ # Repositories
│   │   │   ├── di/            # Dependency injection modules
│   │   │   ├── ui/            # UI layer
│   │   │   │   ├── exercise/   # Exercise-related UI
│   │   │   │   ├── progress/   # Progress-related UI
│   │   │   │   ├── workout/    # Workout-related UI
│   │   │   │   └── settings/   # Settings-related UI
│   │   │   ├── util/          # Utility classes
│   │   │   └── LiftStrongApp.kt # Application class
│   │   ├── res/               # Resources
│   │   └── AndroidManifest.xml
│   └── test/                  # Unit tests
├── build.gradle               # App-level build configuration
└── proguard-rules.pro         # ProGuard rules
```

### Build Configuration
- **Minimum SDK**: API 23 (Android 6.0 Marshmallow)
- **Target SDK**: Latest stable Android SDK
- **Compile SDK**: Latest stable Android SDK
- **Build Tools**: Gradle with Kotlin DSL

## Technical Constraints

1. **Device Compatibility**: The app must work on a wide range of Android devices, from API 23 (Marshmallow) and above.

2. **Performance**: The app must be responsive, with special attention to database operations during workout logging to ensure no lag or data loss.

3. **Battery Usage**: Minimize battery drain, especially during active workout tracking.

4. **Storage**: Keep app size and data storage requirements reasonable.

5. **Offline Functionality**: The app must work fully offline, with all core functionality available without an internet connection.

## Dependencies

### Core Dependencies
```kotlin
// Kotlin
implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"

// Android Architecture Components
implementation "androidx.core:core-ktx:$core_ktx_version"
implementation "androidx.appcompat:appcompat:$appcompat_version"
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
implementation "androidx.navigation:navigation-ui-ktx:$nav_version"
implementation "androidx.room:room-runtime:$room_version"
implementation "androidx.room:room-ktx:$room_version"
kapt "androidx.room:room-compiler:$room_version"
implementation "androidx.datastore:datastore-preferences:$datastore_version"

// UI Components
implementation "com.google.android.material:material:$material_version"
implementation "androidx.constraintlayout:constraintlayout:$constraint_layout_version"
implementation "androidx.recyclerview:recyclerview:$recyclerview_version"
implementation "androidx.viewpager2:viewpager2:$viewpager2_version"

// Dependency Injection
implementation "com.google.dagger:hilt-android:$hilt_version"
kapt "com.google.dagger:hilt-android-compiler:$hilt_version"

// Charting
implementation "com.github.PhilJay:MPAndroidChart:$mpandroidchart_version"
```

### Testing Dependencies
```kotlin
// Unit Testing
testImplementation "junit:junit:$junit_version"
testImplementation "org.mockito:mockito-core:$mockito_version"
testImplementation "androidx.arch.core:core-testing:$arch_testing_version"
testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version"

// UI Testing
androidTestImplementation "androidx.test.ext:junit:$androidx_junit_version"
androidTestImplementation "androidx.test.espresso:espresso-core:$espresso_version"
```

## Development Practices

1. **Version Control**: Git with feature branching workflow.

2. **Code Style**: Follow Kotlin coding conventions and Android style guide.

3. **Testing**: Unit tests for ViewModels and repositories, UI tests for critical user flows.

4. **Documentation**: Code documentation using KDoc, architecture documentation using diagrams and markdown.

5. **Continuous Integration**: GitHub Actions for automated building and testing.

This technical context provides a comprehensive overview of the technologies, setup, constraints, and dependencies for the LiftStrong app, serving as a reference for development decisions and onboarding new team members.
