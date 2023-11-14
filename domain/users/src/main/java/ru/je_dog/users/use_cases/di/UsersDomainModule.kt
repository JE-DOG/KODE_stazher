package ru.je_dog.users.use_cases.di

import org.koin.dsl.module
import ru.je_dog.users.use_cases.GetDynamicUsersUseCase
import ru.je_dog.users.use_cases.GetUsersUseCases
import ru.je_dog.users.use_cases.GetUsersWithErrorUseCase

val usersDomainModule = module {

    single {
        GetDynamicUsersUseCase(get())
    }

    single {
        GetUsersUseCases(get())
    }

    single {
        GetUsersWithErrorUseCase(get())
    }

}