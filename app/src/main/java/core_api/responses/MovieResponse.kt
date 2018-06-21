package core_api.responses

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */
data class MovieResponse(
        val id: Int,
        val title: String,
        @SerializedName("release_date") val date: Date)