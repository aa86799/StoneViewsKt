plugins {
    id("com.android.application")
//    kotlin("kotlin-android")
//    kotlin("android-extensions")
//    kotlin("kapt")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion 30
    buildToolsVersion "30.0.2"

    defaultConfig {
        applicationId "com.stone.camera.one"
        minSdkVersion 19
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation "org.jetbrains.kotlin:kotlin-stdlib:${versions.kotlin}"
    implementation "androidx.core:core-ktx:1.3.2"
    implementation "androidx.appcompat:appcompat:1.2.0"
    implementation "com.google.android.material:material:1.2.1"
    implementation "androidx.constraintlayout:constraintlayout:2.0.4"
    testImplementation "junit:junit:4.+"
    androidTestImplementation "androidx.test.ext:junit:1.1.2"
    androidTestImplementation "androidx.test.espresso:espresso-core:3.3.0"
}