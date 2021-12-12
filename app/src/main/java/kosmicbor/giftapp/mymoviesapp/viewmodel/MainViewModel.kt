package kosmicbor.giftapp.mymoviesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kosmicbor.giftapp.mymoviesapp.domain.Movie
import kosmicbor.giftapp.mymoviesapp.domain.RepositoryImpl
import kosmicbor.giftapp.mymoviesapp.view.Success
import kosmicbor.giftapp.mymoviesapp.view.Error
import kosmicbor.giftapp.mymoviesapp.view.LoadingState

class MainViewModel(
    private val moviesListMutableLiveData: MutableLiveData<List<Movie>> = MutableLiveData(),
    private val loadingMutableLiveData: MutableLiveData<Boolean> = MutableLiveData(false),
    private val errorMutableLiveData: MutableLiveData<String> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) : ViewModel() {

    fun getMoviesListLiveData(): LiveData<List<Movie>> = moviesListMutableLiveData
    fun getErrorListLiveData(): LiveData<String> = errorMutableLiveData
    fun getLoadingListLiveData(): LiveData<Boolean> = loadingMutableLiveData

    fun getMoviesList() {
        loadingMutableLiveData.value = true

        repositoryImpl.getData {
            when (it) {
                is Success -> {
                    val moviesList = it.value
                    moviesListMutableLiveData.value = moviesList
                }

                is Error -> {
                    errorMutableLiveData.value = it.error.toString()
                }

                is LoadingState -> {
                    loadingMutableLiveData.value = it.value
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        repositoryImpl.shutDown()
    }
}