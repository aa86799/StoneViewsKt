import java.net.URI

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    apply("configs.gradle.kts")

    repositories {
        maven("https://maven.aliyun.com/repository/public")
        google()
    }

    val isBuildDokit = false
    val kotlin: String by extra
    val kotlinVersion = extra.get("kotlin").toString()
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts.kts.kts.kts files

        classpath( "com.google.gms:google-services:4.3.3")

        if (isBuildDokit) {
            classpath( "com.didichuxing.doraemonkit:dokitx-plugin:3.3.5")
        }
    }
}

allprojects {
    repositories {
        maven {  url = URI("https://maven.aliyun.com/repository/public") }
        google()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
