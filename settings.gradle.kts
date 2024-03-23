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
include(":sampleandroid")
include(":sample")
include(":samplejava")
include(":sample:shared")
include(":sample:androidApp")
include(":sample:desktopApp")
