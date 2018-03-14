package com.levnovikov.feature_movies_list.main_screen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.levnovikov.core_common.ComponentProvider
import com.levnovikov.core_common.getComponent
import com.levnovikov.data_movies.MoviesRepo
import com.levnovikov.feature_movies_list.R
import com.levnovikov.feature_movies_list.main_screen.di.DaggerMainComponent
import com.levnovikov.feature_movies_list.main_screen.di.MainComponent
import dagger.Component
import javax.inject.Inject
import kotlin.reflect.KClass

class MoviesListActivity : AppCompatActivity(), ComponentProvider {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupDI()

        testCall()
    }


    @Inject lateinit var moviesRepo: MoviesRepo

    private fun testCall() {
        component.inject(this)
        moviesRepo.getLatestMovies(1)
                .subscribe({ response ->
                    Log.i(">>>>", response.toString())
                }, { e ->
                    e.printStackTrace()
                })
    }

    private lateinit var component: MainComponent

    private fun setupDI() {
        component = DaggerMainComponent.builder()
                .activity(this)
                .dependency(application.getComponent())
                .build()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <C : Any> getComponent(clazz: KClass<C>): C? =
            when(clazz) {
                MainComponent::class -> component as C
                else -> throw UnsupportedOperationException("Component ${clazz.simpleName} not found")
            }
}
