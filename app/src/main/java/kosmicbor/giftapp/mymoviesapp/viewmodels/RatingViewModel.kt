package kosmicbor.giftapp.mymoviesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kosmicbor.giftapp.mymoviesapp.view.AppState
import kosmicbor.giftapp.mymoviesapp.view.LoadingState
import java.util.concurrent.Executors

class RatingViewModel(
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