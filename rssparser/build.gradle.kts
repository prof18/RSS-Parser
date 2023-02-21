plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.com.vanniktech.maven.publish)
}

android {
    namespace = "com.prof.rssparser"

    compileSdk = Integer.parseInt(libs.versions.android.compile.sdk.get())

    defaultConfig {
        minSdk = libs.versions.android.min.sdk.get().toInt()
        targetSdk = Integer.parseInt(libs.versions.android.target.sdk.get())
        buildConfigField(
            "int",
            "VERSION_CODE",
            libs.versions.library.version.code.get()
        )
    }

    buildTypes {
        release {
            isMinifyEnabled =  false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

//    compileOptions {
//        sourceCompatibility = 1.8
//        targetCompatibility = 1.8
//    }

    testOptions.unitTests {
        isIncludeAndroidResources = true
    }

    sourceSets {
        // Adds exported schema location as test app assets.
        getByName("test").assets.srcDir("$projectDir/schemas")
    }
}

dependencies {
    api(libs.com.squareup.okhttp3)
    implementation(libs.org.jetbrains.kotlinx.coroutines.core)
    implementation(libs.org.jetbrains.kotlinx.coroutines.android)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.lifecycle.extensions)

    testImplementation(libs.junit)
    testImplementation(libs.org.robolectric)
    testImplementation(libs.androidx.arch.core.testing)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
    testImplementation(libs.io.mockk)

    testImplementation(libs.androidx.room.testing)
    testImplementation(libs.androidx.test.ext.junit)

    ksp(libs.androidx.room.compiler)
}

ksp {
    arg("room.schemaLocation", "$projectDir/dbschemas".toString())
}