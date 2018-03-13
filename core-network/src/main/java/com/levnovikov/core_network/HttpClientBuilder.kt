package com.levnovikov.core_network

import com.levnovikov.core_network.interceptors.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 12/3/18.
 */

class HttpClientBuilder @Inject constructor(
        private val authInterceptor: AuthInterceptor,
        private val loggingInterceptor: HttpLoggingInterceptor
) {

    fun build(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build()
    }
}