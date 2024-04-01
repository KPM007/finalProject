plugins {
    id("com.android.application")
}

android {
    namespace = "algonquin.cst2335.recipesearchapi"
    compileSdk = 34

    defaultConfig {
        applicationId = "algonquin.cst2335.recipesearchapi"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

// Other dependencies

dependencies {
    val room_version = "2.6.1"
    implementation ("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    implementation ("com.android.volley:volley:1.2.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    implementation ("com.github.bumptech.glide:glide:5.0.0-rc01")
    implementation("com.squareup.picasso:picasso:2.71828")
    // For Java use annotationProcessor instead of kapt
    // Optional - Kotlin Extensions and Coroutines support for Room

    // For the integration of Glide with the Android framework
    annotationProcessor ("com.github.bumptech.glide:compiler:5.0.0-rc01")

    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}