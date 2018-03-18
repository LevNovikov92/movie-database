package com.levnovikov.movie_database

import android.app.Application
import com.levnovikov.core_common.ComponentProvider
import com.levnovikov.feature_movie_details.di.MovieDetailsDependencies
import com.levnovikov.feature_movies_list.main_screen.di.MainComponentDependencies
import com.levnovikov.movie_database.di.AppComponent
import com.levnovikov.movie_database.di.DaggerAppComponent
import timber.log.Timber
import kotlin.reflect.KClass

/**
 * Author: lev.novikov
 * Date: 12/3/18.
 */
class App : Application(), ComponentProvider {
    override fun onCreate() {
        super.onCreate()
        setupLogger()
        setupDI()
    }

    private fun setupLogger() {
        Timber.plant(Timber.DebugTree())
    }

    private lateinit var component: AppComponent

    private fun setupDI() {
        component = DaggerAppComponent.builder()
                .context(this)
                .build()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <C : Any> getComponent(clazz: KClass<C>): C? =
            when(clazz) {
                MainComponentDependencies::class -> component as C
                MovieDetailsDependencies::class -> component as C
                else -> throw UnsupportedOperationException("App don't implement interface ${clazz.simpleName}")
            }
}
