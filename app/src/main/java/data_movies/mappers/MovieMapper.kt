package data_movies.mappers

import core_api.responses.UpcomingMoviesResponse
import data_movies.entities.Movie
import data_movies.entities.PagerMetadata
import io.reactivex.functions.Function

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */
class MovieMapper : Function<UpcomingMoviesResponse, Pair<List<Movie>, PagerMetadata>> {
    override fun apply(response: UpcomingMoviesResponse): Pair<List<Movie>, PagerMetadata> =
            response.results.map { Movie(it.id, it.title) } to PagerMetadata(response.totalPages)
}