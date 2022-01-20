package kosmicbor.giftapp.mymoviesapp.domain.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class HistoryEntity (
    @PrimaryKey
    val id: Int?,
    val title: String?,
    val note: String?,
    val isFavorite: Boolean
)