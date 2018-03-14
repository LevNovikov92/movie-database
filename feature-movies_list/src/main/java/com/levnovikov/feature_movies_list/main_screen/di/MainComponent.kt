package com.levnovikov.feature_movies_list.main_screen.di

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import com.levnovikov.data_movies.MoviesRepo
import com.levnovikov.feature_movies_list.main_screen.MoviesListActivity
import com.levnovikov.feature_movies_list.main_screen.movies_list.MoviesListView
import com.levnovikov.feature_movies_list.main_screen.movies_list.di.MoviesListComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
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
    fun moviesRepo(): MoviesRepo
}

@MainScope
@Component(dependencies = [MainComponentDependencies::class], modules = [
    MainComponent.MainModule::class
])
interface MainComponent {

    @Module(subcomponents = [MoviesListComponent::class])
    class MainModule {
        @Provides
        fun provideLayoutInflater(activity: Activity): LayoutInflater =
                activity.layoutInflater
    }

    @Component.Builder
    interface Builder {
        fun build(): MainComponent
        fun dependency(dependency: MainComponentDependencies): Builder

        @BindsInstance
        fun activity(activity: Activity): Builder
    }

    fun inject(moviesListActivity: MoviesListActivity)

    fun moviesListBuilder(): MoviesListComponent.Builder
}