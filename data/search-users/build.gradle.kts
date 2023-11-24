plugins {
    id("je_dog.data")
}

android {
    namespace = "ru.je_dog.data.users"
}

dependencies {

    implementation( project(Projects.Domain.users) )

}