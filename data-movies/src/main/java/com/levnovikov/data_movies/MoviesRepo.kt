package com.levnovikov.data_movies

import com.levnovikov.core_api.MovieApi
import com.levnovikov.data_movies.entities.Movie
import com.levnovikov.data_movies.entities.MovieDetails
import com.levnovikov.data_movies.entities.PagerMetadata
import com.levnovikov.data_movies.mappers.MovieDetailsMapper
import com.levnovikov.data_movies.mappers.MovieMapper
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */
interface MoviesRepo {
    fun getLatestMovies(page: Int): Single<Pair<List<Movie>, PagerMetadata>>
    fun getMoviesByDate(page: Int, date: Date): Single<List<Movie>>
    fun getMovieDetails(id: Int): Single<MovieDetails>
}

class MoviesRepoImpl @Inject constructor(private val api: MovieApi) : MoviesRepo {
    override fun getLatestMovies(page: Int): Single<Pair<List<Movie>, PagerMetadata>> =
            api.getUpcomingMovies(page).map(MovieMapper())

    override fun getMoviesByDate(page: Int, date: Date): Single<List<Movie>> {
        TODO("not implemented")
    }

    override fun getMovieDetails(id: Int): Single<MovieDetails> =
            api.getMovieDetails(id).map(MovieDetailsMapper())

}