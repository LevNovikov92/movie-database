package com.levnovikov.data_movies

import com.levnovikov.core_api.MovieApi
import com.levnovikov.core_api.responses.MovieResponse
import com.levnovikov.core_api.responses.UpcomingMoviesResponse
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import org.junit.Test
import java.util.*

/**
 * Author: lev.novikov
 * Date: 17/3/18.
 */
class MoviesRepoImplTest {

    @Test
    fun getLatestMovies() {
        val date = Date()
        val response = UpcomingMoviesResponse(1, listOf(MovieResponse(1, "title", date)), 1)
        val api = mock<MovieApi> { on { getUpcomingMovies(any()) } doReturn Single.just(response) }

        val repo = MoviesRepoImpl(api)
        val test = repo.getLatestMovies(1).test()
        test.assertNoErrors()
        test.assertValueAt(0, { result ->
            val list = result.first
            val metadata = result.second
            list.size == 1 &&
                    list[0].id == response.results[0].id &&
                    list[0].title == response.results[0].title &&
                    metadata.totalPages == response.totalPages
        })
    }

    @Test
    fun getMoviesByDate() {
        val date = GregorianCalendar(2017, 2, 2, 2, 22).time
        val response = UpcomingMoviesResponse(1, listOf(
                MovieResponse(1, "title", date),
                MovieResponse(2, "title", Date()),
                MovieResponse(3, "title", date),
                MovieResponse(4, "title", Date())), 1)
        val api = mock<MovieApi> { on { getUpcomingMovies(any()) } doReturn Single.just(response) }

        val repo = MoviesRepoImpl(api)
        val test = repo.getMoviesByDate(1,
                GregorianCalendar(2017, 2, 2, 1, 11).time)
                .test()
        test.assertNoErrors()
        test.assertValueAt(0, { result ->
            result.first.run {
                size == 2 &&
                this[0].id == 1 &&
                this[1].id == 3
            }
        })
    }
}