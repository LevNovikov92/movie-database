package com.levnovikov.movie_database.main_screen.di

import android.app.Activity
import com.levnovikov.core_api.MovieApiModule
import com.levnovikov.movie_database.di.AppComponent
import com.levnovikov.movie_database.main_screen.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Scope

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class MainScope

@MainScope
@Component(dependencies = [AppComponent::class], modules = [MainComponent.MainModule::class, MovieApiModule::class])
interface MainComponent {

    @Module
    class MainModule {

    }

    @Component.Builder
    interface Builder {
        fun build(): MainComponent
        fun dependency(dependency: AppComponent): Builder

        @BindsInstance
        fun activity(activity: Activity): Builder
    }

    fun inject(mainActivity: MainActivity)
}