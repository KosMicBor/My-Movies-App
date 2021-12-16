package kosmicbor.giftapp.mymoviesapp.viewmodel

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kosmicbor.giftapp.mymoviesapp.domain.RepositoryImpl
import kosmicbor.giftapp.mymoviesapp.view.AppState
import kosmicbor.giftapp.mymoviesapp.view.Success
import kosmicbor.giftapp.mymoviesapp.view.Error
import kosmicbor.giftapp.mymoviesapp.view.LoadingState
import java.util.concurrent.Executors
import kotlin.random.Random

class FavoritesViewModel(
    private val moviesListMutableLiveData: MutableLiveData<AppState<*>> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) : ViewModel() {

    private val executor = Executors.newSingleThreadExecutor()

    fun getMoviesListLiveData(): LiveData<AppState<*>> = moviesListMutableLiveData

    fun getMoviesList() {

        moviesListMutableLiveData.value = LoadingState

        executor.execute {
            Thread {

                Thread.sleep(2000L)

                val randomBoolean = Random.nextBoolean()

                if (randomBoolean) {
                    val moviesList = repositoryImpl.getLocalData()
                    moviesListMutableLiveData.postValue(Success(moviesList))
                } else {
                    moviesListMutableLiveData.postValue(Error<Exception>(Exception("Can't load movies database")))
                }

            }.start()
        }
    }

    override fun onCleared() {
        super.onCleared()
        executor.shutdown()
    }
}