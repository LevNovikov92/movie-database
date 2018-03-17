package com.levnovikov.core_api.responses

import com.google.gson.annotations.SerializedName

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */
data class UpcomingMoviesResponse(
        val page: Int,
        val results: List<MovieResponse>,
        @SerializedName("total_pages") val totalPages: Int
)