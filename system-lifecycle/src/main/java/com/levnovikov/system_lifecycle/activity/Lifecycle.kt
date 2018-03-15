package com.levnovikov.system_lifecycle.activity

import io.reactivex.disposables.Disposable

/**
 * Author: lev.novikov
 * Date: 2/1/18.
 */

interface Lifecycle {
    fun subscribeUntil(event: LifecycleEvent, disposable: Disposable)
    fun subscribeUntilDestroy(disposable: Disposable)
}
