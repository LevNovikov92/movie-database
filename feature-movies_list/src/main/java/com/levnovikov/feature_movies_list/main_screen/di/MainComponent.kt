package com.levnovikov.feature_movies_list.main_screen.di

import android.app.Activity
import android.content.Context
import com.levnovikov.data_movies.MoviesRepo
import com.levnovikov.data_movies.di.MovieDataModule
import com.levnovikov.feature_movies_list.main_screen.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import retrofit2.Retrofit
import javax.inject.Scope

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class MainScope

interface MainComponentDependencies {
    fun context(): Context
    fun retrofit(): Retrofit
    fun moviesRepo(): MoviesRepo
}

@MainScope
@Component(dependencies = [MainComponentDependencies::class], modules = [
    MainComponent.MainModule::class
])
interface MainComponent {

    @Module
    class MainModule {

    }

    @Component.Builder
    interface Builder {
        fun build(): MainComponent
        fun dependency(dependency: MainComponentDependencies): Builder

        @BindsInstance
        fun activity(activity: Activity): Builder
    }

    fun inject(mainActivity: MainActivity)
}