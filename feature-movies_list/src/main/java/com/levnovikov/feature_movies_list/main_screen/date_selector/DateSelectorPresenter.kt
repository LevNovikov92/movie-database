package com.levnovikov.feature_movies_list.main_screen.date_selector

import android.app.Activity
import android.app.DatePickerDialog
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.feature_movies_list.main_screen.DateStreamProvider
import com.levnovikov.feature_movies_list.main_screen.OnDateSelectedListener
import com.levnovikov.feature_movies_list.main_screen.date_selector.di.DateSelectorScope
import com.levnovikov.system_lifecycle.activity.Lifecycle
import java.util.*
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 14/3/18.
 */

@DateSelectorScope
class DateSelectorPresenter @Inject constructor(
        private val onDateSelectedListener: OnDateSelectedListener,
        private val activity: Activity,
        private val view: SelectorView,
        lifecycle: Lifecycle,
        asyncHelper: AsyncHelper,
        dateStreamProvider: DateStreamProvider
) {

    private val currentDate: Calendar = GregorianCalendar()

    init {
        lifecycle.subscribeUntilDestroy(dateStreamProvider.getDateStream()
                .compose(asyncHelper.observeInMain())
                .subscribe({
                    view.setDate(if (it.isPresent) {
                        currentDate.time = it.get()
                        GregorianCalendar().apply { it.get() }
                    } else {
                        currentDate.time = Date()
                        null
                    })
                }, { TODO() }))
    }

    fun showDatePickerDialog() {
        currentDate.let {
            val dialog = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { _, y, m, d ->
                onDateSelectedListener.onDateSelected(GregorianCalendar().apply { set(y, m, d) }.time)
            }, it.get(Calendar.YEAR), it.get(Calendar.MONTH), it.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }
    }

    fun onClearClicked() {
        onDateSelectedListener.onDateSelected(null)
    }
}