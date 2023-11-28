package ru.je_dog.kode_stazher.di

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import org.koin.dsl.module
import ru.je_dog.feature.users.di.searchUsersModule
import ru.je_dog.kode_stazher.App

val appModule = module {

    single<Context> { App.INSTANCE }

    includes(
        networkModule
    )

    //Features
    includes(
        searchUsersModule
    )

}