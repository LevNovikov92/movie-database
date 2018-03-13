package com.levnovikov.movie_database

import android.app.Application
import com.levnovikov.movie_database.di.AppComponent
import com.levnovikov.movie_database.di.DaggerAppComponent

/**
 * Author: lev.novikov
 * Date: 12/3/18.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupDI()
    }

    lateinit var component: AppComponent

    private fun setupDI() {
        component = DaggerAppComponent.builder()
                .context(this)
                .build()
    }
}

fun Application.getComponent(): AppComponent =
        (this as? App)?.component ?: throw UnsupportedOperationException("Application is not App class")