package com.levnovikov.core_network

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

/**
 * Author: lev.novikov
 * Date: 12/3/18.
 */

internal const val BASE_URL = "BASE_URL"

@Module(includes = [NetworkModule::class])
class RetrofitModule {

    @Singleton
    @Provides
    @Named(BASE_URL)
    fun provideBaseUrl() = "https://api.themoviedb.org/3"

    @Singleton
    @Provides
    fun provideRetrofit(builder: RetrofitBuilder) = builder.build()
}

@Module
internal class NetworkModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()

    @Provides
    fun provideInterceptors(loggingInterceptor: HttpLoggingInterceptor): List<Interceptor> =
            listOf(loggingInterceptor)

    @Singleton
    @Provides
    fun provideClient(clientBuilder: HttpClientBuilder, interceptors: List<Interceptor>) =
            clientBuilder.build(interceptors)
}