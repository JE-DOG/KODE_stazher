plugins {
    id("je_dog.android.library")
    id("je_dog.android.network.retrofit")
}

android {
    namespace = "ru.je_dog.core.data"
}

dependencies {

    implementation( project(Projects.Core.domain) )
    implementation( project(Projects.Core.core) )

}