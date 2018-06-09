package com.levnovikov.feature_date_selector.date_selector

import android.app.Activity
import android.app.DatePickerDialog
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.core_common.defaultError
import com.levnovikov.core_common.mvp_mvvm.Active
import com.levnovikov.feature_date_selector.date_selector.dependencies.DateStreamProvider
import com.levnovikov.feature_date_selector.date_selector.dependencies.OnDateSelectedListener
import com.levnovikov.feature_date_selector.date_selector.di.DateSelectorScope
import com.levnovikov.system_lifecycle.activity.Lifecycle
import java.util.*
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 14/3/18.
 */

interface DateSelectorPresenter : Active {
    fun onClearClicked()
    fun showDatePickerDialog()
}

@DateSelectorScope
class DateSelectorPresenterImpl @Inject constructor(
        private val onDateSelectedListener: OnDateSelectedListener,
        private val activity: Activity,
        private val view: SelectorView,
        private val lifecycle: Lifecycle,
        private val asyncHelper: AsyncHelper,
        private val dateStreamProvider: DateStreamProvider
) : DateSelectorPresenter {

    private val currentDate: Calendar = GregorianCalendar()

    override fun onGetActive() {
        lifecycle.subscribeUntilDestroy(dateStreamProvider.getDateStream()
                .compose(asyncHelper.observeInMain())
                .subscribe({
                    view.setDate(if (it.isPresent) {
                        currentDate.time = it.get()
                        GregorianCalendar().apply { this.time = it.get() }
                    } else {
                        currentDate.time = Date()
                        null
                    })
                }, defaultError))
    }

    override fun showDatePickerDialog() {
        currentDate.let {
            val dialog = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { _, y, m, d ->
                onDateSelectedListener.onDateSelected(GregorianCalendar().apply { set(y, m, d) }.time)
            }, it.get(Calendar.YEAR), it.get(Calendar.MONTH), it.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }
    }

    override fun onClearClicked() {
        onDateSelectedListener.onDateSelected(null)
    }

    private fun doNothing() {

    }
}