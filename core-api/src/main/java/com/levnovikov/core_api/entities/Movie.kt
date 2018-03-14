package com.levnovikov.core_api.entities

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */
data class Movie(val id: Int, val title: String, @SerializedName("release_date") val date: Date)