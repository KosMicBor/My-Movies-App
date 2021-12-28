package kosmicbor.giftapp.mymoviesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieDTO
import kosmicbor.giftapp.mymoviesapp.domain.RepositoryImpl
import kosmicbor.giftapp.mymoviesapp.view.AppState
import kosmicbor.giftapp.mymoviesapp.view.Success
import kosmicbor.giftapp.mymoviesapp.view.Error
import kosmicbor.giftapp.mymoviesapp.view.LoadingState

class MainViewModel(
    private val moviesListMutableLiveData: MutableLiveData<AppState<*>> = MutableLiveData()
) : ViewModel() {

    fun getMoviesListLiveData(): LiveData<AppState<*>> = moviesListMutableLiveData

    @Synchronized
    fun getMoviesList() {

        moviesListMutableLiveData.value = LoadingState

        Thread {

            RepositoryImpl.getCollections(object :
                RepositoryImpl.OnCollectionLoadListener<HashMap<String, List<MovieDTO>?>> {
                override fun loadSuccess(value: HashMap<String, List<MovieDTO>?>) {

                    moviesListMutableLiveData.postValue(Success(value))

                }

                override fun loadError(throwable: Throwable) {
                    moviesListMutableLiveData.postValue(Error<Exception>(Exception("Can't load movies database")))
                }

            })

        }.start()
    }
}