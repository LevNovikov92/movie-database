package com.levnovikov.core_network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

/**
 * Author: lev.novikov
 * Date: 12/3/18.
 */
class RetrofitBuilder @Inject constructor(
        private val client: OkHttpClient,
        @Named(BASE_URL) private val baseUrl: String) {

    fun build(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .build()
    }
}