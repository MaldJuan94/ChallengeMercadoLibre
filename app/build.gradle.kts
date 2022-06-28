plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.android")
    id("jacoco")
    id("org.sonarqube")
}

android {
    compileSdk = 31
    buildToolsVersion = "30.0.2"
    defaultConfig {
        minSdk = 23
        targetSdk = 30
        versionCode = 1
        applicationId = ApplicationIdentifier.id
        versionName = Releases.versionName
        versionCode = Releases.versionCodeMaster
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["size"] = "Large"
        testInstrumentationRunnerArguments["clearPackageData"] = "true"
        buildConfigField("String", "SERVER_URL", "\"https://api.mercadolibre.com/\"")
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isTestCoverageEnabled = true
        }
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            applicationIdSuffix = ".release"
            versionNameSuffix = "-release"
            isTestCoverageEnabled = false
        }
    }

    flavorDimensions += "environment"

    productFlavors {
        create("develop") {
            applicationId = ApplicationIdentifier.idDev
            dimension = "environment"
            versionName = Releases.versionName
            versionCode = Releases.versionCodeDev
            buildConfigField("String", "SERVER_URL", "\"https://api.mercadolibre.com/\"")
            manifestPlaceholders["manifestApplicationId"] = "$applicationId"
            signingConfig = signingConfigs.getByName("debug")
        }
        create("qa") {
            applicationId = ApplicationIdentifier.idQA
            dimension = "environment"
            versionName = Releases.versionName
            versionCode = Releases.versionCodeQa
            buildConfigField("String", "SERVER_URL", "\"https://api.mercadolibre.com/\"")
            manifestPlaceholders["manifestApplicationId"] = "$applicationId"
            signingConfig = signingConfigs.getByName("debug")
        }
        create("master") {
            applicationId = ApplicationIdentifier.id
            dimension = "environment"
            versionName = Releases.versionName
            versionCode = Releases.versionCodeMaster
            buildConfigField("String", "SERVER_URL", "\"https://api.mercadolibre.com/\"")
            manifestPlaceholders["manifestApplicationId"] = "$applicationId"
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        dataBinding = true
    }

    testOptions {
        animationsDisabled = true
        unitTests.apply {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }

    lint {
        isAbortOnError = false
        isCheckReleaseBuilds = false
        isIgnoreWarnings = true
        isQuiet = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    packagingOptions {
        resources.excludes.add("META-INF/DEPENDENCIES")
        resources.excludes.add("META-INF/LICENSE")
        resources.excludes.add("META-INF/LICENSE.txt")
        resources.excludes.add("META-INF/license.txt")
        resources.excludes.add("META-INF/NOTICE")
        resources.excludes.add("META-INF/NOTICE.txt")
        resources.excludes.add("META-INF/notice.txt")
        resources.excludes.add("META-INF/licenses/**")
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
        resources.excludes.add("META-INF/*.kotlin_module")
    }

    kapt {
        generateStubs = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }
}

configurations.all {
    resolutionStrategy {
        force(AndroidLibraries.appcompat)
    }
}

dependencies {
//Ktx and lifecycle
    implementation(AndroidLibraries.coreKtx)
    implementation(KotlinLibraries.kotlin)
    implementation(AndroidLibraries.appcompat)
    implementation(AndroidLibraries.lifecycleViewModel)
    implementation(AndroidLibraries.lifecycleRunTime)
    implementation(AndroidLibraries.lifecycleLiveData)

//Compose
    implementation(ComposeLibraries.activityCompose)
    implementation(ComposeLibraries.navigationCompose)
    implementation(ComposeLibraries.materialCompose)
    implementation(ComposeLibraries.animationCompose)
    implementation(ComposeLibraries.uiToolingCompose)
    implementation(ComposeLibraries.utilCompose)
    implementation(ComposeLibraries.lifecycleVieModelCompose)
    implementation(ComposeLibraries.themeAdapterCompose)
    implementation(ComposeLibraries.accompanistPager)
    implementation(ComposeLibraries.accompanistInsets)
    implementation(ComposeLibraries.accompanistNavigation)
    implementation(ComposeLibraries.constraintLayoutCompose)
    implementation(ComposeLibraries.motionCompose)
    implementation(ComposeLibraries.lottieCompose)
    implementation(ComposeLibraries.coilCompose)
    implementation(Window.window)

//Retrofit
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitGsonConverter)
    api(Libraries.httpLoggingInterceptor)
    implementation(Libraries.okhttp)

//Coroutines
    implementation(KotlinLibraries.coreKotlinCoroutine)
    implementation(KotlinLibraries.androidKotlinCoroutine)

//Testing
    testImplementation(TestLibraries.junit)
    testImplementation(TestLibraries.coreTesting)
    testImplementation(TestLibraries.coroutinesTesting)
    testImplementation(TestLibraries.mockk)
    androidTestImplementation(TestLibraries.mockkAndroid)
    androidTestImplementation(TestLibraries.junitExt)
    androidTestImplementation(TestLibraries.espresso)
    androidTestImplementation(TestLibraries.espressoIntents)
    androidTestImplementation(TestLibraries.espressoWeb)
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.testRules)

//Dependence Injection DI
    implementation(Libraries.daggerHilt)
    kapt(Libraries.daggerHiltCompiler)
    implementation(AndroidLibraries.activityKtx)
}


