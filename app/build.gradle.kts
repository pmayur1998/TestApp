import java.util.Properties
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.appdistribution)
}

android {
    namespace = "com.example.testapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.testapp"
        minSdk = 24
        targetSdk = 35
        val versionCodeVal = getVersionCodeFromBuildProperty("VERSION_CODE")
        versionCode = versionCodeVal
        versionName = generateVersionName(versionCodeVal)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file("${rootProject.rootDir}/keystore.jks")
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = System.getenv("KEY_ALIAS")
            keyPassword = System.getenv("KEY_PASSWORD")
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
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

fun getVersionCodeFromBuildProperty(buildPropertyName: String): Int {
    val properties = Properties()
    val buildPropertiesFile = rootProject.file("build.properties")

    buildPropertiesFile.inputStream().use { properties.load(it) }

    val versionValue = properties.getProperty(buildPropertyName)
        ?: error("Build property '$buildPropertyName' not found.")

    return versionValue.toInt()
}

fun generateVersionName(versionCode: Int): String {
    val major = versionCode / 100
    val minor = (versionCode / 10) % 10
    val patch = versionCode % 10
    return "$major.$minor.$patch"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(platform(libs.firebase.bom))
}
firebaseAppDistribution {
    serviceCredentialsFile = "${rootProject.rootDir}/firebase-service-account.json"
    releaseNotes = System.getenv("FIREBASE_RELEASE_NOTES") ?: "Auto release from CI"
    groups = System.getenv("FIREBASE_GROUPS") ?: ""
}
