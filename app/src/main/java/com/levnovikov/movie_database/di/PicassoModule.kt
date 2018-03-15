package com.levnovikov.movie_database.di

import android.content.Context
import android.net.Uri
import com.levnovikov.core_network.BASE_URL
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Author: lev.novikov
 * Date: 15/3/18.
 */

const val BASE_IMAGE_URL = "BASE_IMAGE_URL"

@Module
class PicassoModule {

    @Provides
    fun providePicasso(@Named(BASE_IMAGE_URL) baseUrl: String, context: Context): Picasso =
            Picasso.Builder(context)
                    .requestTransformer {
                        it.buildUpon()
                                .setUri(Uri.parse(baseUrl + it.uri.toString()))
                                .build()
                    }.build()
}