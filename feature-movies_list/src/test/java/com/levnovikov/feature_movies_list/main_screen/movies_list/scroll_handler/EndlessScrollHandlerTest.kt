package com.levnovikov.feature_movies_list.main_screen.movies_list.scroll_handler

import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.data_movies.entities.PagerMetadata
import com.levnovikov.feature_movies_list.main_screen.movies_list.ListView
import com.levnovikov.feature_movies_list.main_screen.movies_list.MovieVO
import com.levnovikov.feature_movies_list.main_screen.movies_list.MoviesListAdapter
import com.levnovikov.system_lifecycle.activity.Lifecycle
import com.levnovikov.system_lifecycle.activity.LifecycleEvent
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.disposables.Disposable
import junit.framework.Assert
import org.junit.Test
import java.util.*

/**
 * Author: lev.novikov
 * Date: 18/3/18.
 */
class EndlessScrollHandlerTest {
    @Test
    fun loadNextPage_loadOnePage() {
        val adapter = mock<MoviesListAdapter>()
        doNothing().whenever(adapter).addItems(any())
        val movieLoader = object : MovieVOLoader {
            override fun loadVO(page: Int, date: Date?) = Single.just(getData(1))
        }
        val lifecycle = object : Lifecycle {
            override fun subscribeUntil(event: LifecycleEvent, disposable: Disposable) {}
            override fun subscribeUntilDestroy(disposable: Disposable) {}
        }
        val view = mock<ListView>()
        val pageLoadingListener = mock<PageLoadingListener>()
        val handler = EndlessScrollHandler(adapter, view, movieLoader, lifecycle, pageLoadingListener, testAsyncHelper)

        handler.loadNextPage()

        verify(adapter).addItems(any())
        verify(pageLoadingListener).onLoaded()
    }

    @Test
    fun loadNextPage_loadSeveralPages() {
        val adapter = object : MoviesListAdapter {

            private val data = mutableListOf<MovieVO>()
            override fun addItems(items: List<MovieVO>) {
                data.addAll(items)
            }

            override fun clearData() {
                data.clear()
            }

            override fun itemsCount(): Int = data.size
        }

        val movieLoader = object : MovieVOLoader {
            override fun loadVO(page: Int, date: Date?) = Single.just(getData(20))
        }
        val lifecycle = object : Lifecycle {
            override fun subscribeUntil(event: LifecycleEvent, disposable: Disposable) {}
            override fun subscribeUntilDestroy(disposable: Disposable) {}
        }
        val view = mock<ListView> { on { getLastVisibleItemPosition() } doReturn 10 }
        val pageLoadingListener = mock<PageLoadingListener>()
        val handler = EndlessScrollHandler(adapter, view, movieLoader, lifecycle, pageLoadingListener, testAsyncHelper)

        handler.loadNextPage()

        verify(pageLoadingListener, times(3)).onLoaded()
    }

    fun getData(totalPages: Int): Pair<List<MovieVO>, PagerMetadata> {
        val list = mutableListOf<MovieVO>()
        for (i in 1..20) {
            list.add(MovieVO(1, "title"))
        }
        return list to PagerMetadata(totalPages)
    }
}

val testAsyncHelper = object : AsyncHelper {
    override fun <Upstream> observeInMain() = ObservableTransformer<Upstream, Upstream> { upstream -> upstream }

    override fun <Upstream> async() = ObservableTransformer<Upstream, Upstream> { upstream -> upstream }

    override fun <Upstream> observeInMainSingle() = SingleTransformer<Upstream, Upstream> { upstream -> upstream }

    override fun <Upstream> asyncCall() = SingleTransformer<Upstream, Upstream> { upstream -> upstream }

    override fun <Upstream> subscribeInIO() = SingleTransformer<Upstream, Upstream> { upstream -> upstream }
}