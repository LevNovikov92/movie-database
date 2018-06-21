package movies_list

import activity.Lifecycle
import android.support.v7.widget.RecyclerView
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.core_common.mvp_mvvm.Active
import data_movies.MoviesRepo
import data_movies.entities.PagerMetadata
import io.reactivex.Single
import movies_list.di.MoviesListScope
import movies_list.scroll_handler.MovieVOLoader
import movies_list.scroll_handler.OnItemClick
import movies_list.scroll_handler.PageLoadingListener
import movies_list.scroll_handler.ScrollHandler
import movies_list.scroll_handler.ScrollHandlerFactory
import java.util.*
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 14/3/18.
 */

interface MoviesListPresenter : Active {
    fun onScrolled()
    fun getAdapter(): RecyclerView.Adapter<*>
}

@MoviesListScope
class MoviesListPresenterImpl @Inject constructor(
        private val moviesRepo: MoviesRepo,
        private val navigator: Navigator,
        private val view: ListView,
        scrollHandlerProvider: ScrollHandlerFactory,
        private val lifecycle: Lifecycle,
        private val asyncHelper: AsyncHelper
//        private val dateStreamProvider: DateStreamProvider
) : MoviesListPresenter, MovieVOLoader, PageLoadingListener, OnItemClick {

    private val scrollHandler: ScrollHandler
            = scrollHandlerProvider.getEndlessScrollHandler(this, this, this)

    override fun onItemClick(id: Int) = navigator.startMovieDetails(id)

    override fun onGetActive() {
//        lifecycle.subscribeUntilDestroy(dateStreamProvider.getDateStream()
//                .compose(asyncHelper.observeInMain())
//                .subscribe({
//                    scrollHandler.reloadData(if (it.isPresent) it.get() else null)
//                }, defaultError))
    }

    override fun loadVO(page: Int, date: Date?): Single<Pair<List<MovieVO>, PagerMetadata>> =
            moviesRepo.getMoviesByDate(page, date)
                    .map { it.first.map { MovieVO(it.id, it.title) } to it.second }

    override fun onScrolled() {
        scrollHandler.onScroll()
    }

    override fun getAdapter(): RecyclerView.Adapter<*> {
        return scrollHandler.getAdapter() as RecyclerView.Adapter<*>
    }

    override fun onStartLoading() {
        view.showProgress()
    }

    override fun onLoaded() {
        view.hideProgress()
    }

    private fun doNothingElseAgain() {

    }

    private fun doNothingMore() {

    }

    private fun doNothingElse() {

    }
}