#!/bin/bash

# Script to fix the LiftStrong project issues
# This script will:
# 1. Update the project's build.gradle files to ensure compatibility with Kotlin and Room
# 2. Fix the Kotlin version and Room implementation

# Source .zshrc to ensure environment variables are set
source ~/.zshrc

# Set colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}=== LiftStrong Project Fix Script ===${NC}"

# Step 1: Check if the project's root build.gradle exists
if [ ! -f "build.gradle" ]; then
    echo -e "${RED}Error: build.gradle not found in the current directory.${NC}"
    exit 1
fi

# Step 2: Create a backup of the build.gradle files
echo -e "${YELLOW}Creating backups of build.gradle files...${NC}"
cp build.gradle build.gradle.bak
cp app/build.gradle app/build.gradle.bak
echo -e "${GREEN}Backups created.${NC}"

# Step 3: Update the settings.gradle file
echo -e "${YELLOW}Updating settings.gradle file...${NC}"
cp settings.gradle settings.gradle.bak
cat > settings.gradle << 'EOL'
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}

rootProject.name = "LiftStrong"
include ':app'
EOL
echo -e "${GREEN}Settings.gradle updated.${NC}"

# Step 4: Update the root build.gradle file
echo -e "${YELLOW}Updating root build.gradle file...${NC}"
cat > build.gradle << 'EOL'
// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    ext {
        kotlin_version = '1.9.22'
        hilt_version = '2.48.1'
        room_version = '2.6.1'
        nav_version = '2.7.6'
        lifecycle_version = '2.7.0'
    }
    
    repositories {
        google()
        mavenCentral()
    }
    
    dependencies {
        classpath 'com.android.tools.build:gradle:8.2.2'
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        classpath "androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version"
        classpath "com.google.dagger:hilt-android-gradle-plugin:$hilt_version"
    }
}

// Clean task
tasks.register('clean', Delete) {
    delete rootProject.buildDir
}
EOL
echo -e "${GREEN}Root build.gradle updated.${NC}"

# Step 4: Update the app/build.gradle file
echo -e "${YELLOW}Updating app/build.gradle file...${NC}"
cat > app/build.gradle << 'EOL'
plugins {
    id 'com.android.application'
    id 'kotlin-android'
    id 'kotlin-kapt'
    id 'dagger.hilt.android.plugin'
    id 'androidx.navigation.safeargs.kotlin'
}

android {
    compileSdk 35

    defaultConfig {
        applicationId "com.liftstrong"
        minSdk 23
        targetSdk 35
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += [
                    "room.schemaLocation": "$projectDir/schemas",
                    "room.incremental": "true",
                    "room.expandProjection": "true"
                ]
            }
        }
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }
    
    kotlinOptions {
        jvmTarget = '17'
    }
    
    buildFeatures {
        viewBinding true
        dataBinding true
    }
    
    namespace 'com.liftstrong'
}

dependencies {
    // Kotlin
    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3"

    // Android Core
    implementation 'androidx.core:core-ktx:1.12.0'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.11.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'androidx.recyclerview:recyclerview:1.3.2'
    implementation 'androidx.viewpager2:viewpager2:1.0.0'

    // Architecture Components
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version"
    implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
    implementation "androidx.navigation:navigation-ui-ktx:$nav_version"

    // Room
    implementation "androidx.room:room-runtime:$room_version"
    implementation "androidx.room:room-ktx:$room_version"
    kapt "androidx.room:room-compiler:$room_version"

    // Hilt
    implementation "com.google.dagger:hilt-android:$hilt_version"
    kapt "com.google.dagger:hilt-android-compiler:$hilt_version"

    // DataStore
    implementation "androidx.datastore:datastore-preferences:1.0.0"

    // MPAndroidChart for progress tracking
    implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'

    // Testing
    testImplementation 'junit:junit:4.13.2'
    testImplementation "androidx.arch.core:core-testing:2.2.0"
    testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3"
    testImplementation "org.mockito:mockito-core:5.8.0"
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
}

// Allow references to generated code
kapt {
    correctErrorTypes true
}
EOL
echo -e "${GREEN}app/build.gradle updated.${NC}"

# Step 5: Update the gradle.properties file
echo -e "${YELLOW}Updating project-level gradle.properties file...${NC}"
cp gradle.properties gradle.properties.bak
cat > gradle.properties << 'EOL'
# Project-wide Gradle settings.
# IDE (e.g. Android Studio) users:
# Gradle settings configured through the IDE *will override*
# any settings specified in this file.
# For more details on how to configure your build environment visit
# http://www.gradle.org/docs/current/userguide/build_environment.html
# Specifies the JVM arguments used for the daemon process.
# The setting is particularly useful for tweaking memory settings.
org.gradle.jvmargs=-Xmx4g -XX:+HeapDumpOnOutOfMemoryError

# When configured, Gradle will run in incubating parallel mode.
# This option should only be used with decoupled projects. More details, visit
# http://www.gradle.org/docs/current/userguide/multi_project_builds.html#sec:decoupled_projects
org.gradle.parallel=true
org.gradle.configureondemand=true
org.gradle.caching=true

# AndroidX package structure to make it clearer which packages are bundled with the
# Android operating system, and which are packaged with your app's APK
# https://developer.android.com/topic/libraries/support-library/androidx-rn
android.useAndroidX=true
# Automatically convert third-party libraries to use AndroidX
android.enableJetifier=true
# Kotlin code style for this project: "official" or "obsolete":
kotlin.code.style=official
EOL
echo -e "${GREEN}gradle.properties updated.${NC}"

echo -e "${BLUE}=== Fix Complete ===${NC}"
echo -e "${YELLOW}Next steps:${NC}"
echo -e "1. Run './gradlew clean' to clean the project"
echo -e "2. Run './gradlew build' to build the project"
echo -e "3. If there are still issues with Room DAOs, you may need to update the DAO implementations"
echo -e "${GREEN}Script execution completed.${NC}"
