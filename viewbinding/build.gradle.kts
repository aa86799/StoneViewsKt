plugins {
    "com.android.library"
    "kotlin-android"
}

android {
    compileSdkVersion 30
    buildToolsVersion "30.0.2"

    defaultConfig {
        minSdkVersion 19
        targetSdkVersion 30
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles "consumer-rules.pro"

        //在要使用ViewBinding的 module 的gradle文件中开启ViewBinding
        //编译器就会为此模块下的每个布局文件都产生一个对应的绑定类
        //tools:viewBindingIgnore="true"  不想为某个布局文件生成binding类
        viewBinding {
            enabled = true
        }
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
    implementation refs.kotlin
    implementation "androidx.core:core-ktx:1.3.2"
    implementation "androidx.appcompat:appcompat:1.2.0"
    implementation "com.google.android.material:material:1.1.0"
}