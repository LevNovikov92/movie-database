package com.levnovikov.feature_movies_list.main_screen

import android.app.Activity
import com.levnovikov.core_common.activity_starter.DetailsActivityStarter
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 15/3/18.
 */
class Navigator @Inject constructor(
        private val detailsActivityStarter: DetailsActivityStarter,
        private val activity: Activity
) {

    fun startMovieDetails(movieId: Int) {
        detailsActivityStarter.startDetailsActivity(activity, movieId)
    }
}