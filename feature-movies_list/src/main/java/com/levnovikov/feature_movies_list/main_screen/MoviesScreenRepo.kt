package com.levnovikov.feature_movies_list.main_screen

import com.google.common.base.Optional
import com.levnovikov.feature_date_selector.date_selector.dependencies.DateStreamProvider
import com.levnovikov.feature_date_selector.date_selector.dependencies.OnDateSelectedListener
import com.levnovikov.feature_movies_list.main_screen.di.MainScope
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 14/3/18.
 */

@MainScope
class MoviesScreenRepo @Inject constructor(dateByDefault: Date?) : OnDateSelectedListener, DateStreamProvider {

    private val dateStream = BehaviorSubject.createDefault<Optional<Date>>(Optional.fromNullable(dateByDefault))
    private var latestValue: Date? = dateByDefault

    override fun onDateSelected(date: Date?) {
        latestValue = date
        dateStream.onNext(Optional.fromNullable(date))
    }

    override fun getDateStream(): Observable<Optional<Date>> = dateStream.distinctUntilChanged()

    override fun getLatestValue(): Date? = latestValue
}

