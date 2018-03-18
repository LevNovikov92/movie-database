package com.levnovikov.feature_movies_list.main_screen

import android.app.Activity
import com.levnovikov.intermediate_common.activity_starter.DetailsActivityStarter
import dagger.Binds
import dagger.Module
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 15/3/18.
 */

@Module
interface NavigatorModule {
    @Binds fun bindNavigator(impl: NavigatorImpl): Navigator
}

interface Navigator {
    fun startMovieDetails(movieId: Int)
}

class NavigatorImpl @Inject constructor(
        private val detailsActivityStarter: DetailsActivityStarter,
        private val activity: Activity
) : Navigator {

    override fun startMovieDetails(movieId: Int) {
        detailsActivityStarter.startDetailsActivity(activity, movieId)
    }
}