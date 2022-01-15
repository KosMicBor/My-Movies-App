package kosmicbor.giftapp.mymoviesapp.domain.tmdbdata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocalMovie(
    val id: Int?,
    val title: String?,
    val note: String? = "",
    val isInFavorite: Boolean = false

) : Parcelable