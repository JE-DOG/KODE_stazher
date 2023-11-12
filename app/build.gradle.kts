plugins {
    id("je_dog.android.application")
    id("je_dog.android.compose")
    id("je_dog.android.network.retrofit")
    id("je_dog.di.koin.android")
}

android {
    namespace = "ru.je_dog.kode_stazher"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}