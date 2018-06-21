package movies_list.scroll_handler

import movies_list.MoviesListAdapter
import java.util.*

interface ScrollHandler {
    fun reloadData(date: Date?)
    fun onScroll()
    fun getAdapter(): MoviesListAdapter
}