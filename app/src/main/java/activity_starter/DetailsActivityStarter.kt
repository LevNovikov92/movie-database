package activity_starter

import android.app.Activity

/**
 * Author: lev.novikov
 * Date: 18/3/18.
 */
interface DetailsActivityStarter {
    fun startDetailsActivity(activity: Activity, movieId: Int)
}