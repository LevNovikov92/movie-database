package com.levnovikov.movie_database.di.modules

import com.levnovikov.core_common.activity_starter.DetailsActivityStarter
import com.levnovikov.feature_movie_details.activity_starter.DetailsActivityStarterImpl
import dagger.Binds
import dagger.Module

/**
 * Author: lev.novikov
 * Date: 15/3/18.
 */

@Module
interface ActivityStarterModule {

    @Binds
    fun bindMovieDetailsStarter(impl: DetailsActivityStarterImpl): DetailsActivityStarter
}