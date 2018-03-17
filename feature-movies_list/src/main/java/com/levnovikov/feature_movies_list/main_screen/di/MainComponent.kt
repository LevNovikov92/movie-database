package com.levnovikov.feature_movies_list.main_screen.di

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import com.levnovikov.core_common.ActivityStarter
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.core_common.activity_starter.DetailsActivityStarter
import com.levnovikov.data_movies.MoviesRepo
import com.levnovikov.feature_movies_list.main_screen.DateStreamProvider
import com.levnovikov.feature_movies_list.main_screen.MoviesListActivity
import com.levnovikov.feature_movies_list.main_screen.MoviesScreenRepo
import com.levnovikov.feature_movies_list.main_screen.OnDateSelectedListener
import com.levnovikov.feature_movies_list.main_screen.date_selector.di.DateSelectorComponent
import com.levnovikov.feature_movies_list.main_screen.movies_list.MoviesListView
import com.levnovikov.feature_movies_list.main_screen.movies_list.di.MoviesListComponent
import com.levnovikov.system_lifecycle.activity.Lifecycle
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import java.util.*
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
    fun asyncHelper(): AsyncHelper
    fun detailsActivityStarter(): DetailsActivityStarter
}

@MainScope
@Component(dependencies = [MainComponentDependencies::class], modules = [
    MainComponent.MainModule::class
])
interface MainComponent {

    @Module(subcomponents = [MoviesListComponent::class, DateSelectorComponent::class])
    class MainModule {
        @Provides
        fun provideLayoutInflater(activity: MoviesListActivity): LayoutInflater =
                activity.layoutInflater

        @Provides
        fun provideActivity(activity: MoviesListActivity): Activity = activity

        @Provides
        fun provideDateStreamProvider(impl: MoviesScreenRepo): DateStreamProvider = impl

        @Provides
        fun provideOnDateSelected(impl: MoviesScreenRepo): OnDateSelectedListener = impl

        @Provides
        fun provideLifecycle(activity: MoviesListActivity): Lifecycle = activity
    }

    @Component.Builder
    interface Builder {
        fun build(): MainComponent
        fun dependency(dependency: MainComponentDependencies): Builder

        @BindsInstance
        fun activity(activity: MoviesListActivity): Builder

        @BindsInstance
        fun dateByDefault(date: Date?): Builder
    }

    fun inject(moviesListActivity: MoviesListActivity)

    fun moviesListBuilder(): MoviesListComponent.Builder
    fun dateSelectorBuilder(): DateSelectorComponent.Builder
}