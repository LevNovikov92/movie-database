package com.levnovikov.feature_movies_list.main_screen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.levnovikov.core_common.ComponentProvider
import com.levnovikov.core_common.getComponent
import com.levnovikov.feature_movies_list.R
import com.levnovikov.feature_movies_list.main_screen.di.DaggerMainComponent
import com.levnovikov.feature_movies_list.main_screen.di.MainComponent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.reflect.KClass

class MoviesListActivity : AppCompatActivity(), ComponentProvider {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupDI()
    }

    private lateinit var component: MainComponent

    @Inject lateinit var presenter: MoviesScreenRepo //TODO remove
    private fun setupDI() {
        component = DaggerMainComponent.builder()
                .activity(this)
                .dependency(application.getComponent())
                .build()
        component.inject(this)

//        Single.just(true).delay(10, TimeUnit.SECONDS) //TODO remove
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    presenter.onDateSelected(GregorianCalendar().apply { set(2018, 2, 9) }.time) }, {})
    }

    @Suppress("UNCHECKED_CAST")
    override fun <C : Any> getComponent(clazz: KClass<C>): C? =
            when(clazz) {
                MainComponent::class -> component as C
                else -> throw UnsupportedOperationException("Component ${clazz.simpleName} not found")
            }
}
