package com.levnovikov.feature_movies_list.main_screen.movies_list

import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.data_movies.MoviesRepo
import com.levnovikov.data_movies.entities.PagerMetadata
import com.levnovikov.feature_movies_list.main_screen.DateStreamProvider
import com.levnovikov.feature_movies_list.main_screen.movies_list.di.MoviesListScope
import com.levnovikov.system_lifecycle.activity.Lifecycle
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 14/3/18.
 */

@MoviesListScope
class MoviesListPresenter @Inject constructor(
        private val moviesRepo: MoviesRepo,
        adapter: MoviesListAdapter,
        private val view: ListView,
        lifecycle: Lifecycle,
        asyncHelper: AsyncHelper,
        dateStreamProvider: DateStreamProvider
) : MovieVOLoader, PageLoadingListener {

    private val endlessScrollHandler: EndlessScrollHandler
            = EndlessScrollHandler(adapter, view, this, lifecycle, this, asyncHelper)

    init {
        lifecycle.subscribeUntilDestroy(dateStreamProvider.getDateStream()
                .compose(asyncHelper.observeInMain())
                .subscribe({
                    endlessScrollHandler.reloadData(if (it.isPresent) it.get() else null)
                }, { TODO() }))
    }

    override fun loadVO(page: Int, date: Date?): Single<Pair<List<MovieVO>, PagerMetadata>> =
            moviesRepo.getMoviesByDate(page, date)
                    .map { it.first.map { MovieVO(it.id, it.title) } to it.second }

    fun onScrolled() {
        endlessScrollHandler.onScroll()
    }

    fun getAdapter() = endlessScrollHandler.getAdapter()

    override fun onStartLoading() {
        view.showProgress()
    }

    override fun onLoaded() {
        view.hideProgress()
    }
}