import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.com.vanniktech.maven.publish)
}

tasks.withType(KotlinJvmCompile::class).configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
    }
}

kotlin {
    jvm()

    androidTarget {
        publishAllLibraryVariants()
    }

    iosArm64()
    iosX64()
    iosSimulatorArm64()
    macosArm64()
    macosX64()
    tvosArm64()
    tvosSimulatorArm64()
    tvosX64()

    // From S8 processor, only full 64-bit processors
    watchosDeviceArm64()
    watchosSimulatorArm64()
    watchosX64()

    applyDefaultHierarchyTemplate()

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    explicitApi()

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
