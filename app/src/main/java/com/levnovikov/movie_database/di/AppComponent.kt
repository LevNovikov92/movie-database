package com.levnovikov.movie_database.di

import android.content.Context
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.data_movies.MoviesRepo
import com.levnovikov.data_movies.di.MovieDataModule
import com.levnovikov.feature_movie_details.di.MovieDetailsDependencies
import com.levnovikov.feature_movies_list.main_screen.di.MainComponentDependencies
import com.squareup.picasso.Picasso
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */

const val APP_CONTEXT = "APP_CONTEXT"

@Singleton
@Component(modules = [
    AppModule::class,
    NetworkConfigModule::class,
    MovieDataModule::class,
    AsyncHelperModule::class,
    PicassoModule::class
])
interface AppComponent : MainComponentDependencies, MovieDetailsDependencies {

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun context(context: Context): Builder
    }

    override fun context(): Context
    override fun moviesRepo(): MoviesRepo
    override fun asyncHelper(): AsyncHelper
    override fun picasso(): Picasso
}

@Module
class AppModule {

    @Provides
    @Named(APP_CONTEXT)
    fun provideContext(context: Context) = context

    @Provides
    @Named(BASE_IMAGE_URL)
    fun provideBaseImageUrl() = "https://image.tmdb.org/t/p/w500"
}