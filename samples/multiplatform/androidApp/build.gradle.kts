plugins {
    id("com.android.application")
    kotlin("android")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.prof18.rssparser.sample.android"
    compileSdk = Integer.parseInt(libs.versions.android.compile.sdk.get())
    defaultConfig {
        applicationId = "com.prof18.rssparser.sample.android"
        minSdk = libs.versions.android.min.sdk.get().toInt()
        targetSdk = Integer.parseInt(libs.versions.android.compile.sdk.get())

        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}


dependencies {
    implementation(project(":samples:multiplatform:shared"))
    implementation(libs.androidx.appcompat)

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)

    implementation(libs.bundles.compose)
}