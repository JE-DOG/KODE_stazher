package ru.je_dog.kode_stazher.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.je_dog.core.data.common.util.NetworkConnectionMonitorImpl
import ru.je_dog.core.data.common.util.NetworkConnectionMonitory

val networkModule = module {

    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    }

    single<OkHttpClient> {

        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()

    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://stoplight.io/mocks/kode-education/trainee-test/25143926/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single<NetworkConnectionMonitory> {
        NetworkConnectionMonitorImpl(get())
    }

}