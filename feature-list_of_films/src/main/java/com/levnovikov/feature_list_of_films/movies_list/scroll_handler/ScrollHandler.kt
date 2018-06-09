package com.levnovikov.feature_list_of_films.movies_list.scroll_handler

import com.levnovikov.feature_list_of_films.movies_list.MoviesListAdapter
import java.util.*

interface ScrollHandler {
    fun reloadData(date: Date?)
    fun onScroll()
    fun getAdapter(): MoviesListAdapter
}