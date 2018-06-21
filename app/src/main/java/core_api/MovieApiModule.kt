package core_api

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */

@Module
class MovieApiModule {
    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)
}