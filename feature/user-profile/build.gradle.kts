plugins {
    id("je_dog.feature")
}

android {
    namespace = "ru.je_dog.feature.user_profile"

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get().toString()
    }
}