//Sonar and jaCoCo report
fun org.sonarqube.gradle.SonarQubeProperties.add(name: String, valueProvider: () -> String) {
    properties.getOrPut(name) { mutableSetOf<Any>() }
        .also {
            @Suppress("UNCHECKED_CAST")
            (it as MutableCollection<Any>).add(object {
                override fun toString() = valueProvider()
            })
        }
}

configurations.all {
    resolutionStrategy {
        eachDependency {
            if ("org.jacoco" == requested.group) {
                useVersion(Versions.jacoco)
            }
        }
    }
}

tasks.withType<Test> {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

sonarqube {
    properties {
        val projectName = "Mercado Libre"
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.sources", "src/main/java")
        property("sonar.projectKey", projectName.replace(" ", "-"))
        property("sonar.projectName", projectName)
        property("sonar.projectVersion", project.version.toString())
        property("sonar.projectDescription", "A demo MercadoLibre simple app")
        property("sonar.organization", "MercadoLibre")
        property("sonar.tests", mutableSetOf("src/test/java", "src/androidTest/java"))
        property("sonar.test.inclusions", "**/*Test*/**")
        property("sonar.java.coveragePlugin", "jacoco")
        property(
            "sonar.android.lint.report",
            "${project.buildDir}/reports/lint-results-stagingDebug.xml"
        )

        //Credentials for login sonar
        property("sonar.login", "admin")
        property("sonar.password", "123456")

        property(
            "sonar.exclusions", "**/*Test*/**," + "*.json," +
                    "**/*test*/**," +
                    "**/.gradle/**," +
                    "**/R.class"
        )

        property("sonar.coverage.exclusions", ExcludeCoverage.exclude)
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "build/reports/jacoco/testDevelopDebugUnitTestCoverage/testDevelopDebugUnitTestCoverage.xml"
        )
        property("sonar.junit.reportPaths", "build/test-results/testDevelopDebugUnitTest")
        property("sonar.links.scm", "https://github.com/MaldJuan94/ChallengeMercadoLibre")
    }
}

android {
    applicationVariants.all {
        val variantName = "${this.flavorName.capitalize()}${this.buildType.name.capitalize()}"
        val testTaskName = "test${variantName}UnitTest"
        val uiTestCoverageTaskName = "create${variantName}CoverageReport"
        task<JacocoReport>("${testTaskName}Coverage") {
            dependsOn(testTaskName, uiTestCoverageTaskName)
            group = "Reporting"
            description =
                "Generate Jacoco coverage reports for the $variantName build"
            reports {
                xml.isEnabled = true
                html.isEnabled = true
            }
            val javaClasses = fileTree(this@all.javaCompileProvider.get().destinationDir) {
                exclude(ExcludeCoverage.exclude)
            }
            val kotlinClasses = fileTree("${buildDir}/tmp/kotlin-classes/${variantName}") {
                exclude(ExcludeCoverage.exclude)
            }
            classDirectories.setFrom(javaClasses, kotlinClasses)

            sourceDirectories.setFrom(
                fileTree("$buildDir/src/main/java"),
                fileTree("$buildDir/src/${variantName}/java"),
                fileTree("$buildDir/src/main/kotlin"),
                fileTree("$buildDir/src/${variantName}/kotlin")
            )

            val uiTestsData =
                fileTree("${buildDir}/outputs/code_coverage/${variantName}AndroidTest/connected/") {
                    include("**/*.ec")
                }

            val unitTestsData =
                fileTree("${buildDir}/outputs/unit_test_code_coverage/${variantName}UnitTest/") {
                    include("**/*.exec")
                }

            executionData.setFrom(
                fileTree("$buildDir/jacoco/${testTaskName}.exec"),
                unitTestsData,
                uiTestsData
            )
        }
    }
}
