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

        register("je_dogAndroidApplication"){
            id = "je_dog.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("je_dogAndroidNetworkRetrofit"){
            id = "je_dog.android.network.retrofit"
            implementationClass = "NetworkRetrofitConventionPlugin"
        }

        register("je_dogDiKoinCore"){
            id = "je_dog.di.koin.core"
            implementationClass = "DiKoinCoreConventionPlugin"
        }

        register("je_dogDiKoinAndroid"){
            id = "je_dog.di.koin.android"
            implementationClass = "AndroidDiKoinConventionPlugin"
        }

    }

}