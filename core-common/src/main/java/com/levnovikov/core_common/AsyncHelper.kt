package com.levnovikov.core_common

import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.SingleTransformer
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 15/3/18.
 */

class AsyncHelper @Inject constructor(
        private val mainScheduler: Scheduler,
        private val workingScheduler: Scheduler,
        private val ioScheduler: Scheduler
) {

    fun <Upstream> observeInMain(): ObservableTransformer<Upstream, Upstream> =
            ObservableTransformer {
                it.observeOn(mainScheduler)
            }

    fun <Upstream> async(): ObservableTransformer<Upstream, Upstream> =
            ObservableTransformer {
                it.subscribeOn(workingScheduler).observeOn(mainScheduler)
            }

    fun <Upstream> observeInMainSingle(): SingleTransformer<Upstream, Upstream> =
            SingleTransformer {
                it.observeOn(mainScheduler)
            }

    fun <Upstream> asyncCall(): SingleTransformer<Upstream, Upstream> =
            SingleTransformer {
                it.subscribeOn(ioScheduler).observeOn(mainScheduler)
            }
}