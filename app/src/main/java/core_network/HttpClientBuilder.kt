package core_network

import core_network.interceptors.AuthInterceptor
import core_network.interceptors.RequestLimitErrorInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import javax.inject.Inject
import javax.inject.Named

/**
 * Author: lev.novikov
 * Date: 12/3/18.
 */

class HttpClientBuilder @Inject constructor(
        private val authInterceptor: AuthInterceptor,
        private val loggingInterceptor: HttpLoggingInterceptor,
        private val requestLimitInterceptor: RequestLimitErrorInterceptor,
        private val directory: File,
        @Named(CACHE_SIZE) private val cacheSize: Long
) {

    fun build(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .addInterceptor(requestLimitInterceptor)
                .cache(Cache(directory, cacheSize))
                .build()
    }
}