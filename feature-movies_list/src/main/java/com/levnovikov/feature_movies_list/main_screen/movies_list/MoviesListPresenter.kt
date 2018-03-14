package com.levnovikov.feature_movies_list.main_screen.movies_list

import android.util.Log
import com.levnovikov.data_movies.MoviesRepo
import com.levnovikov.data_movies.entities.PagerMetadata
import com.levnovikov.feature_movies_list.main_screen.DateStreamProvider
import com.levnovikov.feature_movies_list.main_screen.movies_list.di.MoviesListScope
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
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
        view: ListView,
        dateStreamProvider: DateStreamProvider
) : MovieVOLoader {

    private val endlessScrollHandler: EndlessScrollHandler = EndlessScrollHandler(adapter, view, this)

    init {
        dateStreamProvider.getDateStream() //TODO unsubscribe
                .subscribe({
                    endlessScrollHandler.reloadData(if (it.isPresent) it.get() else null)
                }, { TODO() })
    }


    override fun loadVO(page: Int, date: Date?): Single<Pair<List<MovieVO>, PagerMetadata>> =
            moviesRepo.getMoviesByDate(page, date)
                    .map { it.first.map { MovieVO(it.id, it.title) } to it.second }

    fun onScrolled() {
        endlessScrollHandler.onScroll()
    }

    fun getAdapter() = endlessScrollHandler.getAdapter()
}

interface MovieVOLoader {
    fun loadVO(page: Int, date: Date?): Single<Pair<List<MovieVO>, PagerMetadata>>
}

class EndlessScrollHandler @Inject constructor(
        private val adapter: MoviesListAdapter,
        private val view: ListView,
        private val movieLoader: MovieVOLoader
) {

    companion object {
        private const val MIN_OFFSET = 45 //Make offset bigger to make smooth scrolling
    }

    private var currentPage = 0
    private var totalPages = 1
    private var loadingInProgress = false

    private var date: Date? = null

    fun reloadData(date: Date?) {
        this.date = date
        adapter.clearData()
        currentPage = 0
        totalPages = 1
        loadingInProgress = false
        loadNextPage()
    }

    private fun loadNextPage() {
        Log.i(">>>>", "LoadNewData (loading started): currentpage = $currentPage") //TODO improve logging
        loadingInProgress = true
        movieLoader.loadVO(++currentPage, date) //TODO unsubscribe
                .observeOn(AndroidSchedulers.mainThread()) //TODO provide scheduler by DI
                .subscribe({
                    Log.i(">>>>", "LoadNewData (loading finished): currentpage = ${currentPage - 1}") //TODO improve logging
                    adapter.addItems(it.first)
                    totalPages = it.second.totalPages
                    loadingInProgress = false
                    onScroll() //check conditions and load more data if needed
                }, {  }) //TODO handle error, handle 429: make next request with exponential delay
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