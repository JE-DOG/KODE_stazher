package ru.je_dog.build_logic.convention.core.ext

import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope
import ru.je_dog.build_logic.convention.dependencies.DependenciesConfiguration
import java.util.Optional

fun DependencyHandlerScope.implementation(library: Optional<Provider<MinimalExternalModuleDependency>>) = add(DependenciesConfiguration.implementation,library.get())

fun DependencyHandlerScope.implementationPlatform(library: Optional<Provider<MinimalExternalModuleDependency>>) = add(DependenciesConfiguration.debugImplementation,platform(library.get()))

fun DependencyHandlerScope.testImplementation(library: Optional<Provider<MinimalExternalModuleDependency>>) = add(DependenciesConfiguration.testImplementation,library.get())

fun DependencyHandlerScope.androidTestImplementation(library: Optional<Provider<MinimalExternalModuleDependency>>) = add(DependenciesConfiguration.androidTestImplementation,library.get())

fun DependencyHandlerScope.androidTestImplementationPlatform(library: Optional<Provider<MinimalExternalModuleDependency>>) = add(DependenciesConfiguration.androidTestImplementation,platform( library.get() ))

fun DependencyHandlerScope.debugImplementation(library: Optional<Provider<MinimalExternalModuleDependency>>) = add(DependenciesConfiguration.debugImplementation,library.get())

