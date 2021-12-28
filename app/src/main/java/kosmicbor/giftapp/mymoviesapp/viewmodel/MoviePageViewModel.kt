package kosmicbor.giftapp.mymoviesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.Movie
import kosmicbor.giftapp.mymoviesapp.domain.RepositoryImpl
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieDTO

class MoviePageViewModel(
    private val favoritesListMutableLiveData: MutableLiveData<Movie> = MutableLiveData()
) : ViewModel() {

    fun liveDataToObserve() : LiveData<Movie> = favoritesListMutableLiveData

    fun addFavoriteMovie(movie: Movie) {
        RepositoryImpl.addFavoriteMovie(movie)
    }

    fun removeFavoriteMovie(movie: Movie) {
        RepositoryImpl.removeFavoriteMovie(movie)
    }

    fun isMovieFavorite(movie: Movie): Boolean {
        return RepositoryImpl.favoritesList.contains(movie)
    }

}