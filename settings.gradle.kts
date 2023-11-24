pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "KODE_stazher"
include(":app")
include(":core")
include(":core:domain")
include(":core:data")
include(":core:feature")
include(":feature")
include(":data")
include(":domain")
include(":domain:search-users")
include(":data:search-users")
include(":feature:search-users")
include(":feature:user-profile")
