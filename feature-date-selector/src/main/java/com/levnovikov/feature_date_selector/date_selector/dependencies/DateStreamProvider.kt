package com.levnovikov.feature_date_selector.date_selector.dependencies

import com.google.common.base.Optional
import io.reactivex.Observable
import java.util.*

interface DateStreamProvider {
    fun getDateStream(): Observable<Optional<Date>>
    fun getLatestValue(): Date?
}