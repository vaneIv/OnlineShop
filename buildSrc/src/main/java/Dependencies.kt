object Versions {
    val activityKtxVersion = "1.7.2"
    val androidXVersion = "1.0.0"
    val androidXLegacySupport = "1.0.0"
    val androidXTestRulesVersion = "1.2.0"
    val androidxHiltCompiler = "1.0.0"
    val androidXAnnotations = "1.6.0"
    val appCompatVersion = "1.6.1"
    val lifecycleVersion = "2.6.1"
    val loadingButtonVersion = "2.2.0"
    val coroutinesVersion = "1.7.1"
    val circularImageVersion = "3.1.0"
    val dexMakerVersion = "2.28.1"
    val fragmentVersion = "1.5.7"
    val firebase = "22.3.0"
    val firebaseBom = "32.6.0"
    val glideVersion = "4.14.2"
    val hiltVersion = "2.44"
    val materialVersion = "1.9.0"
    val navigationVersion = "2.6.0"
    val recyclerViewVersion = "1.3.0"
    val rulesVersion = "1.0.1"
    val stepViewVersion = "1.5.1"
    val kotlinVersion = "1.8.20"
    val viewPagerIndicatorVersion = "1.0.0-alpha.1"
}

object Libs {
    // Kotlin
    val kotlin_std_lib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
    val fragment = "androidx.fragment:fragment-ktx:${Versions.fragmentVersion}"

    // App dependencies
    val app_compat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerViewVersion}"
    val androidx_annotations = "androidx.annotation:annotation:${Versions.androidXAnnotations}"
    val androidx_legacy = "androidx.legacy:legacy-support-v4:${Versions.androidXLegacySupport}"

    // Material Design
    val material_design = "com.google.android.material:material:${Versions.materialVersion}"

    // Architectural Components
    val viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"

    // Lifecycle
    val livedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"
    val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycleVersion}"
    val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleVersion}"
    val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycleVersion}"

    // Activity KTX for viewModels()
    val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtxVersion}"

    // Coroutines
    val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
    val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"

    // Navigation Components
    val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"

    //Dagger - Hilt
    val hilt = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"

    //implementation "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
    val androidxHiltCompiler = "androidx.hilt:hilt-compiler:${Versions.androidxHiltCompiler}"

    // Firebase
    val firebase = "com.google.firebase:firebase-auth:${Versions.firebase}"
    // Firebase Bom
    val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"

    // Glide
    val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"

    // Viewpager2 indicator
    val viewPagerIndicator = "io.github.vejei.viewpagerindicator:viewpagerindicator:${Versions.viewPagerIndicatorVersion}"

    //StepView
    val stepView = "com.shuhart.stepview:stepview:${Versions.stepViewVersion}"

    //Loading button
    val loadingButton = "br.com.simplepass:loading-button-android:${Versions.loadingButtonVersion}"

    //circular image
    val circularImage = "de.hdodenhof:circleimageview:${Versions.circularImageVersion}"
}

object ConfigVersions {
    val compileSdkVersion = 34
    val minSdkVersion = 21
    val targetSdkVersion = 34
}