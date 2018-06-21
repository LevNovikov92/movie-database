package feature_list_of_films_interface

import activity.Lifecycle
import activity_starter.DetailsActivityStarter
import android.app.Activity
import android.view.LayoutInflater
import com.levnovikov.core_common.AsyncHelper
import data_movies.MoviesRepo

interface MoviesListDependency {
    fun moviesRepo(): MoviesRepo
    fun detailsActivityStarter(): DetailsActivityStarter
    fun activity(): Activity
    fun lifecycle(): Lifecycle
    fun asyncHelper(): AsyncHelper
    fun layoutInflater(): LayoutInflater
}