package main_screen

import activity.LifecycleActivity
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import com.levnovikov.core_common.ComponentProvider
import com.levnovikov.core_common.getComponent
import com.levnovikov.movie_database.R
import feature_list_of_films_interface.MoviesListDependency
import kotlinx.android.parcel.Parcelize
import main_screen.di.DaggerMainComponent
import main_screen.di.MainComponent
import java.util.*
import javax.inject.Inject
import kotlin.reflect.KClass

class MoviesListActivity : LifecycleActivity(), ComponentProvider {

    @Inject
    lateinit var moviesScreenRepo: MoviesScreenRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupDI(restoreState(savedInstanceState))
    }

    private fun restoreState(savedInstanceState: Bundle?): Date? =
            savedInstanceState?.getParcelable<MoviesListState>(MOVIES_LIST_STATE)?.date

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelable(MOVIES_LIST_STATE,
                MoviesListState(moviesScreenRepo.getLatestValue()))
        super.onSaveInstanceState(outState)
    }

    private lateinit var component: MainComponent

    private fun setupDI(date: Date?) {
        component = DaggerMainComponent.builder()
                .activity(this)
                .dateByDefault(date)
                .dependency(application.getComponent())
                .build()
        component.inject(this)
    }

   private  fun doNothing() {

    }

    @Suppress("UNCHECKED_CAST")
    override fun <C : Any> getComponent(clazz: KClass<C>): C? =
            when(clazz) {
                MoviesListDependency::class -> component as C
                MainComponent::class -> component as C
                else -> throw UnsupportedOperationException("Component ${clazz.simpleName} not found")
            }
}

private const val MOVIES_LIST_STATE = "MOVIES_LIST_STATE"

@SuppressLint("ParcelCreator")
@Parcelize
data class MoviesListState(val date: Date?): Parcelable
