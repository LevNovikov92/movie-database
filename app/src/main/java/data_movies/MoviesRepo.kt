package data_movies

import core_api.MovieApi
import data_movies.entities.Movie
import data_movies.entities.MovieDetails
import data_movies.entities.PagerMetadata
import data_movies.mappers.MovieDetailsMapper
import data_movies.mappers.MovieMapper
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */
interface MoviesRepo {
    fun getLatestMovies(page: Int): Single<Pair<List<Movie>, PagerMetadata>>
    fun getMoviesByDate(page: Int, date: Date?): Single<Pair<List<Movie>, PagerMetadata>>
    fun getMovieDetails(id: Int): Single<MovieDetails>
}

class MoviesRepoImpl @Inject constructor(private val api: MovieApi) : MoviesRepo {
    override fun getLatestMovies(page: Int): Single<Pair<List<Movie>, PagerMetadata>> =
            api.getUpcomingMovies(page)
                    .map(MovieMapper())

    override fun getMoviesByDate(page: Int, date: Date?): Single<Pair<List<Movie>, PagerMetadata>> =
            api.getUpcomingMovies(page)
                    .map {
//                        if (date != null) it.copy(results = it.results.filter { equalWithoutTime(it.date, date) })
//                        else it
                        it
                    }
                    .map(MovieMapper())

    override fun getMovieDetails(id: Int): Single<MovieDetails> =
            api.getMovieDetails(id).map(MovieDetailsMapper())

}