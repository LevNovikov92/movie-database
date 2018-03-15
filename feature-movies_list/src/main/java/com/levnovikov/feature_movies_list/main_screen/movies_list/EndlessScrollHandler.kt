package com.levnovikov.feature_movies_list.main_screen.movies_list

import android.support.annotation.MainThread
import android.util.Log
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.data_movies.entities.PagerMetadata
import com.levnovikov.system_lifecycle.activity.Lifecycle
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import java.util.*
import javax.inject.Inject


interface MovieVOLoader {
    fun loadVO(page: Int, date: Date?): Single<Pair<List<MovieVO>, PagerMetadata>>
}

interface PageLoadingListener {
    fun onStartLoading()
    fun onLoaded()
}

class EndlessScrollHandler @Inject constructor(
        private val adapter: MoviesListAdapter,
        private val view: ListView,
        private val movieLoader: MovieVOLoader,
        private val lifecycle: Lifecycle,
        private val pageLoadingListener: PageLoadingListener,
        private val asyncHelper: AsyncHelper
) {

    companion object {
        private const val MIN_OFFSET = 45 //Make offset bigger to make smooth scrolling
    }

    private var currentPage = -1
    private var totalPages = -1
    private var loadingInProgress = false

    private var date: Date? = null

    fun reloadData(date: Date?) {
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
    private fun loadNextPage() {
        Log.i(">>>>", "LoadNewData (loading started): currentPage = $currentPage") //TODO improve logging
        loadingInProgress = true
        pageLoaderDisposable = movieLoader.loadVO(++currentPage, date)
                .compose(asyncHelper.asyncCall())
                .subscribe({
                    Log.i(">>>>", "LoadNewData (loading finished): currentPage = ${currentPage - 1}") //TODO improve logging
                    adapter.addItems(it.first)
                    totalPages = it.second.totalPages
                    loadingInProgress = false
                    onScroll() //check conditions and load more data if needed
                    pageLoadingListener.onLoaded()
                }, {  }) //TODO handle error, handle 429: make next request with exponential delay
                .apply { lifecycle.subscribeUntilDestroy(this) }
    }

    fun onScroll() {
        if (!loadingInProgress &&
                currentPage < totalPages &&
                adapter.itemsCount() - view.getLastVisibleItemPosition() <= MIN_OFFSET) {
            loadNextPage()
        }
    }

    fun getAdapter() = adapter
}