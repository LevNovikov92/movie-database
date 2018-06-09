package com.levnovikov.feature_list_of_films.movies_list.scroll_handler

import android.view.LayoutInflater
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.feature_list_of_films.movies_list.ListView
import com.levnovikov.feature_list_of_films.movies_list.MoviesListAdapterImpl
import com.levnovikov.system_lifecycle.activity.Lifecycle
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