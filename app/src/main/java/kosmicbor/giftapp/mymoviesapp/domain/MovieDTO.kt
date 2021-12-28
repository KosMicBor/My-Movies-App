package kosmicbor.giftapp.mymoviesapp.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDTO(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("adult")
    val adult: Boolean? = false,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: String?,
    @SerializedName("budget")
    val budget: Long?,
    @SerializedName("genres")
    val genres: List<Genre>?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<Company>?,
    @SerializedName("production_countries")
    val productionCountries: List<Country>?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("revenue")
    val revenue: Int?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("vote_average")
    val voteAverage: Number?,
    @SerializedName("vote_count")
    val voteCount: Int?
) : Parcelable