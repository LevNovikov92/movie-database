package movies_list.di

import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import feature_list_of_films_interface.MoviesListDependency
import movies_list.ListView
import movies_list.MoviesListAdapter
import movies_list.MoviesListAdapterImpl
import movies_list.MoviesListPresenter
import movies_list.MoviesListPresenterImpl
import movies_list.MoviesListView
import movies_list.NavigatorModule
import javax.inject.Scope

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class MoviesListScope

@MoviesListScope
@Component(dependencies = [MoviesListDependency::class],
        modules = [MoviesListComponent.MoviesListModule::class, NavigatorModule::class])
interface MoviesListComponent {

    @Module
    interface MoviesListModule {
        @Binds fun bindListView(view: MoviesListView): ListView
        @Binds fun bindPresenter(impl: MoviesListPresenterImpl): MoviesListPresenter
        @Binds fun bindAdapter(impl: MoviesListAdapterImpl): MoviesListAdapter
    }

    @Component.Builder
    interface Builder {
        fun build(): MoviesListComponent
        fun dependency(dependency: MoviesListDependency): Builder

        @BindsInstance
        fun view(view: MoviesListView): Builder
    }

    fun inject(moviesListView: MoviesListView)

}