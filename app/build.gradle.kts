plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "app.shoes.sneakers"
    compileSdk = 33

    defaultConfig {
        applicationId = "app.shoes.sneakers"
        minSdk = 23
        targetSdk = 33
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
    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(11))
    }

    buildFeatures {
        viewBinding=true
    }
}

dependencies {
    val roomVersion="2.5.2"
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //unit testcases
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    testImplementation ("androidx.room:room-testing:$roomVersion")
    testImplementation ("junit:junit:4.13.2")
    testImplementation ("com.google.truth:truth:1.1.2")
    testImplementation ("org.robolectric:robolectric:4.5.1")

    //coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    //dagger
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")

    //viewModel & livedata
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")

    //ktx
    implementation("androidx.activity:activity-ktx:1.7.2")

    // Glide
    implementation ("com.github.bumptech.glide:glide:4.9.0")
    kapt ("com.github.bumptech.glide:compiler:4.9.0")

    //room
    implementation ("androidx.room:room-runtime:$roomVersion")
    kapt ("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    //shimmer
    implementation ("com.facebook.shimmer:shimmer:0.5.0")


    //RxJava
    implementation ("io.reactivex.rxjava3:rxjava:3.0.13")
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.0")



}

