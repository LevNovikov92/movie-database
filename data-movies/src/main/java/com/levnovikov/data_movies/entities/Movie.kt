package com.levnovikov.data_movies.entities

import java.util.*

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */

data class Movie(val id: Int, val title: String)

data class PagerMetadata(val totalPages: Int)