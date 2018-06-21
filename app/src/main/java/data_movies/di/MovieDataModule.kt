package data_movies.di

import core_api.MovieApiModule
import dagger.Binds
import dagger.Module
import data_movies.MoviesRepo
import data_movies.MoviesRepoImpl

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */

@Module(includes = [MovieApiModule::class])
interface MovieDataModule {

    @Binds fun bindMoviesRepo(impl: MoviesRepoImpl): MoviesRepo
}