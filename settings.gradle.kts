pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://dl.bintray.com/kotlin/")
        }
    }
}

include(":rssparser")
include(":samples:android")
include(":samples:multiplatform")
include(":samples:java")
include(":samples:multiplatform:shared")
include(":samples:multiplatform:androidApp")
include(":samples:multiplatform:desktopApp")
