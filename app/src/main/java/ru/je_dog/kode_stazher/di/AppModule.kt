package ru.je_dog.kode_stazher.di

import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {

    includes(
        networkModule
    )

}