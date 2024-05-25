import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.prof"
version = "1.0-SNAPSHOT"


kotlin {
    jvm {
        jvmToolchain(17)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
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
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "sample"
            packageVersion = "1.0.0"
        }
    }
}
