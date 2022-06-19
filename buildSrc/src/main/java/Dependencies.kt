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

object Modules {
    const val app = ":app"
    const val common = ":common"
    const val domain = ":domain"
}

object Versions {
    const val kotlin = "1.6.21"
    const val hilt = "2.37"
    const val lottie = "5.0.3"
    const val shimmer  = "0.5.0"
    const val autoFitText = "0.2.1"
    const val navSafeArgs = "2.4.2"
    const val gradle = "7.0.4"
    const val activityKtx = "1.4.0"
    const val fragmentKtx = "1.4.1"

    //Test
    const val junit = "4.13.2"
    const val coreTesting = "2.1.0"
    const val coroutinesTesting = "1.6.1"
    const val mockk = "1.12.2"
    const val junitExt = "1.1.3"
    const val espresso = "3.4.0"
    const val testRunner = "1.4.0"
    const val testRules = "1.4.0"

    const val okHttp = "4.10.0"
    const val retrofit = "2.9.0"
    const val retrofitMoshi = "2.6.2"
    const val loggingInterceptor = "4.10.0"
    const val moshiVersion = "1.11.0"
    const val kotlinCoroutine = "1.6.1"
    const val coreKtx = "1.8.0"

    const val appcompat = "1.4.2"
    const val lifecycle = "2.4.1"
    const val recyclerview = "1.2.1"
    const val constraintLayout = "2.1.4"
    const val navigationKtx = "2.4.2"
    const val lifecycleExtensions = "2.2.0"
    const val dataBinding = "3.5.0"
    const val material = "1.6.1"
    const val glide = "4.11.0"
}

object Libraries {
    // Dagger core
    const val daggerHilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val daggerHiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"

    // RETROFIT
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofitMoshi}"
    const val httpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"

    // MOSHI
    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshiVersion}"
    const val moshiKotlinCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshiVersion}"

   //Material
   const val material = "com.google.android.material:material:${Versions.material}"

    //Glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    //Other
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
    const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmer}"
    const val autoFitText = "me.grantland:autofittextview:${Versions.autoFitText}"
}

object KotlinLibraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val coreKotlinCoroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutine}"
    const val androidKotlinCoroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutine}"
}

object AndroidLibraries {
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtx}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"

    const val dataBinding = "com.android.databinding:compiler:${Versions.dataBinding}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleRunTime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationKtx}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigationKtx}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensions}"
}

object TestLibraries {
    const val junit = "junit:junit:${Versions.junit}"
    const val coreTesting = "androidx.arch.core:core-testing:${Versions.coreTesting}"
    const val coroutinesTesting = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTesting}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val junitExt = "androidx.test.ext:junit:${Versions.junitExt}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"
    const val testRules = "androidx.test:rules:${Versions.testRules}"
}
