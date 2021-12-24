package kosmicbor.giftapp.mymoviesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kosmicbor.giftapp.mymoviesapp.domain.RepositoryImpl
import kosmicbor.giftapp.mymoviesapp.view.AppState
import kosmicbor.giftapp.mymoviesapp.view.Success
import kosmicbor.giftapp.mymoviesapp.view.Error
import kosmicbor.giftapp.mymoviesapp.view.LoadingState
import kotlin.random.Random

class MainViewModel(
    private val moviesListMutableLiveData: MutableLiveData<AppState<*>> = MutableLiveData()
) : ViewModel() {

    fun getMoviesListLiveData(): LiveData<AppState<*>> = moviesListMutableLiveData

    fun getMoviesList() {

        moviesListMutableLiveData.value = LoadingState

        Thread {

            Thread.sleep(2000L)

            val randomBoolean = Random.nextBoolean()

            if (randomBoolean) {
                val moviesList = RepositoryImpl.getCollections()
                moviesListMutableLiveData.postValue(Success(moviesList))
            } else {
                moviesListMutableLiveData.postValue(Error<Exception>(Exception("Can't load movies database")))
            }

        }.start()
    }
}