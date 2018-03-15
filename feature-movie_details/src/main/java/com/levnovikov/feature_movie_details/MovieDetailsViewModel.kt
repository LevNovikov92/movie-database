package com.levnovikov.feature_movie_details

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.data_movies.MoviesRepo
import com.levnovikov.data_movies.entities.MovieDetails
import com.levnovikov.feature_movie_details.di.MovieDetailsScope
import com.levnovikov.system_lifecycle.activity.Lifecycle
import io.reactivex.functions.Function
import java.util.*
import javax.inject.Inject
import javax.inject.Named

/**
 * Author: lev.novikov
 * Date: 15/3/18.
 */

const val MOVIE_ID = "MOVIE_ID"

interface MovieDetailsViewModel {
    var progressVisibility: ObservableBoolean
    var detailsVisibility: ObservableBoolean
    var data: ObservableField<MovieDetailsVO>
}

@MovieDetailsScope
class MovieDetailsViewModelImpl @Inject constructor(
        private val moviesRepo: MoviesRepo,
        private val lifecycle: Lifecycle,
        private val asyncHelper: AsyncHelper,
        private val mapper: MovieDetailsMapper,
        @Named(MOVIE_ID) private val movieId: Int
) : MovieDetailsViewModel {

    override var progressVisibility = ObservableBoolean(false)
    override var detailsVisibility = ObservableBoolean(false)
    override var data = ObservableField<MovieDetailsVO>()

    init {
        loadDetails()
    }

    private fun loadDetails() {
        showProgress(true)
        moviesRepo.getMovieDetails(movieId)
                .compose(asyncHelper.asyncCall())
                .map(mapper)
                .subscribe({
                    data.set(it)
                    showProgress(false)
                }, { TODO() })
                .let { lifecycle.subscribeUntilDestroy(it) }
    }

    private fun showProgress(show: Boolean) {
        progressVisibility.set(show)
        detailsVisibility.set(!show)
    }
}

data class MovieDetailsVO(
        val title: String,
        val imgUrl: String,
        val desc: String,
        val date: Date)

class MovieDetailsMapper @Inject constructor(
) : Function<MovieDetails, MovieDetailsVO> {

    override fun apply(movie: MovieDetails): MovieDetailsVO =
            MovieDetailsVO(
                    movie.title,
                    movie.imgUrl,
                    movie.desc,
                    movie.date)
}