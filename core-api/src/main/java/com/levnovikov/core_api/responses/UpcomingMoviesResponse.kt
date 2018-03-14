package com.levnovikov.core_api.responses

import com.google.gson.annotations.SerializedName
import com.levnovikov.core_api.entities.Movie

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */
data class UpcomingMoviesResponse(
        val page: Int,
        val results: List<Movie>,
        @SerializedName("total_pages") val totalPages: Int
)