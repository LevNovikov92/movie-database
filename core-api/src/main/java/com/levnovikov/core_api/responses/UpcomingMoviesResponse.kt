package com.levnovikov.core_api.responses

import com.levnovikov.core_api.entity.Movie

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */
data class UpcomingMoviesResponse(val page: Int, val results: List<Movie>)