package main_screen.di

import activity.Lifecycle
import activity_starter.DetailsActivityStarter
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import com.levnovikov.core_common.AsyncHelper
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import data_movies.MoviesRepo
import date_selector.dependencies.DateStreamProvider
import date_selector.dependencies.OnDateSelectedListener
import date_selector.di.DateSelectorComponent
import feature_list_of_films_interface.MoviesListDependency
import main_screen.MoviesListActivity
import main_screen.MoviesScreenRepo
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
interface MainComponent : MoviesListDependency {

    @Module(subcomponents = [DateSelectorComponent::class])
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

    fun dateSelectorBuilder(): DateSelectorComponent.Builder
}