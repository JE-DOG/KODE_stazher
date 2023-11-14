package ru.je_dog.data.users.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.je_dog.data.users.UsersRepositoryImpl
import ru.je_dog.data.users.network.UsersRemoteDataSource
import ru.je_dog.data.users.network.UsersRemoteDataSourceImpl
import ru.je_dog.data.users.network.api.UsersApi
import ru.je_dog.users.UsersRepository

val dataModule = module {

    single<UsersApi> {
        val retrofit = get<Retrofit>()

        retrofit
            .create(UsersApi::class.java)
    }

    singleOf(::UsersRemoteDataSourceImpl) { bind<UsersRemoteDataSource>() }
    singleOf(::UsersRepositoryImpl) { bind<UsersRepository>() }

}