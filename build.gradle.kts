// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    with(libs.plugins){

        with(android){
            alias(application) apply false
            alias(library) apply false
        }

        with(kotlin){
            alias(android) apply false
            alias(jvm) apply false
        }

    }
}