package kosmicbor.giftapp.mymoviesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kosmicbor.giftapp.mymoviesapp.domain.repositories.RepositoryImpl
import kosmicbor.giftapp.mymoviesapp.view.AppState
import kosmicbor.giftapp.mymoviesapp.view.Success
import kosmicbor.giftapp.mymoviesapp.view.Error
import kosmicbor.giftapp.mymoviesapp.view.LoadingState
import java.util.concurrent.Executors
import kotlin.random.Random

class ProfileViewModel(
    private val moviesListMutableLiveData: MutableLiveData<AppState<*>> = MutableLiveData()
) : ViewModel() {

    private val executor = Executors.newSingleThreadExecutor()

    fun getMoviesListLiveData(): LiveData<AppState<*>> = moviesListMutableLiveData

    fun getMoviesList() {

        moviesListMutableLiveData.value = LoadingState

    }

    override fun onCleared() {
        super.onCleared()
        executor.shutdown()
    }
}