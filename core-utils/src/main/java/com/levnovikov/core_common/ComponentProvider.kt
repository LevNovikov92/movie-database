package com.levnovikov.core_common

import android.app.Application
import kotlin.reflect.KClass

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */
interface ComponentProvider {
    fun <C : Any> getComponent(clazz: KClass<C>): C?
}

inline fun <reified T : Any> Application.getComponent(): T =
        (this as? ComponentProvider)?.getComponent(T::class) ?: throw UnsupportedOperationException("Application is not App class")
