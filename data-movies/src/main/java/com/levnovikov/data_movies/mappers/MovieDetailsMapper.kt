package com.levnovikov.data_movies.mappers

import com.levnovikov.core_api.responses.MovieDetailsResponse
import io.reactivex.functions.Function
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */
class MovieDetailsMapper @Inject constructor() : Function<MovieDetailsResponse, com.levnovikov.data_movies.entities.MovieDetails> {
    override fun apply(response: MovieDetailsResponse): com.levnovikov.data_movies.entities.MovieDetails =
            com.levnovikov.data_movies.entities.MovieDetails(
                    response.id,
                    response.title,
                    response.imgUrl,
                    response.desc,
                    response.date)
}