object ApplicationIdentifier {
    const val id = "com.mercadolibre.app"
    const val idDev = "com.mercadolibre.dev"
    const val idQA = "com.mercadolibre.qa"
}

object Releases {
    const val versionName = "1.0"

    //Master flavor
    const val versionCodeMaster = 1

    //Qa flavor
    const val versionCodeQa = 1

    //Dev flavor
    const val versionCodeDev = 1
}

object Versions {
    const val kotlin = "1.6.10"
    const val hilt = "2.38.1"
    const val gradle = "7.0.2"
    const val coreKtx = "1.8.0"
    const val appcompat = "1.4.2"
    const val lifecycle = "2.4.1"
    const val activityKtx = "1.4.0"

    //Test
    const val junit = "4.13.2"
    const val coreTesting = "2.1.0"
    const val coroutinesTesting = "1.6.1"
    const val mockk = "1.12.2"
    const val junitExt = "1.1.3"
    const val espresso = "3.4.0"
    const val testRunner = "1.4.0"
    const val testRules = "1.4.0"
    const val jacoco = "0.8.8"
    const val sonar = "2.8"

    //Retrofit
    const val retrofit = "2.9.0"
    const val kotlinCoroutine = "1.6.1"
    const val loggingInterceptor = "4.10.0"
    const val okHttp = "4.10.0"

    //Compose
    const val activityComposeVersion = "1.4.0"
    const val composeVersion = "1.1.1"
    const val viewModelComposeVersion = "2.4.1"
    const val navComposeVersion = "2.4.2"
    const val accompanistVersion = "0.23.1"
    const val composeConstraintLayout = "1.0.0"
    const val lottieVersion = "5.0.3"
    const val motionComposeCore = "0.8.4"
    const val coilCompose = "2.1.0"
}

object Libraries {
    // Dagger core
    const val daggerHilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val daggerHiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"

    // RETROFIT
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val httpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
}

object KotlinLibraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val coreKotlinCoroutine =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutine}"
    const val androidKotlinCoroutine =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutine}"
}

object Window {
    const val window = "androidx.window:window:1.0.0"
}

object ComposeLibraries {
    const val activityCompose =
        "androidx.activity:activity-compose:${Versions.activityComposeVersion}"
    const val navigationCompose =
        "androidx.navigation:navigation-compose:${Versions.navComposeVersion}"
    const val materialCompose = "androidx.compose.material:material:${Versions.composeVersion}"
    const val animationCompose = "androidx.compose.animation:animation:${Versions.composeVersion}"
    const val uiToolingCompose = "androidx.compose.ui:ui-tooling:${Versions.composeVersion}"
    const val utilCompose = "androidx.compose.ui:ui-util:${Versions.composeVersion}"
    const val lifecycleVieModelCompose =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.viewModelComposeVersion}"
    const val themeAdapterCompose =
        "androidx.compose.runtime:runtime-livedata:${Versions.composeVersion}"
    const val accompanistTheme =
        "com.google.android.material:compose-theme-adapter:${Versions.composeVersion}"
    const val accompanistInsets =
        "com.google.accompanist:accompanist-insets:${Versions.accompanistVersion}"
    const val accompanistPager =
        "com.google.accompanist:accompanist-pager:${Versions.accompanistVersion}"
    const val accompanistNavigation =
        "com.google.accompanist:accompanist-navigation-material:${Versions.accompanistVersion}"
    const val constraintLayoutCompose =
        "androidx.constraintlayout:constraintlayout-compose:${Versions.composeConstraintLayout}"
    const val motionCompose =
        "io.github.fornewid:material-motion-compose-core:${Versions.motionComposeCore}"
    const val lottieCompose = "com.airbnb.android:lottie-compose:${Versions.lottieVersion}"
    const val coilCompose = "io.coil-kt:coil-compose:${Versions.coilCompose}"

}

object AndroidLibraries {
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtx}"
    const val lifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleRunTime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
}

object TestLibraries {
    const val junit = "junit:junit:${Versions.junit}"
    const val coreTesting = "androidx.arch.core:core-testing:${Versions.coreTesting}"
    const val coroutinesTesting =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTesting}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val junitExt = "androidx.test.ext:junit:${Versions.junitExt}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val espressoIntents = "androidx.test.espresso:espresso-intents:${Versions.espresso}"
    const val espressoWeb = "androidx.test.espresso:espresso-web:${Versions.espresso}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"
    const val testRules = "androidx.test:rules:${Versions.testRules}"
}

object ExcludeCoverage {
    val exclude = mutableSetOf(
        "**/com/mercadolibre/app/di/**/*.*,",
        "**/com/mercadolibre/app/network/**/*.*,",
        "**/com/mercadolibre/app/repository/**/*.*,",
        "**/com/mercadolibre/app/ui/**/*.*,",
        "**/com/mercadolibre/app/utils/**/*.*,",
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
}
