plugins {
    id("je_dog.feature")
}

android {
    namespace = "ru.je_dog.feature.users"

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get().toString()
    }
}

dependencies {

    implementation( project(Projects.Domain.users) )
    implementation( project(Projects.Data.users) )

    with(libs.other){

        implementation(pull.to.refresh)

    }

}