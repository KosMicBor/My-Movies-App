package kosmicbor.giftapp.mymoviesapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    // некотые из свойств спрятаны в окмментарии, чтобы упростить разработку приложения
    // на начальном этапе.
    //val id: Int,
    //val adult: Boolean = false,
    //val belongs_to_collection: MoviesCollection,
    //val budget: Long = 0,
    //val genres: Genres,
    //val originalTitle: String,
    val title: String,
    val overview: String,
    //val productionCountries: String,
    val releaseDate: String,
    // val revenue: Int,
    //val runtime: Int,
    //val spokenLanguages: String,
    //val status: String,
    //val tagline: String,
    val voteAverage: Number,
    //val voteCount: Int
) : Parcelable