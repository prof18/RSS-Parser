plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    android()

    jvm("desktop") {
        jvmToolchain(11)
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotlinx.coroutines.test)
            }
        }

        val androidMain by getting {
            dependsOn(commonMain)

            dependencies {
            }
        }
        val androidTest by getting {
            dependsOn(commonTest)

            dependencies {
                implementation(libs.junit)
                implementation(libs.org.robolectric)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
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
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)

            dependencies {
            }
        }


        val desktopMain by getting {
            dependsOn(commonMain)

            dependencies {
//                api(compose.preview)
            }
        }
        val desktopTest by getting {
            dependsOn(commonTest)

        }
    }


}

configurations.all {
    // Exclude native compiler
    exclude("org.jetbrains.compose.compiler", "compiler-hosted")
}

android {
    namespace = "com.prof18.rssparser.sample"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}
