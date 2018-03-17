package com.levnovikov.data_movies.mappers

import com.levnovikov.core_api.responses.MovieDetailsResponse
import com.levnovikov.data_movies.entities.MovieDetails
import io.reactivex.functions.Function
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */
class MovieDetailsMapper @Inject constructor() : Function<MovieDetailsResponse, MovieDetails> {
    override fun apply(response: MovieDetailsResponse): MovieDetails =
            MovieDetails(
                    response.id,
                    response.title,
                    response.imgUrl,
                    response.desc,
                    response.date)
}