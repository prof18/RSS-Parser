plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
//    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.com.vanniktech.maven.publish)
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    android {
        publishAllLibraryVariants()
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    ios()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(libs.org.jetbrains.kotlinx.coroutines.test)
            }
        }

        val commonJvmAndroidMain by creating {
            dependsOn(commonMain)

            dependencies {
                api(libs.com.squareup.okhttp3)
            }
        }

        val commonJvmAndroidTest by creating {
            dependsOn(commonTest)
        }

        val jvmMain by getting {
            dependsOn(commonJvmAndroidMain)

            dependencies {
            }
        }
        val jvmTest by getting {
            dependsOn(commonJvmAndroidTest)
            dependencies {
                implementation(libs.junit)
                implementation(kotlin("test-junit"))
            }
        }
        val androidMain by getting {
            dependsOn(commonJvmAndroidMain)
            dependencies {
                implementation(libs.kotlinx.coroutines.android)
            }
        }
        val androidTest by getting {
            dependsOn(commonJvmAndroidTest)

            dependencies {
                implementation(libs.org.robolectric)
                implementation(libs.junit)
//                implementation(kotlin("test-common"))
//                implementation(kotlin("test-annotations-common"))

            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by getting {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)

            dependencies {
            }
        }

    }
}

tasks.register<Copy>("copyIosTestResourcesArm64") {
    from("src/commonTest/resources")
    into("build/bin/iosSimulatorArm64/debugTest/resources")
}

tasks.register<Copy>("copyIosTestResourcesX64") {
    from("src/commonTest/resources")
    into("build/bin/iosX64/debugTest/resources")
}

tasks.findByName("iosX64Test")?.dependsOn("copyIosTestResourcesX64")
tasks.findByName("iosSimulatorArm64Test")?.dependsOn("copyIosTestResourcesArm64")

android {
    namespace = "com.prof.rssparser"

    compileSdk = Integer.parseInt(libs.versions.android.compile.sdk.get())

    defaultConfig {
        minSdk = libs.versions.android.min.sdk.get().toInt()
        targetSdk = Integer.parseInt(libs.versions.android.target.sdk.get())

        // TODO: necessary?
        buildConfigField(
            "int",
            "VERSION_CODE",
            libs.versions.library.version.code.get()
        )
    }

    // TODO: necessary?
//    buildTypes {
//        release {
//            isMinifyEnabled =  false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }

//    compileOptions {
//        sourceCompatibility = 1.8
//        targetCompatibility = 1.8
//    }

    testOptions.unitTests {
        isIncludeAndroidResources = true
    }
//
//    sourceSets {
//        // Adds exported schema location as test app assets.
//        getByName("test").assets.srcDir("$projectDir/schemas")
//    }
}

//dependencies {
//    api(libs.com.squareup.okhttp3)
//    implementation(libs.org.jetbrains.kotlinx.coroutines.core)
//    implementation(libs.org.jetbrains.kotlinx.coroutines.android)
//
//    // Room
//    implementation(libs.androidx.room.runtime)
//    implementation(libs.androidx.room.ktx)
//    implementation(libs.androidx.lifecycle.extensions)
//
//    testImplementation(libs.junit)
//    testImplementation(libs.org.robolectric)
//    testImplementation(libs.androidx.arch.core.testing)
//    testImplementation(libs.androidx.test.core)
//    testImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
//    testImplementation(libs.io.mockk)
//
//    testImplementation(libs.androidx.room.testing)
//    testImplementation(libs.androidx.test.ext.junit)
//
//    ksp(libs.androidx.room.compiler)
//}

//ksp {
//    arg("room.schemaLocation", "$projectDir/dbschemas".toString())
//}