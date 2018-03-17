package com.levnovikov.feature_movie_details.activity_starter

import android.app.Activity
import android.content.Intent
import com.levnovikov.core_common.activity_starter.DetailsActivityStarter
import com.levnovikov.feature_movie_details.MOVIE_ID
import com.levnovikov.feature_movie_details.MovieDetailsActivity
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 15/3/18.
 */

class DetailsActivityStarterImpl @Inject constructor() : DetailsActivityStarter {

    override fun startDetailsActivity(activity: Activity, movieId: Int) {
        val intent = Intent(activity, MovieDetailsActivity::class.java).apply { putExtra(MOVIE_ID, movieId) }
        activity.startActivity(intent)
    }
}