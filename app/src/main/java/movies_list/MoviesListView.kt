package movies_list

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.levnovikov.core_common.ComponentProvider
import com.levnovikov.movie_database.R
import feature_list_of_films_interface.MoviesListDependency
import movies_list.di.DaggerMoviesListComponent
import movies_list.scroll_handler.OnItemClick
import javax.inject.Inject


/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */

interface ListView {
    fun getLastVisibleItemPosition(): Int
    fun showProgress()
    fun hideProgress()
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
        presenter.onGetActive()
    }

    private val layoutManager = LinearLayoutManager(context)
    private lateinit var progressBar: View

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
        progressBar = findViewById(R.id.progress)
    }

    private fun setupDI() {
        context.let {
            if (it is ComponentProvider) {
                DaggerMoviesListComponent.builder()
                        .view(this)
                        .dependency(it.getComponent(MoviesListDependency::class)!!)
                        .build().apply {
                    this.inject(this@MoviesListView)
                }
            }
        }
    }

    override fun getLastVisibleItemPosition(): Int = layoutManager.findLastVisibleItemPosition()

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var id: TextView = view.findViewById(R.id.movie_id)
    private var title: TextView = view.findViewById(R.id.title)

    fun bind(movieVO: MovieVO, listener: OnItemClick?) {
        id.text = movieVO.id.toString()
        title.text = movieVO.title
        itemView.setOnClickListener {
            listener?.onItemClick(movieVO.id)
        }
    }
}