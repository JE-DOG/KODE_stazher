package ru.je_dog.build_logic.convention.dependencies

object DependenciesName {

    //AndroidX
    const val androidxActivityCompose = "androidx.activity.compose"
    const val androidxCoreKtx = "androidx.core.ktx"
    const val androidxLifecycleRuntimeKtx = "androidx.core.ktx"

//Compose

    const val compose_navigation = "compose.navigation"

    const val composeBom = "compose.bom"
    const val composeUi = "compose.ui"
    const val composeUiGraphics = "compose.ui.graphics"
    const val composeUiToolingPreview = "compose.ui.tooling.preview"
    const val composeMaterial3 = "compose.material3"
    const val composeMaterial = "compose.material"
    //Tests
    const val composeJunitUiTest = "compose.junit.ui.test"
    const val composeJunitUiTooling = "compose.junit.ui.tooling"
    const val composeJunitUiTestManifest = "compose.junit.ui.test.manifest"
    const val extensionAndroidx = "extension.androidx"

//HTTP

    //Retrofit
    const val retrofit = "retrfoit"
    const val retrofit_gson = "retrfoit.gson"
    //Okhttp
    const val okhttp_bom = "okhttp.bom"
    const val okhttp = "okhttp"
    const val okhttp_interceptor = "okhttp.interceptor"

//DI

    const val koin_bom = "koin.bom"
    const val koin_core = "koin.core"
    const val koin_android = "koin.android"
    const val koin_compose = "koin.compose"

//Tests

    //Junit
    const val jUnit = "junit"
    const val jUnitExtensionAndroidx = "junit.extension.androidx"
    //Espresso
    const val  espressoCore = "espresso.core"

}