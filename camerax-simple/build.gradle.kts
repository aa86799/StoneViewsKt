plugins {
    "com.android.application"
    "kotlin-android"
    "kotlin-android-extensions"
}
android {
    compileSdkVersion 30
    buildToolsVersion "29.0.3"

    defaultConfig {
        applicationId "com.stone.camerax"
        minSdkVersion 21
        targetSdkVersion 30
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation fileTree(dir: "libs", include: ["*.jar"])
    implementation refs.kotlin
    implementation "androidx.appcompat:appcompat:1.1.0"
    implementation "androidx.core:core-ktx:1.2.0"
    implementation "androidx.constraintlayout:constraintlayout:1.1.3"
    testImplementation "junit:junit:4.13"
    androidTestImplementation "androidx.test.ext:junit:1.1.1"
    androidTestImplementation "androidx.test.espresso:espresso-core:3.2.0"

    implementation "androidx.camera:camera-core:1.0.0-beta03"
    implementation "androidx.camera:camera-camera2:1.0.0-beta03"
    implementation "androidx.camera:camera-lifecycle:1.0.0-beta03"
    implementation "androidx.camera:camera-view:1.0.0-alpha10"
    implementation "androidx.camera:camera-extensions:1.0.0-alpha10"
}
