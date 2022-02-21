package ru.kirea.filmswithpagination

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.kirea.filmswithpagination.di.Modules

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                Modules.application,
                Modules.appActivity,
                Modules.filmListWindow,
                Modules.pageWithFilmsWindow,
                Modules.filmInfoWindow
            )
        }
    }
}