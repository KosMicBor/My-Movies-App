package kosmicbor.giftapp.mymoviesapp.domain


import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import kosmicbor.giftapp.mymoviesapp.domain.room.HistoryEntity
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.LocalMovie
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieDTO

fun View.viewShow() {
    visibility = View.VISIBLE
}

fun View.viewHide() {
    visibility = View.GONE
}

fun View.showSnackBar(
    view: View,
    @StringRes stringRes: Int,
    length: Int = Snackbar.LENGTH_INDEFINITE,
    @StringRes actionText: Int,
    action: (View) -> Unit
) {
    Snackbar.make(
        view, stringRes,
        length
    ).setAction(actionText, action).show()
}

fun convertEntityToMoviesList(entityList: List<HistoryEntity>): List<LocalMovie> {
    return entityList.map {
        LocalMovie(
            id = it.id,
            title = it.title,
            note = it.note,
            isInFavorite = it.isFavorite
        )
    }
}

fun convertMovieToEntity (movie: LocalMovie): HistoryEntity {
    return HistoryEntity(
        id = movie.id,
        title = movie.title,
        note = movie.note,
        isFavorite = movie.isInFavorite
    )
}

fun convertEntityToMovie(entity: HistoryEntity): LocalMovie {
    return LocalMovie(
            id = entity.id,
            title = entity.title,
            note = entity.note,
            isInFavorite = entity.isFavorite
        )
    }