package date_selector

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.levnovikov.core_common.ComponentProvider
import com.levnovikov.movie_database.R
import date_selector.di.DateSelectorComponent
import java.util.*
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 14/3/18.
 */

interface SelectorView {
    fun setDate(currentDate: Calendar?)
}

class DateSelectorView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), SelectorView {

    private lateinit var dateTextView: TextView

    @Inject
    lateinit var presenter: DateSelectorPresenter

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        dateTextView = findViewById(R.id.date)
        setupDI()

        this.setOnClickListener {
            presenter.showDatePickerDialog()
        }

        findViewById<View>(R.id.clear).setOnClickListener {
            presenter.onClearClicked()
        }
        presenter.onGetActive()
    }

    @Suppress("ReplaceSingleLineLet")
    private fun setupDI() {
        context.let {
            if (it is ComponentProvider) {
                it.getComponent(DateSelectorComponent.Builder::class)?.let {
                    it
                            .view(this)
                            .build()
                            .inject(this)
                }
            }
        }
    }

    override fun setDate(currentDate: Calendar?) {
        dateTextView.text = currentDate?.let {
            "${it.get(Calendar.DATE)}.${it.get(Calendar.MONTH) + 1}.${it.get(Calendar.YEAR)}"
        } ?: context.getString(R.string.all_dates)
    }
}