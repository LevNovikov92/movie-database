package com.levnovikov.feature_movie_details

import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.data_movies.MoviesRepo
import com.levnovikov.data_movies.entities.MovieDetails
import com.levnovikov.system_lifecycle.activity.Lifecycle
import com.levnovikov.system_lifecycle.activity.LifecycleEvent
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import io.reactivex.disposables.Disposable
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Author: lev.novikov
 * Date: 17/3/18.
 */
class MovieDetailsViewModelImplTest {
    @Test
    fun loadDetails() {
        val details = MovieDetails(1, "title", "url", "desc", Date())
        val repo = mock<MoviesRepo> {
            on { getMovieDetails(any()) } doReturn Single.just(details) }
        val lifecycle = object : Lifecycle {
            override fun subscribeUntil(event: LifecycleEvent, disposable: Disposable) {}
            override fun subscribeUntilDestroy(disposable: Disposable) {}
        }
        val asyncHelper = mock<AsyncHelper> { on { subscribeInIO<Any>() } doReturn testTransformer }
        val vm = MovieDetailsViewModelImpl(repo, lifecycle, asyncHelper, 0)

        vm.loadDetails()

        Assert.assertTrue(vm.data.get().title == details.title)
        Assert.assertTrue(vm.data.get().desc == details.desc)
        Assert.assertTrue(vm.data.get().imgUrl == details.imgUrl)

        Assert.assertTrue(vm.detailsVisibility.get())
        Assert.assertFalse(vm.progressVisibility.get())
        Assert.assertFalse(vm.errorMessageVisibility.get())
    }

    @Test
    fun showProgress() {
        val vm = MovieDetailsViewModelImpl(mock(), mock(), mock(), 0)
        vm.showProgress(true)
        Assert.assertFalse(vm.errorMessageVisibility.get())
        Assert.assertTrue(vm.progressVisibility.get())
        Assert.assertFalse(vm.detailsVisibility.get())

        vm.showProgress(false)
        Assert.assertFalse(vm.errorMessageVisibility.get())
        Assert.assertFalse(vm.progressVisibility.get())
        Assert.assertTrue(vm.detailsVisibility.get())
    }

    @Test
    fun showError() {
        val vm = MovieDetailsViewModelImpl(mock(), mock(), mock(), 0)
        vm.showError()
        Assert.assertTrue(vm.errorMessageVisibility.get())
        Assert.assertTrue(vm.progressVisibility.get())
        Assert.assertFalse(vm.detailsVisibility.get())
    }

}

val testTransformer = SingleTransformer<Any, Any> { upstream -> upstream }