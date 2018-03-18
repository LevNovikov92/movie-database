package com.levnovikov.feature_movie_details

import android.annotation.SuppressLint
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.support.annotation.VisibleForTesting
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

//Interface was introduced to hide VM methods and ban calls from xml
interface MovieDetailsViewModel {
    var progressVisibility: ObservableBoolean
    var detailsVisibility: ObservableBoolean
    var errorMessageVisibility: ObservableBoolean
    var data: ObservableField<MovieDetailsVO>
}

//Interface was introduced to allow to start VM from activity
interface ViewModelActions {
    fun onGetActive()
}

@MovieDetailsScope
class MovieDetailsViewModelImpl @Inject constructor(
        private val moviesRepo: MoviesRepo,
        private val lifecycle: Lifecycle,
        private val asyncHelper: AsyncHelper,
        @Named(MOVIE_ID) private val movieId: Int
) : MovieDetailsViewModel, ViewModelActions {

    override var progressVisibility = ObservableBoolean(false)
    override var detailsVisibility = ObservableBoolean(false)
    override var errorMessageVisibility = ObservableBoolean(false)
    override var data = ObservableField<MovieDetailsVO>()

    @SuppressLint("VisibleForTests")
    override fun onGetActive() {
        loadDetails()
    }

    @VisibleForTesting
    internal fun loadDetails() {
        showProgress(true)
        moviesRepo.getMovieDetails(movieId)
                .compose(asyncHelper.subscribeInIO()) //subscribe and observe in IO since we are using android dataBindings
                .map(MovieDetailsMapper())
                .subscribe({
                    data.set(it)
                    showProgress(false)
                }, { showError() })
                .let { lifecycle.subscribeUntilDestroy(it) } //dispose observable on activity onDestroy
    }

    @VisibleForTesting
    internal fun showProgress(show: Boolean) {
        progressVisibility.set(show)
        detailsVisibility.set(!show)
        errorMessageVisibility.set(false)
    }

    @VisibleForTesting
    internal fun showError() {
        showProgress(true)
        errorMessageVisibility.set(true)
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