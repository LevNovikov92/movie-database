package com.levnovikov.movie_database.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */

const val APP_CONTEXT = "APP_CONTEXT"

@Singleton
@Component(modules = [AppComponent.AppModule::class, RetrofitModule::class])
interface AppComponent {

    @Module
    class AppModule {

        @Named(APP_CONTEXT)
        fun provideContext(context: Context) = context
    }

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun context(context: Context): Builder
    }

    fun context(): Context
    fun retrofit(): Retrofit
}