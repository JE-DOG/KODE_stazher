import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.je_dog.build_logic.convention.core.ext.implementation
import ru.je_dog.build_logic.convention.core.ext.implementationPlatform
import ru.je_dog.build_logic.convention.core.ext.versionCatalog
import ru.je_dog.build_logic.convention.dependencies.DependenciesName

class NetworkRetrofitConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) = with(target) {

        val libs = versionCatalog()

        dependencies {
            with(libs){
                with(DependenciesName){

                    implementationPlatform(findLibrary(okhttp_bom))

                    implementation(findLibrary(okhttp))
                    implementation(findLibrary(okhttp_interceptor))

                    implementation(findLibrary(retrofit))
                    implementation(findLibrary(retrofit_gson))

                }
            }
        }

    }

}