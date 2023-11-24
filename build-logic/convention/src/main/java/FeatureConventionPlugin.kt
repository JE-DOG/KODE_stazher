import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.je_dog.build_logic.convention.core.ext.implementation
import ru.je_dog.build_logic.convention.core.ext.implementationProject
import ru.je_dog.build_logic.convention.core.ext.versionCatalog
import ru.je_dog.build_logic.convention.dependencies.DependenciesName

class FeatureConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) = with(target) {

        val libs = versionCatalog()

        with(pluginManager){
            apply("je_dog.android.library")
            apply("je_dog.android.compose")
            apply("je_dog.di.koin.android")
        }

        dependencies {

            implementationProject(":core")
            implementationProject(":core:feature")
            implementationProject(":core:data")
            implementationProject(":core:domain")
            implementation(libs.findLibrary(DependenciesName.retrofit_gson))

        }

    }
}