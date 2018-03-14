package com.levnovikov.feature_movies_list.main_screen.movies_list

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.levnovikov.core_common.ComponentProvider
import com.levnovikov.feature_movies_list.R
import com.levnovikov.feature_movies_list.main_screen.di.MainComponent
import javax.inject.Inject


/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */

interface ListView {
    fun getLastVisibleItemPosition(): Int
}

class MoviesListView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ListView {

    @Inject
    lateinit var presenter: MoviesListPresenter

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setupDI()
        initList()
    }

    private val layoutManager = LinearLayoutManager(context)

    private fun initList() {
        val recycler = findViewById<RecyclerView>(R.id.recycler_view)
        recycler.layoutManager = layoutManager
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                presenter.onScrolled()
            }
        })
        recycler.adapter = presenter.getAdapter()
    }

    private fun setupDI() {
        context.let{
            if (it is ComponentProvider) {
                it.getComponent(MainComponent::class)?.let {
                    it.moviesListBuilder()
                            .view(this)
                            .build().apply {
                                this.inject(this@MoviesListView)
                            }
                }
            }
        }
    }

    override fun getLastVisibleItemPosition(): Int = layoutManager.findLastVisibleItemPosition()
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var id: TextView = view.findViewById(R.id.movie_id)
    private var title: TextView = view.findViewById(R.id.title)

    fun bind(movieVO: MovieVO) {
        id.text = movieVO.id.toString()
        title.text = movieVO.title
    }
}