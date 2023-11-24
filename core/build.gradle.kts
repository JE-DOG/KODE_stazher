plugins {
    id("je_dog.kotlin.library")
}

dependencies {
    with(libs){

        with(kotlin){

            api(coroutines.core)

        }

    }
}