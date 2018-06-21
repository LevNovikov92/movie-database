package movies_list

import activity_starter.DetailsActivityStarter
import android.app.Activity
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