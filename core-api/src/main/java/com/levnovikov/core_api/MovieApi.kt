package com.levnovikov.core_api

import com.levnovikov.core_api.entities.MovieDetails
import com.levnovikov.core_api.responses.UpcomingMoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */

interface MovieApi {

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("page") page: Int): Single<UpcomingMoviesResponse>

    @GET("/movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>
}