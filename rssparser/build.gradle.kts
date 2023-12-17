import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
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

    androidTarget {
        publishAllLibraryVariants()
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    iosArm64()
    iosX64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()

    tasks.withType(KotlinCompile::class).configureEach {
        compilerOptions {
            freeCompilerArgs.addAll("-Xexpect-actual-classes")
        }

        kotlinOptions.freeCompilerArgs += "-Xexpect-actual-classes"
    }

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }

        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
        }

       commonTest.dependencies {
           implementation(kotlin("test"))
           implementation(kotlin("test-common"))
           implementation(kotlin("test-annotations-common"))
           implementation(libs.kotlinx.coroutines.test)
        }


        val commonJvmAndroidMain by creating {
            dependsOn(commonMain.get())

            dependencies {
                api(libs.com.squareup.okhttp3)
            }
        }

        val commonJvmAndroidTest by creating {
            dependsOn(commonTest.get())
        }

        jvmMain.get().dependsOn(commonJvmAndroidMain)
        jvmTest {
            dependsOn(commonJvmAndroidTest)
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

        androidMain {
            dependsOn(commonJvmAndroidMain)
            dependencies {
                implementation(libs.kotlinx.coroutines.android)
            }
        }

        val androidUnitTest by getting {
            dependsOn(commonJvmAndroidTest)

            dependencies {
                implementation(libs.org.robolectric)
                implementation(kotlin("test-junit"))
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
    namespace = "com.prof18.rssparser"

    compileSdk = Integer.parseInt(libs.versions.android.compile.sdk.get())

    defaultConfig {
        minSdk = libs.versions.android.min.sdk.get().toInt()
    }

    testOptions.unitTests {
        isIncludeAndroidResources = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
