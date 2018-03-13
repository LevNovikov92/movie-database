package com.levnovikov.core_network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.levnovikov.core_network.requests.SignedRequest
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import javax.inject.Named
import javax.inject.Singleton

/**
 * Author: lev.novikov
 * Date: 12/3/18.
 */

internal const val BASE_URL = "BASE_URL"
internal const val API_KEY = "API_KEY"

@Module(includes = [NetworkModule::class])
class RetrofitModule {

    @Provides
    @Named(BASE_URL)
    fun provideBaseUrl() = "https://api.themoviedb.org/3"

    @Provides
    @Named(API_KEY)
    fun provideApiKey() = "7eec4ec73a1da74ccd7307112a55f491"

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

    @Provides
    fun provideGson(@Named(API_KEY) apiKey: String): Gson =
            GsonBuilder().registerTypeAdapterFactory(object : TypeAdapterFactory {
                override fun <T : Any?> create(gson: Gson, type: TypeToken<T>?): TypeAdapter<T> {
                    val delegateTypeAdapter = gson.getDelegateAdapter(this, type)
                    return object : TypeAdapter<T>() {

                        @Throws(IOException::class)
                        override fun write(out: JsonWriter, value: T) {
                            if (value is SignedRequest) {
                                value.apiKey = apiKey
                            }
                            delegateTypeAdapter.write(out, value)
                        }

                        @Throws(IOException::class)
                        override fun read(`in`: JsonReader): T {
                            return delegateTypeAdapter.read(`in`)
                        }
                    }
                }

            }).create()
}