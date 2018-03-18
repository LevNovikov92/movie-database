package com.levnovikov.core_common

import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.SingleTransformer
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 15/3/18.
 */

interface AsyncHelper {
    fun <Upstream> observeInMain(): ObservableTransformer<Upstream, Upstream>
    fun <Upstream> async(): ObservableTransformer<Upstream, Upstream>
    fun <Upstream> observeInMainSingle(): SingleTransformer<Upstream, Upstream>
    fun <Upstream> asyncCall(): SingleTransformer<Upstream, Upstream>
    fun <Upstream> subscribeInIO(): SingleTransformer<Upstream, Upstream>
}

class AsyncHelperImpl @Inject constructor(
        private val mainScheduler: Scheduler,
        private val workingScheduler: Scheduler,
        private val ioScheduler: Scheduler
) : AsyncHelper {

    override fun <Upstream> observeInMain(): ObservableTransformer<Upstream, Upstream> =
            ObservableTransformer {
                it.observeOn(mainScheduler)
            }

    override fun <Upstream> async(): ObservableTransformer<Upstream, Upstream> =
            ObservableTransformer {
                it.subscribeOn(workingScheduler).observeOn(mainScheduler)
            }

    override fun <Upstream> observeInMainSingle(): SingleTransformer<Upstream, Upstream> =
            SingleTransformer {
                it.observeOn(mainScheduler)
            }

    override fun <Upstream> asyncCall(): SingleTransformer<Upstream, Upstream> =
            SingleTransformer {
                it.subscribeOn(ioScheduler).observeOn(mainScheduler)
            }

    override fun <Upstream> subscribeInIO(): SingleTransformer<Upstream, Upstream> =
            SingleTransformer {
                it.subscribeOn(ioScheduler)
            }
}