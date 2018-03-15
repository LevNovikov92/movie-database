package com.levnovikov.core_api.entities

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */
data class MovieDetails(
        val id: Int,
        val title: String,
        @SerializedName("poster_path") val imgUrl: String,
        @SerializedName("overview") val desc: String,
        @SerializedName("release_date") val date: Date)