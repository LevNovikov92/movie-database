package com.levnovikov.data_movies.mappers

import com.levnovikov.core_api.responses.UpcomingMoviesResponse
import com.levnovikov.data_movies.entities.Movie
import io.reactivex.functions.Function

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */
class MovieMapper : Function<UpcomingMoviesResponse, List<Movie>> {
    override fun apply(response: UpcomingMoviesResponse): List<Movie> =
            response.results.map { Movie(it.id, it.title) }
}