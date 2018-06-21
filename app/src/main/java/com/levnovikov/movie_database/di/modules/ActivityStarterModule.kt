package com.levnovikov.movie_database.di.modules

import activity_starter.DetailsActivityStarter
import dagger.Binds
import dagger.Module
import feature_movie_details.activity_starter.DetailsActivityStarterImpl

/**
 * Author: lev.novikov
 * Date: 15/3/18.
 */

@Module
interface ActivityStarterModule {

    @Binds
    fun bindMovieDetailsStarter(impl: DetailsActivityStarterImpl): DetailsActivityStarter
}