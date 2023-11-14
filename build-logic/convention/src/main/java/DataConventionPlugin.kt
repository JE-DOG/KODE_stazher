import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.je_dog.build_logic.convention.core.ext.implementationProject

class DataConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) = with(target) {

        with(pluginManager){

            apply("je_dog.android.library")
            apply("je_dog.android.network.retrofit")

        }

        dependencies {

            implementationProject(":core")
            implementationProject(":core:data")
            implementationProject(":core:domain")

        }

    }
}