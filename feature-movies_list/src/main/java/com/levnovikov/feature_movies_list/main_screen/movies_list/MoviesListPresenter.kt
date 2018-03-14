package com.levnovikov.feature_movies_list.main_screen.movies_list

import android.util.Log

import com.levnovikov.data_movies.MoviesRepo
import com.levnovikov.data_movies.entities.PagerMetadata
import com.levnovikov.feature_movies_list.main_screen.movies_list.di.MoviesListScope
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
/**
 * Author: lev.novikov
 * Date: 14/3/18.
 */

@MoviesListScope
class MoviesListPresenter @Inject constructor(
        private val moviesRepo: MoviesRepo,
        adapter: MoviesListAdapter,
        view: ListView
) : MovieVOLoader {

    private val endlessScrollHandler: EndlessScrollHandler = EndlessScrollHandler(adapter, view, this)

    override fun loadVO(page: Int): Single<Pair<List<MovieVO>, PagerMetadata>> =
            moviesRepo.getLatestMovies(page)
                    .map { it.first.map { MovieVO(it.id, it.title) } to it.second }

    fun onScrolled() {
        endlessScrollHandler.onScroll()
    }

    fun getAdapter() = endlessScrollHandler.getAdapter()
}

interface MovieVOLoader {
    fun loadVO(page: Int): Single<Pair<List<MovieVO>, PagerMetadata>>
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

    init {
        adapter.clearData()
        loadNextPage()
    }

    private fun loadNextPage() {
        Log.i(">>>>", "LoadNewData (loading started): currentpage = $currentPage")
        loadingInProgress = true
        movieLoader.loadVO(++currentPage) //TODO unsubscribe
                .subscribeOn(Schedulers.trampoline())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.i(">>>>", "LoadNewData (loading finished): currentpage = ${currentPage - 1}")
                    adapter.addItems(it.first)
                    totalPages = it.second.totalPages
                    loadingInProgress = false
                }, { TODO() })
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