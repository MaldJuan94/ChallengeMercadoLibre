plugins {
    id("com.android.application")
    id("kotlin-parcelize")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.android")
}

android {

    compileSdk = 32
    buildToolsVersion = "32.0.0"
    defaultConfig {
        minSdk = 23
        targetSdk = 32
        versionCode = 1
        versionName = Releases.versionName
        versionCode = Releases.versionCodeMaster
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["size"] = "Large"
        buildConfigField("String", "SERVER_URL", "\"https://api.mercadolibre.com/\"")
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            applicationIdSuffix = ".release"
            versionNameSuffix = "-release"
        }
    }

    flavorDimensions += "environment"

    productFlavors {
        create("develop") {
            dimension = "environment"
            versionName = Releases.versionName
            versionCode = Releases.versionCodeDev
            buildConfigField("String", "SERVER_URL", "\"https://api.mercadolibre.com/\"")
            manifestPlaceholders["manifestApplicationId"] = "$applicationId"
            signingConfig = signingConfigs.getByName("debug")
        }
        create("qa") {
            dimension = "environment"
            versionName = Releases.versionName
            versionCode = Releases.versionCodeQa
            buildConfigField("String", "SERVER_URL", "\"https://api.mercadolibre.com/\"")
            manifestPlaceholders["manifestApplicationId"] = "$applicationId"
            signingConfig = signingConfigs.getByName("debug")
        }
        create("master") {
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
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        unitTests.isIncludeAndroidResources = true
    }

    lint {
        isCheckReleaseBuilds = false
        isAbortOnError = false
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
        kotlinCompilerExtensionVersion = Versions.composeVersion
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
    implementation(ComposeLibraries.lifecycleVieModelCompose)
    implementation(ComposeLibraries.themeAdapterCompose)
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
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.testRules)

    //Dependence Injection DI
    implementation(Libraries.daggerHilt)
    kapt(Libraries.daggerHiltCompiler)
    implementation(AndroidLibraries.activityKtx)

}