package com.levnovikov.movie_database.main_screen

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.levnovikov.core_api.MovieApi
import com.levnovikov.movie_database.R
import com.levnovikov.movie_database.getComponent
import com.levnovikov.movie_database.main_screen.di.DaggerMainComponent
import com.levnovikov.movie_database.main_screen.di.MainComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupDI()

        testCall()
    }


    @Inject lateinit var api: MovieApi

    private fun testCall() {
        component.inject(this)
        api.getUpcomingMovies(1)
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
}
