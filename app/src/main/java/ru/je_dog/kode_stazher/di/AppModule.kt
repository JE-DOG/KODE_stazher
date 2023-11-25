package ru.je_dog.kode_stazher.di

import android.content.Context
import androidx.navigation.NavController
import org.koin.dsl.module
import ru.je_dog.kode_stazher.App

fun appModule(
    navController: NavController
) = module {

    single<Context> { App.INSTANCE }
    single<NavController> { navController }

    includes(
        networkModule
    )

}