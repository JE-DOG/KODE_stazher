plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {

    plugins {

        register("je_dogAndroidCompose"){
            id = "je_dog.android.compose"
            implementationClass = "ComposeConventionPlugin"
        }

        register("je_dogAndroid"){
            id = "je_dog.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }


    }

}