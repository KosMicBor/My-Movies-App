package kosmicbor.giftapp.mymoviesapp.domain.tmdbdata

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieCollection(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieDTO>,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int
) : Parcelable
