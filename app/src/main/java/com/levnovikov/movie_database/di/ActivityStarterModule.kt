package com.levnovikov.movie_database.di

import com.levnovikov.core_common.activity_starter.DetailsActivityStarter
import com.levnovikov.movie_database.activity_starter_impl.DetailsActivityStarterImpl
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