plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.onlineshop"
    compileSdk = ConfigVersions.compileSdkVersion

    defaultConfig {
        applicationId = "com.example.onlineshop"
        minSdk = ConfigVersions.minSdkVersion
        targetSdk = ConfigVersions.targetSdkVersion
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packaging {
        resources.excludes.addAll(
            listOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
                "META-INF/gradle/incremental.annotation.processors"
                //"/META-INF/{AL2.0,LGPL2.1}"
            )
        )
    }
}

dependencies {

    implementation(Libs.kotlin_std_lib)
    implementation(Libs.fragment)
    implementation(Libs.app_compat)
    implementation(Libs.recyclerview)
    //implementation(Libs.androidx_annotations)
    implementation(Libs.androidx_legacy)

    // Material design
    implementation(Libs.material_design)

    // Architectural components
    implementation(Libs.viewmodelKtx)
    implementation(Libs.livedataKtx)
    implementation(Libs.lifecycleRuntime)
    implementation(Libs.runtimeKtx)
    implementation(Libs.lifecycleCompiler)

    // Activity Ktx for viewModels()
    implementation(Libs.activityKtx)

    // Coroutines
    implementation(Libs.coroutines)
    implementation(Libs.coroutinesCore)

    // Navigation components
    implementation(Libs.navigationFragmentKtx)
    implementation(Libs.navigationUiKtx)

    // Dagger - Hilt
    implementation(Libs.hilt)
    implementation(Libs.hiltCompiler)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    kapt(Libs.androidxHiltCompiler)

    //Firebase
    implementation(Libs.firebase)
    // Firebase BoM
    implementation(Libs.firebaseBom)

    // Glide
    implementation(Libs.glide)
    kapt(Libs.glideCompiler)

    //Viewpager2 indicator
    implementation(Libs.viewPagerIndicator)

    //StepView
    implementation(Libs.stepView)

    // LoadingButton
    implementation(Libs.loadingButton)

    // Circular image
    implementation(Libs.circularImage)
}
// Non-existent type correction. Needed for Hilt.
kapt {
    correctErrorTypes = true
}