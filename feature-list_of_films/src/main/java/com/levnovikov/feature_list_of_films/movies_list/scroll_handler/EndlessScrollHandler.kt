package com.levnovikov.feature_list_of_films.movies_list.scroll_handler

import android.annotation.SuppressLint
import android.support.annotation.MainThread
import android.support.annotation.VisibleForTesting
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.core_common.defaultError
import com.levnovikov.data_movies.entities.PagerMetadata
import com.levnovikov.feature_list_of_films.movies_list.ListView
import com.levnovikov.feature_list_of_films.movies_list.MovieVO
import com.levnovikov.feature_list_of_films.movies_list.MoviesListAdapter
import com.levnovikov.system_lifecycle.activity.Lifecycle
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.*
import javax.inject.Inject


interface MovieVOLoader {
    fun loadVO(page: Int, date: Date?): Single<Pair<List<MovieVO>, PagerMetadata>>
}

interface PageLoadingListener {
    fun onStartLoading()
    fun onLoaded()
}

interface OnItemClick {
    fun onItemClick(id: Int)
}

class EndlessScrollHandler @Inject constructor(
        private val adapter: MoviesListAdapter,
        private val view: ListView,
        private val movieLoader: MovieVOLoader,
        private val lifecycle: Lifecycle,
        private val pageLoadingListener: PageLoadingListener,
        private val asyncHelper: AsyncHelper
) : ScrollHandler {

    companion object {
        private const val MIN_OFFSET = 45 //Make offset bigger to make smooth scrolling
    }

    private var currentPage = 0
    private var totalPages = 1
    private var loadingInProgress = false //should be modified in mainThread only

    private var date: Date? = null

    @SuppressLint("VisibleForTests")
    @MainThread
    override fun reloadData(date: Date?) {
        this.date = date
        resetHandlerState()
        pageLoadingListener.onStartLoading()
        loadNextPage()
    }

    private fun resetHandlerState() {
        currentPage = 0
        totalPages = 1
        loadingInProgress = false
        pageLoaderDisposable?.dispose()
        adapter.clearData()
    }

    private var pageLoaderDisposable: Disposable? = null

    @MainThread
    @VisibleForTesting
    internal fun loadNextPage() {
        Timber.d(">>>> LoadNewData (loading started): currentPage = $currentPage")
        loadingInProgress = true
        pageLoaderDisposable = movieLoader.loadVO(++currentPage, date)
                .compose(asyncHelper.asyncCall())
                .subscribe({
                    Timber.d(">>>> LoadNewData (loading finished): currentPage = ${currentPage - 1}")
                    adapter.addItems(it.first)
                    totalPages = it.second.totalPages
                    loadingInProgress = false
                    onScroll() //check conditions and load more data if needed
                    pageLoadingListener.onLoaded()
                }, defaultError)
                .apply { lifecycle.subscribeUntilDestroy(this) }
    }

    @SuppressLint("VisibleForTests")
    override fun onScroll() {
        if (!loadingInProgress &&
                currentPage < totalPages &&
                adapter.itemsCount() - view.getLastVisibleItemPosition() <= MIN_OFFSET) {
            loadNextPage()
        }
    }

    override fun getAdapter() = adapter
}