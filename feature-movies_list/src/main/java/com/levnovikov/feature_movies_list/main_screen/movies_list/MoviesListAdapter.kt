package com.levnovikov.feature_movies_list.main_screen.movies_list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.levnovikov.feature_movies_list.R
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */

class MoviesListAdapter @Inject constructor(
        private val inflater: LayoutInflater
) : RecyclerView.Adapter<ViewHolder>() {

    fun addItems(items: List<MovieVO>) {
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    private val data: MutableList<MovieVO> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
        ViewHolder(inflater.inflate(R.layout.movie_item, parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun itemsCount(): Int = data.size
}

data class MovieVO(val id: Int, val title: String)