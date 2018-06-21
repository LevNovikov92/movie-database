package core_network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Named


/**
 * Author: lev.novikov
 * Date: 12/3/18.
 */
class RetrofitBuilder @Inject constructor(
        private val client: OkHttpClient,
        private val gson: Gson,
        @Named(BASE_URL) private val baseUrl: String) {

    fun build(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}