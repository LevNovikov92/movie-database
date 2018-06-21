package feature_movie_details

import activity.LifecycleActivity
import android.os.Bundle
import com.levnovikov.core_common.ComponentProvider
import com.squareup.picasso.Picasso
import feature_movie_details.di.DaggerMovieDetailsComponent
import feature_movie_details.di.MovieDetailsDependencies
import javax.inject.Inject

/**
 * MovieDetailsActivity component will follow MVVM design pattern with android data binding
 */

class MovieDetailsActivity : LifecycleActivity() {

    @Inject
    lateinit var vm: MovieDetailsViewModel

    @Inject
    lateinit var vmActions: ViewModelActions

    @Inject
    lateinit var picasso: Picasso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDI()
//        setupBinding()
//        setupImageLoader()
        vmActions.onGetActive()
    }

//    private fun setupImageLoader() {
//        vm.data.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
//            override fun onPropertyChanged(p0: Observable?, p1: Int) {
//                picasso.load(vm.data.get().imgUrl).into(binding.imageView)
//            }
//        })
//    }

    private fun setupDI() {
        applicationContext.let {
            if (it is ComponentProvider) {
                it.getComponent(MovieDetailsDependencies::class)?.let {
                    DaggerMovieDetailsComponent.builder()
                            .activity(this)
                            .dependencies(it)
                            .build()
                            .inject(this)
                }
            }
        }
    }

//    private lateinit var binding: ActivityMovieDetailsBinding

//    private fun setupBinding() {
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
//        binding.vm = vm
//    }
}
