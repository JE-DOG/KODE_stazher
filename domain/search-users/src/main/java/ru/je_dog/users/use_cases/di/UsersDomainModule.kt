package ru.je_dog.users.use_cases.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.je_dog.users.use_cases.GetDynamicUsersUseCase
import ru.je_dog.users.use_cases.GetUsersUseCases
import ru.je_dog.users.use_cases.GetUsersWithErrorUseCase

val searchUsersDomainModule = module {

    singleOf(::GetDynamicUsersUseCase)
    singleOf(::GetUsersUseCases)
    singleOf(::GetUsersWithErrorUseCase)

}