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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get().toString()
    }
}

dependencies {

    implementation( project(Projects.Core.core) )
    implementation( project(Projects.Core.domain) )
    implementation( project(Projects.Core.data) )
    implementation( project(Projects.Core.feature) )

    implementation( project(Projects.Feature.search_users) )
    implementation( project(Projects.Feature.user_profile) )

}