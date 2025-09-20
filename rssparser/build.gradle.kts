@file:OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.com.vanniktech.maven.publish)
    alias(libs.plugins.kotlinx.serialization)
}

tasks.withType(KotlinJvmCompile::class).configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
    }
}

kotlin {
    jvm()

    androidTarget {
        publishLibraryVariants("release", "debug")
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

    listOf(js(), wasmJs()).forEach {
        it.browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    val name = if (it.targetName == "js") "js" else "wasm"
                    useConfigDirectory(rootProject.projectDir.resolve("karma.config.d").resolve(name))
                }

                testLogging {
                    events("passed", "skipped", "failed", "standardOut", "standardError")
                    showStandardStreams = true
                    showStackTraces = true
                    showExceptions = true
                }
            }
        }
    }

    applyDefaultHierarchyTemplate {
        common {
            group("jvmAndroid") {
                withAndroidTarget()
                withJvm()
            }
        }
    }

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

        jvmTest {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

        androidMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.android)
            }
        }

        androidUnitTest {
            dependencies {
                implementation(libs.org.robolectric)
                implementation(kotlin("test-junit"))
            }
        }

        get("jvmAndroidMain").dependencies {
            api(libs.com.squareup.okhttp3)
        }

        get("webMain").dependencies {
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.xmlutil)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.js)
            implementation(libs.ktor.client.core)
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
