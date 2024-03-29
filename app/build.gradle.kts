plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.rooze.lib.composeadmob"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.rooze.lib.composeadmob"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            manifestPlaceholders["ads_id"] = "ca-app-pub-3940256099942544~3347511713"
            buildConfigField("String", "INTER_ADS_ID", "\"ca-app-pub-3940256099942544/1033173712\"")
            buildConfigField("String", "BANNER_ADS_ID", "\"ca-app-pub-3940256099942544/6300978111\"")
        }
        debug {
            isMinifyEnabled = false
            manifestPlaceholders["ads_id"] = "ca-app-pub-3940256099942544~3347511713"
            buildConfigField("String", "INTER_ADS_ID", "\"ca-app-pub-3940256099942544/1033173712\"")
            buildConfigField("String", "BANNER_ADS_ID", "\"ca-app-pub-3940256099942544/6300978111\"")
        }

        create("normal") {
            initWith(getByName("release"))
        }
        create("pro") {
            initWith(getByName("release"))
            applicationIdSuffix = ".pro"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    val nav_version = "2.7.6"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("com.google.android.gms:play-services-ads:22.6.0")
    implementation(project(":basicads"))
}