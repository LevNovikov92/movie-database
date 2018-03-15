package com.levnovikov.system_lifecycle.activity

import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*

/**
 * Author: lev.novikov
 * Date: 7/1/18.
 */

open class LifecycleActivity : AppCompatActivity(), Lifecycle {

    private val disposableMap = HashMap<LifecycleEvent, CompositeDisposable>()

    override fun subscribeUntil(event: LifecycleEvent, disposable: Disposable) {
        val compositeDisposable = disposableMap[event]
        if (compositeDisposable != null) {
            disposableMap[event] = CompositeDisposable()
        }

        disposableMap[event]?.add(disposable)
    }

    override fun subscribeUntilDestroy(disposable: Disposable) {
        subscribeUntil(LifecycleEvent.DESTROY, disposable)
    }

    override fun onStart() {
        super.onStart()
        dispose(LifecycleEvent.START)
    }

    override fun onResume() {
        super.onResume()
        dispose(LifecycleEvent.RESUME)
    }

    override fun onPause() {
        super.onPause()
        dispose(LifecycleEvent.PAUSE)
    }

    override fun onStop() {
        super.onStop()
        dispose(LifecycleEvent.STOP)
    }

    override fun onDestroy() {
        dispose(LifecycleEvent.DESTROY)
        super.onDestroy()
    }

    private fun dispose(event: LifecycleEvent) {
        val disposable = disposableMap[event]
        if (disposable != null) {
            disposable.dispose()
            disposableMap.remove(event)
        }
    }
}
