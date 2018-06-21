package movies_list.scroll_handler

import android.view.LayoutInflater
import com.levnovikov.core_common.AsyncHelper
import activity.Lifecycle
import movies_list.ListView
import movies_list.MoviesListAdapterImpl
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 18/3/18.
 */
class ScrollHandlerFactory @Inject constructor(
        private val lifecycle: Lifecycle,
        private val asyncHelper: AsyncHelper,
        private val inflater: LayoutInflater,
        private val view: ListView
) {

    fun getEndlessScrollHandler(
            onItemClickListener: OnItemClick,
            movieLoader: MovieVOLoader,
            pageLoadingListener: PageLoadingListener
    ): ScrollHandler =
            EndlessScrollHandler(MoviesListAdapterImpl(inflater, onItemClickListener), view,
                    movieLoader, lifecycle, pageLoadingListener, asyncHelper)
}