package com.levnovikov.feature_list_of_films.movies_list.di

import com.levnovikov.feature_list_of_films.movies_list.ListView
import com.levnovikov.feature_list_of_films.movies_list.MoviesListAdapter
import com.levnovikov.feature_list_of_films.movies_list.MoviesListAdapterImpl
import com.levnovikov.feature_list_of_films.movies_list.MoviesListPresenter
import com.levnovikov.feature_list_of_films.movies_list.MoviesListPresenterImpl
import com.levnovikov.feature_list_of_films.movies_list.MoviesListView
import com.levnovikov.feature_list_of_films.movies_list.NavigatorModule
import com.levnovikov.feature_list_of_films_interface.MoviesListDependency
import dagger.Binds
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
annotation class MoviesListScope

@MoviesListScope
@Component(dependencies = [MoviesListDependency::class],
        modules = [MoviesListComponent.MoviesListModule::class, NavigatorModule::class])
interface MoviesListComponent {

    @Module
    interface MoviesListModule {
        @Binds fun bindListView(view: MoviesListView): ListView
        @Binds fun bindPresenter(impl: MoviesListPresenterImpl): MoviesListPresenter
        @Binds fun bindAdapter(impl: MoviesListAdapterImpl): MoviesListAdapter
    }

    @Component.Builder
    interface Builder {
        fun build(): MoviesListComponent
        fun dependency(dependency: MoviesListDependency): Builder

        @BindsInstance
        fun view(view: MoviesListView): Builder
    }

    fun inject(moviesListView: MoviesListView)

}