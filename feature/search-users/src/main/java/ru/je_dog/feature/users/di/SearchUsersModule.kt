package ru.je_dog.feature.users.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.je_dog.data.users.di.searchUsersDataModule
import ru.je_dog.feature.users.vm.SearchUsersViewModel
import ru.je_dog.users.use_cases.di.searchUsersDomainModule

val searchUsersModule = module {

    includes(
        searchUsersDataModule,
        searchUsersDomainModule
    )
    singleOf(::SearchUsersViewModel)

}