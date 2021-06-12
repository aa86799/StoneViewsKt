plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

val isBuildDokit = false
if (isBuildDokit) {
    apply(plugin = "com.didi.dokit")
}

val builds: Map<String, Any> by extra

android {
    compileSdkVersion = builds["compileSdkVersion"].toString()

    defaultConfig {
        applicationId = "com.stone.stoneviewskt"
        minSdkVersion(builds.minSdkVersion)
        targetSdkVersion = builds.targetSdkVersion
        versionCode = builds.versionCode
        versionName = builds.versionName

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"

        externalNativeBuild {
            cmake {
                cppFlags "-std=c++11 -frtti -fexceptions"
            }

        }

        ndk {
            //设置支持的SO库架构
            abiFilters /*"armeabi" ,*/ "armeabi-v7a" , "arm64-v8a", "x86", "x86_64"
        }
    }

    //specify specific version. "major.minor.build" 要求 在 sdk/ndk/ 目录下的
    ndkVersion "20.0.5594570"

    externalNativeBuild {
        cmake {
            path "src/main/cpp/CMakeLists.txt"
            version "3.10.2"
        }
    }

    signingConfigs { //gradle assembleRelease
        myConfig {
            storeFile file("stone.keystore")
            storePassword "482935"
            keyAlias "stone"
            keyPassword "482935"
            v1SigningEnabled true
            v2SigningEnabled true
        }
    }

    buildTypes {
        release {
            multiDexEnabled true
            minifyEnabled true
            zipAlignEnabled true
            signingConfig signingConfigs.myConfig
            proguardFiles getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
        }

        debug {
            multiDexEnabled true
            minifyEnabled false
            zipAlignEnabled true
            signingConfig signingConfigs.myConfig
        }
    }

    compileOptions {
        sourceCompatibility 1.8
        targetCompatibility 1.8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    androidExtensions {//plugin: "kotlin-android-extensions"
        defaultCacheImplementation = "SPARSE_ARRAY" //default HASH_MAP
    }
}

dependencies {
    implementation(fileTree(org.gradle.internal.impldep.bsh.commands.dir: "libs", include: ["*.jar"]))
    implementation(project(":viewbinding")
    implementation(refs.kotlin)
    implementation(refs.androidxCompat)
    implementation(refs.ktx)
    implementation(refs.constraintLayout)
    implementation(refs.recyclerviewX)
    implementation(refs.anko)
    implementation(refs.kotlinCoroutineAndroid)
    implementation(refs.kotlinCoroutineCore)
    implementation(refs.androidxLifecycle)
    implementation(refs.material)
    implementation(refs.mmkv)
    implementation(refs.multidex)
    implementation(refs.fragmentationx)
    implementation(refs.permissionsDispatcher)
    kapt(refs.permissionsDispatcherCompiler)
    implementation(refs.roomRuntime)
    implementation(refs.roomKtx)
    kapt(refs.roomCompiler)
    implementation("androidx.datastore:datastore-preferences:1.0.0-alpha05")

    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    if (isBuildDokit.toBoolean()) {
        //http://xingyun.xiaojukeji.com/docs/dokit/#/androidGuide  滴滴开发助手
        debugImplementation("com.didichuxing.doraemonkit:dokitx:3.3.5")
        releaseImplementation("com.didichuxing.doraemonkit:dokitx-no-op:3.3.5")
    }
}

if (isBuildDokit.toBoolean()) {
    dokitExt {
        //通用设置
        comm {
            //地图经纬度开关
            gpsSwitch true
            //网络开关
            networkSwitch true
            //大图开关
            bigImgSwitch true
            //webView js 抓包
            webViewSwitch true
        }

        slowMethod {
            //调用栈模式配置
            stackMethod {
                //默认值为 5ms 小于该值的函数在调用栈中不显示
                thresholdTime 10
                //调用栈函数入口
                enterMethods = ["com.didichuxing.doraemondemo.MainDebugActivity.test1"]
                //黑名单 粒度最小到类 暂不支持到方法
                methodBlacklist = ["com.facebook.drawee.backends.pipeline.Fresco"]
            }
            //普通模式配置
            normalMethod {
                //默认值为 500ms 小于该值的函数在运行时不会在控制台中被打印
                thresholdTime 500
                //需要针对函数插装的包名
                packageNames = ["com.didichuxing.doraemondemo"]
                //不需要针对函数插装的包名&类名
                methodBlacklist = ["com.didichuxing.doraemondemo.dokit"]
            }
        }
    }
}
