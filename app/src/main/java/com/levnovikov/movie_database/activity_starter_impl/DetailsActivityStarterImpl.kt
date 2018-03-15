package com.levnovikov.movie_database.activity_starter_impl

import android.content.Intent
import com.levnovikov.core_common.ActivityStarter
import com.levnovikov.core_common.activity_starter.DetailsActivityStarter
import com.levnovikov.feature_movie_details.MOVIE_ID
import com.levnovikov.feature_movie_details.MovieDetailsActivity
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 15/3/18.
 */


class DetailsActivityStarterImpl @Inject constructor() : DetailsActivityStarter {

    override fun startDetailsActivity(activityStarter: ActivityStarter, movieId: Int) {
        val intent = activityStarter.getIntent(MovieDetailsActivity::class.java).apply { putExtra(MOVIE_ID, movieId) }
        activityStarter.startActivity(intent)
    }
}