package com.levnovikov.movie_database.di.modules

import com.levnovikov.core_network.API_KEY
import com.levnovikov.core_network.BASE_URL
import com.levnovikov.core_network.NetworkModule
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */

@Module(includes = [NetworkModule::class])
class NetworkConfigModule {

    @Provides
    @Named(BASE_URL)
    fun provideBaseUrl() = "https://api.themoviedb.org/3/"

    @Provides
    @Named(API_KEY)
    fun provideApiKey() = "7eec4ec73a1da74ccd7307112a55f491"
}