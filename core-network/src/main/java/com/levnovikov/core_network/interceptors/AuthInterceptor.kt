package com.levnovikov.core_network.interceptors

import com.levnovikov.core_network.API_KEY
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */
class AuthInterceptor @Inject constructor(@Named(API_KEY) private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url()
        val modRequest = request.newBuilder().url(
                url.newBuilder().addQueryParameter("api_key", apiKey).build())
                .build()
        return chain.proceed(modRequest)
    }
}