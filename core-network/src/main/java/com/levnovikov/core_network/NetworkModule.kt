package com.levnovikov.core_network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton


/**
 * Author: lev.novikov
 * Date: 12/3/18.
 */

const val BASE_URL = "BASE_URL"
const val API_KEY = "API_KEY"
const val CACHE_SIZE = "CACHE_SIZE"

@Module
class NetworkModule {

    @Provides
    fun provideLoggingInterceptor() =
            HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }

    @Singleton
    @Provides
    fun provideClient(clientBuilder: HttpClientBuilder): OkHttpClient =
            clientBuilder.build()

    @Singleton
    @Provides
    fun provideRetrofit(builder: RetrofitBuilder): Retrofit = builder.build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()
}