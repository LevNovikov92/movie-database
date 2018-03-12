package com.levnovikov.core_network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 12/3/18.
 */

internal class HttpClientBuilder @Inject constructor() {

    fun build(interceptors: List<Interceptor>): OkHttpClient {
        return OkHttpClient.Builder().apply {
            interceptors.forEach { this.addInterceptor(it) }
        }.build()
    }
}