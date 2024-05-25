import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    alias(libs.plugins.compose.compiler)
}

group = "com.prof"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(17)

    jvm {
        withJava()
    }
    sourceSets {
         jvmMain {
            dependencies {
                implementation(project(":samples:multiplatform:shared"))
                implementation(compose.desktop.currentOs)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.prof18.rssparser.sample.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg)
            packageName = "sample"
            packageVersion = "1.0.0"
        }
    }
}
