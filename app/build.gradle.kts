plugins {
    alias(libs.plugins.androidApplication)
}


android {
    namespace = "com.example.mediconnect"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mediconnect"
        minSdk = 26
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
    buildFeatures{
        viewBinding = true
    }

}


    dependencies {
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation("androidx.recyclerview:recyclerview:1.3.2")
        implementation("com.github.bumptech.glide:glide:4.16.0")
        implementation("com.google.android.material:material:1.12.0")
        implementation("com.squareup.okhttp3:okhttp:3.12.1")
        implementation("io.reactivex.rxjava2:rxjava:2.2.5")
        implementation("io.reactivex.rxjava2:rxandroid:2.1.0")
        implementation("com.github.NaikSoftware:StompProtocolAndroid:1.6.6")
        implementation("com.android.support:support-annotations:28.0.0")
        implementation("com.google.guava:guava:31.0.1-android")
        implementation(libs.appcompat)
        implementation(libs.material)
        implementation(libs.activity)
        implementation(libs.constraintlayout)
        testImplementation(libs.junit)
        androidTestImplementation(libs.ext.junit)
        androidTestImplementation(libs.espresso.core)
    }