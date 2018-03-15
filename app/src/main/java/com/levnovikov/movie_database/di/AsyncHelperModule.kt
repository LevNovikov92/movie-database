package com.levnovikov.movie_database.di

import com.levnovikov.core_common.AsyncHelper
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Author: lev.novikov
 * Date: 15/3/18.
 */

@Module
class AsyncHelperModule {

    @Provides
    fun provideAsyncHelper() = AsyncHelper(
            AndroidSchedulers.mainThread(),
            Schedulers.computation(),
            Schedulers.io())
}