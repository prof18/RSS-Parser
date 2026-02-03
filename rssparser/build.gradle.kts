@file:OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalWasmDsl::class)

import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
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

    androidLibrary {
        namespace = "com.prof18.rssparser"
        compileSdk = Integer.parseInt(libs.versions.android.compile.sdk.get())
        minSdk = libs.versions.android.min.sdk.get().toInt()
        compilerOptions.jvmTarget.set(JvmTarget.JVM_1_8)

        withHostTest {
            isIncludeAndroidResources = true
        }
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

        val jvmAndroidMain by creating {
            dependsOn(commonMain.get())
        }

        val jvmAndroidTest by creating {
            dependsOn(commonTest.get())
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

        jvmMain {
            dependsOn(jvmAndroidMain)
        }

        jvmTest {
            dependsOn(jvmAndroidTest)
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

        androidMain {
            dependsOn(jvmAndroidMain)
            dependencies {
                implementation(libs.kotlinx.coroutines.android)
            }
        }

        getByName("androidHostTest") {
            dependsOn(jvmAndroidTest)
            dependencies {
                implementation(libs.org.robolectric)
                implementation(kotlin("test-junit"))
            }
        }

        jvmAndroidMain.dependencies {
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

