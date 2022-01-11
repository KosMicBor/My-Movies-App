package kosmicbor.giftapp.mymoviesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.Movie
import kosmicbor.giftapp.mymoviesapp.domain.RepositoryImpl
import kosmicbor.giftapp.mymoviesapp.view.AppState
import kosmicbor.giftapp.mymoviesapp.view.Success
import kosmicbor.giftapp.mymoviesapp.view.Error
import kosmicbor.giftapp.mymoviesapp.view.LoadingState
import kotlin.random.Random

class FavoritesViewModel(
    private val FavoritesListMutableLiveData: MutableLiveData<AppState<*>> = MutableLiveData(),
) : ViewModel() {

    fun getFavoritesListLiveData(): LiveData<AppState<*>> = FavoritesListMutableLiveData

    fun getFavoritesList() {

        FavoritesListMutableLiveData.value = LoadingState

            Thread {

                Thread.sleep(2000L)

                val randomBoolean = Random.nextBoolean()

                if (randomBoolean) {
                    val favoritesList = RepositoryImpl.getFavorites()
                    FavoritesListMutableLiveData.postValue(Success(favoritesList))
                } else {
                    FavoritesListMutableLiveData.postValue(Error<Exception>(Exception("Can't load Favorites database")))
                }

            }.start()
        }

    fun isInFavorite(movie: Movie): Boolean {
       return RepositoryImpl.favoritesList.contains(movie)
    }
}