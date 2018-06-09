package com.levnovikov.feature_date_selector.date_selector.di

import com.levnovikov.feature_date_selector.date_selector.DateSelectorPresenter
import com.levnovikov.feature_date_selector.date_selector.DateSelectorPresenterImpl
import com.levnovikov.feature_date_selector.date_selector.DateSelectorView
import com.levnovikov.feature_date_selector.date_selector.SelectorView
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Subcomponent
import javax.inject.Scope

/**
 * Author: lev.novikov
 * Date: 14/3/18.
 */

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class DateSelectorScope

@DateSelectorScope
@Subcomponent(modules = [DateSelectorComponent.DateSelectorModule::class])
interface DateSelectorComponent {

    @Module
    interface DateSelectorModule {
        @Binds fun bindSelectorView(impl: DateSelectorView): SelectorView
        @Binds fun bindDateSelectorPresenter(impl: DateSelectorPresenterImpl): DateSelectorPresenter
    }

    @Subcomponent.Builder
    interface Builder {
        fun build(): DateSelectorComponent

        @BindsInstance
        fun view(view: DateSelectorView): Builder
    }

    fun inject(dateSelectorView: DateSelectorView)
}