import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import ru.je_dog.build_logic.convention.core.ext.androidTestImplementation
import ru.je_dog.build_logic.convention.core.ext.implementation
import ru.je_dog.build_logic.convention.core.ext.implementationPlatform
import ru.je_dog.build_logic.convention.core.ext.implementationProject
import ru.je_dog.build_logic.convention.core.ext.testImplementation
import ru.je_dog.build_logic.convention.core.ext.versionCatalog
import ru.je_dog.build_logic.convention.dependencies.DependenciesName

class AndroidLibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) = with(target) {

        val libs = versionCatalog()

        with(pluginManager){

            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
            apply("je_dog.di.koin.android")

        }

        extensions.configure<LibraryExtension> {

            compileSdk = 34

            defaultConfig {
                minSdk = 24
                targetSdk = 34

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                vectorDrawables {
                    useSupportLibrary = true
                }
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            kotlinExtension.jvmToolchain(17)

            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }

        }

        dependencies {

            implementationProject(":core")

            with(libs){
                with(DependenciesName){

                    implementation(findLibrary(androidxCoreKtx))
                    implementation(findLibrary(androidxLifecycleRuntimeKtx))

                    testImplementation(findLibrary(jUnit))

                    androidTestImplementation(findLibrary(jUnitExtensionAndroidx))
                    androidTestImplementation(findLibrary(espressoCore))

                }
            }
        }
    }
}