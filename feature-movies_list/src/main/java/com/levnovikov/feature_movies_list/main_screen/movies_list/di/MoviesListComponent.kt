package com.levnovikov.feature_movies_list.main_screen.movies_list.di

import com.levnovikov.feature_movies_list.main_screen.movies_list.ListView
import com.levnovikov.feature_movies_list.main_screen.movies_list.MovieVOLoader
import com.levnovikov.feature_movies_list.main_screen.movies_list.MoviesListPresenter
import com.levnovikov.feature_movies_list.main_screen.movies_list.MoviesListView
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Subcomponent
import javax.inject.Scope

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class MoviesListScope

@MoviesListScope
@Subcomponent(modules = [MoviesListComponent.MoviesListModule::class])
interface MoviesListComponent {

    @Module
    interface MoviesListModule {
        @Binds fun provideMovieLoader(presenter: MoviesListPresenter): MovieVOLoader
        @Binds fun provideListView(view: MoviesListView): ListView
    }

    @Subcomponent.Builder
    interface Builder {
        fun build(): MoviesListComponent

        @BindsInstance
        fun view(view: MoviesListView): Builder
    }

    fun inject(moviesListView: MoviesListView)

}