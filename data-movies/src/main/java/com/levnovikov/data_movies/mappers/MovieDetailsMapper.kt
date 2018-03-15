package com.levnovikov.data_movies.mappers

import com.levnovikov.core_api.entities.MovieDetails
import io.reactivex.functions.Function
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */
class MovieDetailsMapper @Inject constructor() : Function<MovieDetails, com.levnovikov.data_movies.entities.MovieDetails> {
    override fun apply(response: MovieDetails): com.levnovikov.data_movies.entities.MovieDetails =
            com.levnovikov.data_movies.entities.MovieDetails(
                    response.id,
                    response.title,
                    response.imgUrl,
                    response.desc,
                    response.date)
}