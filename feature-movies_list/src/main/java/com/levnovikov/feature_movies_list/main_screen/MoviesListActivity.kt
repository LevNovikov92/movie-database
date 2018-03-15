package com.levnovikov.feature_movies_list.main_screen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.levnovikov.core_common.ComponentProvider
import com.levnovikov.core_common.getComponent
import com.levnovikov.feature_movies_list.R
import com.levnovikov.feature_movies_list.main_screen.di.DaggerMainComponent
import com.levnovikov.feature_movies_list.main_screen.di.MainComponent
import com.levnovikov.system_lifecycle.activity.LifecycleActivity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.reflect.KClass

class MoviesListActivity : LifecycleActivity(), ComponentProvider {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupDI()
    }

    private lateinit var component: MainComponent

    private fun setupDI() {
        component = DaggerMainComponent.builder()
                .activity(this)
                .dependency(application.getComponent())
                .build()
        component.inject(this)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <C : Any> getComponent(clazz: KClass<C>): C? =
            when(clazz) {
                MainComponent::class -> component as C
                else -> throw UnsupportedOperationException("Component ${clazz.simpleName} not found")
            }
}
