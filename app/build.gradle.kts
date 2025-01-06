import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.leo"
    compileSdk = 31

    defaultConfig {
        applicationId = "com.leo"
        minSdk = 21
        targetSdk = 31
        versionCode = 341
        versionName = "3.4.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        //设置apk和aab的文件名
        val buildAppTime = SimpleDateFormat("MMddHHmm", Locale.getDefault()).format(Date())
        setProperty("archivesBaseName", "ShadowLayout_${buildAppTime}_v${versionName}")
    }

    //签名 (需要签名文件)
    signingConfigs {

        create("testKey") {
            storeFile = file("atman.jks")
            storePassword = "5888062"
            keyAlias = "fastMedical"
            keyPassword = "5888062"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("testKey")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }


    flavorDimensions += "default"
    productFlavors {
        create("google")
    }

    productFlavors.all {
        manifestPlaceholders["APP_CHANNEL_VALUE"] = name
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-crashlytics-buildtools:2.9.9")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(project(mapOf("path" to ":shadowLibrary")))
}