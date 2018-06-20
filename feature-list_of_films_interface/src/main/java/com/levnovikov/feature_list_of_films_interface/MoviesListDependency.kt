package com.levnovikov.feature_list_of_films_interface

import android.app.Activity
import android.view.LayoutInflater
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.data_movies.MoviesRepo
import com.levnovikov.intermediate_common.activity_starter.DetailsActivityStarter
import com.levnovikov.system_lifecycle.activity.Lifecycle

interface MoviesListDependency {
    fun moviesRepo(): MoviesRepo
    fun detailsActivityStarter(): DetailsActivityStarter
    fun activity(): Activity
    fun lifecycle(): Lifecycle
    fun asyncHelper(): AsyncHelper
    fun layoutInflater(): LayoutInflater
}