import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeTest

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.binary.compatibility.validator)
}


apiValidation {
    ignoredProjects.addAll(
        listOf(
            "multiplatform",
            "shared",
            "androidApp",
            "desktopApp",
            "android",
            "java"
        )
    )
}

allprojects {
    val rootDir = "${rootProject.rootDir.path}/rssparser/src/commonTest/resources"

    tasks.withType<Test>().configureEach {
        environment("TEST_RESOURCES_ROOT", rootDir)
    }

    tasks.withType<KotlinNativeTest>().configureEach {
        // https://stackoverflow.com/questions/34618580/xcrun-is-there-a-way-to-set-environment-variable-via-xcrun-simctl-cli/53604237#53604237
        environment("SIMCTL_CHILD_TEST_RESOURCES_ROOT", rootDir)
    }
}
