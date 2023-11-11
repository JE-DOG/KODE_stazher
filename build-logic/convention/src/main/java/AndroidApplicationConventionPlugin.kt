import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import ru.je_dog.build_logic.convention.core.ext.androidTestImplementation
import ru.je_dog.build_logic.convention.core.ext.implementation
import ru.je_dog.build_logic.convention.core.ext.testImplementation
import ru.je_dog.build_logic.convention.core.ext.versionCatalog
import ru.je_dog.build_logic.convention.dependencies.DependenciesName

class AndroidApplicationConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) = with(target) {

        val libs = versionCatalog()

        with(pluginManager){

            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")

        }

        extensions.configure<ApplicationExtension> {

            compileSdk = 34

            defaultConfig {
                applicationId = "ru.je_dog.firebase_learn"
                minSdk = 24
                targetSdk = 34
                versionCode = 1
                versionName = "1.0"

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