package feature_movie_details

import activity.Lifecycle
import android.annotation.SuppressLint
import com.levnovikov.core_common.AsyncHelper
import data_movies.MoviesRepo
import data_movies.entities.MovieDetails
import feature_movie_details.di.MovieDetailsScope
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
//    var progressVisibility: ObservableBoolean
//    var detailsVisibility: ObservableBoolean
//    var errorMessageVisibility: ObservableBoolean
//    var data: ObservableField<MovieDetailsVO>
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
//
//    override var progressVisibility = ObservableBoolean(false)
//    override var detailsVisibility = ObservableBoolean(false)
//    override var errorMessageVisibility = ObservableBoolean(false)
//    override var data = ObservableField<MovieDetailsVO>()

    @SuppressLint("VisibleForTests")
    override fun onGetActive() {
//        loadDetails()
    }
//
//    @VisibleForTesting
//    internal fun loadDetails() {
//        showProgress(true)
//        moviesRepo.getMovieDetails(movieId)
//                .compose(asyncHelper.asyncCall())
//                .map(MovieDetailsMapper())
//                .subscribe({
//                    data.set(it)
//                    showProgress(false)
//                }, { showError() })
//                .let { lifecycle.subscribeUntilDestroy(it) } //dispose observable on activity onDestroy
//    }
//
//    @VisibleForTesting
//    internal fun showProgress(show: Boolean) {
//        progressVisibility.set(show)
//        detailsVisibility.set(!show)
//        errorMessageVisibility.set(false)
//    }
//
//    @VisibleForTesting
//    internal fun showError() {
//        showProgress(true)
//        errorMessageVisibility.set(true)
//    }
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