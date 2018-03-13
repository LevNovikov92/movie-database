package com.levnovikov.data_movies.di

import com.levnovikov.core_api.MovieApiModule
import com.levnovikov.data_movies.MoviesRepo
import com.levnovikov.data_movies.MoviesRepoImpl
import dagger.Binds
import dagger.Module

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */

@Module(includes = [MovieApiModule::class])
interface MovieDataModule {

    @Binds fun bindMoviesRepo(impl: MoviesRepoImpl): MoviesRepo
}