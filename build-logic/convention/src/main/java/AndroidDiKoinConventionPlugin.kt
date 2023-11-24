import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.je_dog.build_logic.convention.core.ext.implementation
import ru.je_dog.build_logic.convention.core.ext.implementationProject
import ru.je_dog.build_logic.convention.core.ext.versionCatalog
import ru.je_dog.build_logic.convention.dependencies.DependenciesName

class AndroidDiKoinConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) = with(target) {

        val libs = versionCatalog()

        pluginManager.apply {
            apply("je_dog.di.koin.core")
        }

        dependencies {

            with(libs){
                with(DependenciesName){

                    implementation(findLibrary(koin_android))
                    implementation(findLibrary(koin_compose))

                }
            }

        }
    }
}