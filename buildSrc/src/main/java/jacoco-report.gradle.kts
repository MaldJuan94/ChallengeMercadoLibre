// Put it at root/buildSrc/src/main/kotlin
@file:Suppress("UnstableApiUsage")

plugins {
    jacoco
}

tasks.withType<Test> {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

val flavors = mutableSetOf(
    "Develop",
    "Qa",
    "Master"
)

val fileFilter = mutableSetOf(
    "**/R\$*.class",
    "**/*\$inlined$*.*",

    // data binding
    "android/databinding/**/*.class",
    "**/android/databinding/*Binding.class",
    "**/android/databinding/*",
    "**/androidx/databinding/*",
    "**/BR.*",
    // android
    "**/R.class",
    "**/R$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "**/*Test*.*",
    "android/**/*.*",
    // butterKnife
    "**/*\$ViewInjector*.*",
    "**/*\$ViewBinder*.*",
    // dagger
    "**/*_MembersInjector.class",
    "**/Dagger*Component.class",
    "**/Dagger*Component\$Builder.class",
    "**/*Module_*Factory.class",
    "**/di/module/*",
    "**/*_Factory*.*",
    "**/*Module*.*",
    "**/*Dagger*.*",
    "**/*Hilt*.*",
    // kotlin
    "**/*MapperImpl*.*",
    "**/*\$ViewInjector*.*",
    "**/*\$ViewBinder*.*",
    "**/BuildConfig.*",
    "**/*Component*.*",
    "**/*BR*.*",
    "**/Manifest*.*",
    "**/*\$Lambda$*.*",
    "**/*Companion*.*",
    "**/*Module*.*",
    "**/*Dagger*.*",
    "**/*Hilt*.*",
    "**/*MembersInjector*.*",
    "**/*_MembersInjector.class",
    "**/*_Factory*.*",
    "**/*_Provide*Factory*.*",
    "**/*Extensions*.*",
    // sealed and data classes
    "**/*$Result.*",
    "**/*$Result$*.*"
)

val includesClassDirectories = mutableSetOf(
    "**/classes/**/main/**",
    "**/intermediates/classes/debug/**",
    "**/intermediates/javac/debug/*/classes/**", // Android Gradle Plugin 3.2.x support.
    "**/tmp/kotlin-classes/debug/**"
)

val includeSourceDirectories = mutableSetOf(
    "src/main/java/**",
    "src/main/kotlin/**",
    "src/debug/java/**",
    "src/debug/kotlin/**"
)

val includeExecutionData = mutableSetOf(
    "outputs/code_coverage/**/*.ec",
    "jacoco/jacocoTestReportDebug.exec",
    "jacoco/testDebugUnitTest.exec",
    "jacoco/test.exec"
)

val setClassDirectories = fileTree(project.buildDir) {
    include(includesClassDirectories)
    exclude(fileFilter)
}

val setSourceDirectories = fileTree("${project.buildDir}") {
    include(includeSourceDirectories)
}

val setExecutionData = fileTree(project.buildDir) {
    include(includeExecutionData)
}

flavors.forEach { flavorName->
    task<JacocoReport>("merged${flavorName}JacocoReport") {
        dependsOn("test${flavorName}DebugUnitTest", "create${flavorName}DebugCoverageReport")
        reports {
            xml
            html
            require(true)
        }
        classDirectories.setFrom(setClassDirectories)
        sourceDirectories.setFrom(setSourceDirectories)
        executionData.setFrom(setExecutionData)
    }
}

