package movies_list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.levnovikov.movie_database.R
import movies_list.scroll_handler.OnItemClick
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */

interface MoviesListAdapter {
    fun addItems(items: List<MovieVO>)
    fun clearData()
    fun itemsCount(): Int
}

class MoviesListAdapterImpl @Inject constructor(
        private val inflater: LayoutInflater,
        private val listener: OnItemClick
) : RecyclerView.Adapter<ViewHolder>(), MoviesListAdapter {

    override fun addItems(items: List<MovieVO>) {
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    private val data: MutableList<MovieVO> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(inflater.inflate(R.layout.movie_item, parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = data[position]
        holder.bind(itemData, listener)
    }

    override fun itemsCount(): Int = data.size
}

data class MovieVO(val id: Int, val title: String)