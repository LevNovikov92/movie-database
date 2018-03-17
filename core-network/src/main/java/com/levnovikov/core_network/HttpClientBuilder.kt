package com.levnovikov.core_network

import com.levnovikov.core_network.interceptors.AuthInterceptor
import com.levnovikov.core_network.interceptors.RequestLimitErrorInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 12/3/18.
 */

class HttpClientBuilder @Inject constructor(
        private val authInterceptor: AuthInterceptor,
        private val loggingInterceptor: HttpLoggingInterceptor,
        private val requestLimitInterceptor: RequestLimitErrorInterceptor
) {

    fun build(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .addInterceptor(requestLimitInterceptor)
                .build()
    }
}