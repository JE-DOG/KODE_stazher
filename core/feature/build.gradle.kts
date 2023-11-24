plugins {
    id("je_dog.android.library")
    id("je_dog.android.compose")
}

android {
    namespace = "ru.je_dog.core.feature"

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

}