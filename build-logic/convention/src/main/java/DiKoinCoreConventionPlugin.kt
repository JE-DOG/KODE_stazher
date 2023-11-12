import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.je_dog.build_logic.convention.core.ext.implementation
import ru.je_dog.build_logic.convention.core.ext.implementationPlatform
import ru.je_dog.build_logic.convention.core.ext.versionCatalog
import ru.je_dog.build_logic.convention.dependencies.DependenciesName

class DiKoinCoreConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) = with(target) {

        val libs = versionCatalog()

        dependencies {
            with(libs){
                with(DependenciesName){

                    implementationPlatform(findLibrary(koin_bom))

                    implementation(findLibrary(koin_core))

                }
            }
        }
    }

}