package com.levnovikov.feature_movie_details.di

import android.content.Context
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.data_movies.MoviesRepo
import com.levnovikov.feature_movie_details.MOVIE_ID
import com.levnovikov.feature_movie_details.MovieDetailsActivity
import com.levnovikov.feature_movie_details.MovieDetailsViewModel
import com.levnovikov.feature_movie_details.MovieDetailsViewModelImpl
import com.levnovikov.feature_movie_details.ViewModelActions
import com.levnovikov.system_lifecycle.activity.Lifecycle
import com.squareup.picasso.Picasso
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Scope

/**
 * Author: lev.novikov
 * Date: 15/3/18.
 */

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class MovieDetailsScope

interface MovieDetailsDependencies {
    fun context(): Context
    fun moviesRepo(): MoviesRepo
    fun asyncHelper(): AsyncHelper
    fun picasso(): Picasso
}

@MovieDetailsScope
@Component(dependencies = [MovieDetailsDependencies::class], modules = [MovieDetailsComponent.MovieDetailsModule::class])
interface MovieDetailsComponent {

    @Module
    class MovieDetailsModule {
        @Provides
        fun provideViewModel(impl: MovieDetailsViewModelImpl): MovieDetailsViewModel = impl

        @Provides
        fun provideViewModelActions(impl: MovieDetailsViewModelImpl): ViewModelActions = impl

        @Provides
        fun provideLifecycle(activity: MovieDetailsActivity): Lifecycle = activity

        @Provides
        @Named(MOVIE_ID)
        fun provideId(activity: MovieDetailsActivity): Int = activity.intent.getIntExtra(MOVIE_ID, 0)
    }

    @Component.Builder
    interface Builder {
        fun build(): MovieDetailsComponent
        fun dependencies(dependencies: MovieDetailsDependencies): Builder

        @BindsInstance
        fun activity(activity: MovieDetailsActivity): Builder
    }

    fun inject(movieDetailsActivity: MovieDetailsActivity)
}