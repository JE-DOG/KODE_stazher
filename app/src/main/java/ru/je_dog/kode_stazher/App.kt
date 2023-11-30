package ru.je_dog.kode_stazher

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.je_dog.feature.users.di.searchUsersModule
import ru.je_dog.kode_stazher.di.appModule

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        init()

    }

    private fun init() {
        INSTANCE = this

        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(
                appModule
            )
        }
    }

    companion object {

        lateinit var INSTANCE: App
            private set

    }
}