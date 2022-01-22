package kosmicbor.giftapp.mymoviesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kosmicbor.giftapp.mymoviesapp.domain.App.Companion.getHistoryDao
import kosmicbor.giftapp.mymoviesapp.domain.repositories.LocalRepoImpl
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.LocalMovie
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieDTO

class MoviePageViewModel(
    private val MovieMutableLiveData: MutableLiveData<MovieDTO> = MutableLiveData(),
    private val localRepo: LocalRepoImpl = LocalRepoImpl(getHistoryDao())
) : ViewModel() {

    private var note: String? = ""

    fun getMoviePageLiveData(): LiveData<MovieDTO> = MovieMutableLiveData

    fun updateMovieInDB(movie: MovieDTO?, isFavorite: Boolean, note: String?) {

        movie?.apply {
            localRepo.updateEntity(
                LocalMovie(
                    id = movie.id,
                    title = movie.title,
                    note = note,
                    isInFavorite = true
                )
            )
        }
    }


    fun addMovieToLocalDB(movie: MovieDTO, isFavorite: Boolean) {
        localRepo.saveEntity(
            LocalMovie(
                id = movie.id,
                title = movie.title,
                note = this.note,
                isInFavorite = isFavorite
            )
        )
    }

    fun removeMovieFromFavorites(movie: MovieDTO?) {
        movie?.apply {
            localRepo.updateEntity(
                LocalMovie(
                    id = movie.id,
                    title = movie.title,
                    note = note,
                    isInFavorite = false
                )
            )
        }

    }

    fun isMovieFavorite(movie: MovieDTO?, listener: OnIsFavoriteListener<Boolean>) {

        localRepo.getEntity(movie?.id, object : LocalRepoImpl.GetEntityListener<LocalMovie> {
            override fun loadSuccess(value: LocalMovie) {

                listener.whenSuccess(value.isInFavorite)
            }

            override fun loadError(throwable: Throwable) {
                throw Exception(throwable.localizedMessage)
            }
        })
    }

    fun getMovieNote(movie: MovieDTO?, listener: OnIsFavoriteListener<String?>) {
        movie?.apply {
            localRepo.getEntity(this.id, object : LocalRepoImpl.GetEntityListener<LocalMovie> {
                override fun loadSuccess(value: LocalMovie) {

                    listener.whenSuccess(value.note ?: "")
                }

                override fun loadError(throwable: Throwable) {
                    throw Exception(throwable.localizedMessage + movie.toString())
                }
            })
        }

    }

    fun interface OnIsFavoriteListener<T> {
        fun whenSuccess(value: T)
    }
}
