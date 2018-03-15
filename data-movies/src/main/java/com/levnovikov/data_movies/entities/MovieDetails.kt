package com.levnovikov.data_movies.entities

import java.util.*

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */
data class MovieDetails(
        val id: Int,
        val title: String,
        val imgUrl: String,
        val desc: String,
        val date: Date)