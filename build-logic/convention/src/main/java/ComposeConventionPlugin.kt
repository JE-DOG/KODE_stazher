import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import ru.je_dog.build_logic.convention.core.ext.androidTestImplementation
import ru.je_dog.build_logic.convention.core.ext.androidTestImplementationPlatform
import ru.je_dog.build_logic.convention.core.ext.debugImplementation
import ru.je_dog.build_logic.convention.core.ext.implementation
import ru.je_dog.build_logic.convention.core.ext.implementationPlatform
import ru.je_dog.build_logic.convention.core.ext.testImplementation
import ru.je_dog.build_logic.convention.core.ext.versionCatalog
import ru.je_dog.build_logic.convention.dependencies.DependenciesName

class ComposeConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) = with(target) {

        val libs = versionCatalog()

        extensions.configure<ApplicationExtension> {

            buildFeatures {
                compose = true
            }

            composeOptions {
                kotlinCompilerExtensionVersion = libs.findVersion(DependenciesName.compose_compiler).get().toString()
            }

        }

        dependencies {
            with(DependenciesName){
                with(libs) {

                    implementation(findLibrary(compose_navigation))

                    implementationPlatform(findLibrary(composeBom))

                    implementation(findLibrary(composeUi))
                    implementation(findLibrary(composeUiGraphics))
                    implementation(findLibrary(composeMaterial3))
                    implementation(findLibrary(androidxActivityCompose))
                    implementation(findLibrary(composeUiToolingPreview))

                    testImplementation(findLibrary(composeJunitUiTest))

                    debugImplementation(findLibrary(composeJunitUiTooling))
                    debugImplementation(findLibrary(composeJunitUiTestManifest))

                    androidTestImplementationPlatform(findLibrary(composeBom))

                    androidTestImplementation(findLibrary(composeJunitUiTestManifest))
                    androidTestImplementation(findLibrary(composeJunitUiTest))
                    androidTestImplementation(findLibrary(composeJunitUiTooling))

                }

            }
        }

    }

}