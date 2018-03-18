package com.levnovikov.feature_movies_list.main_screen.movies_list

import android.support.v7.widget.RecyclerView
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.core_common.defaultError
import com.levnovikov.core_common.mvp_mvvm.Active
import com.levnovikov.data_movies.MoviesRepo
import com.levnovikov.data_movies.entities.PagerMetadata
import com.levnovikov.feature_movies_list.main_screen.DateStreamProvider
import com.levnovikov.feature_movies_list.main_screen.Navigator
import com.levnovikov.feature_movies_list.main_screen.movies_list.di.MoviesListScope
import com.levnovikov.feature_movies_list.main_screen.movies_list.scroll_handler.ScrollHandlerFactory
import com.levnovikov.feature_movies_list.main_screen.movies_list.scroll_handler.MovieVOLoader
import com.levnovikov.feature_movies_list.main_screen.movies_list.scroll_handler.OnItemClick
import com.levnovikov.feature_movies_list.main_screen.movies_list.scroll_handler.PageLoadingListener
import com.levnovikov.feature_movies_list.main_screen.movies_list.scroll_handler.ScrollHandler
import com.levnovikov.system_lifecycle.activity.Lifecycle
import io.reactivex.Single
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
        private val asyncHelper: AsyncHelper,
        private val dateStreamProvider: DateStreamProvider
) : MoviesListPresenter, MovieVOLoader, PageLoadingListener, OnItemClick {

    private val scrollHandler: ScrollHandler
            = scrollHandlerProvider.getEndlessScrollHandler(this, this, this)

    override fun onItemClick(id: Int) = navigator.startMovieDetails(id)

    override fun onGetActive() {
        lifecycle.subscribeUntilDestroy(dateStreamProvider.getDateStream()
                .compose(asyncHelper.observeInMain())
                .subscribe({
                    scrollHandler.reloadData(if (it.isPresent) it.get() else null)
                }, defaultError))
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
}