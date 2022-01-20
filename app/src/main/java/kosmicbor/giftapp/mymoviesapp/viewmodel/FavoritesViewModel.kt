package kosmicbor.giftapp.mymoviesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kosmicbor.giftapp.mymoviesapp.domain.App
import kosmicbor.giftapp.mymoviesapp.domain.repositories.LocalRepoImpl
import kosmicbor.giftapp.mymoviesapp.domain.repositories.RepositoryImpl
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.LocalMovie
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieDTO
import kosmicbor.giftapp.mymoviesapp.view.AppState
import kosmicbor.giftapp.mymoviesapp.view.Error
import kosmicbor.giftapp.mymoviesapp.view.LoadingState
import kosmicbor.giftapp.mymoviesapp.view.Success
import java.util.concurrent.CountDownLatch

class FavoritesViewModel(
    private val favoritesListMutableLiveData: MutableLiveData<AppState<*>> = MutableLiveData(),
    private val localRepo: LocalRepoImpl = LocalRepoImpl(App.getHistoryDao())
) : ViewModel() {

    private var favoritesMoviesList = mutableListOf<MovieDTO>()
    private var localMoviesList = mutableListOf<LocalMovie>()

    fun getFavoritesListLiveData(): LiveData<AppState<*>> = favoritesListMutableLiveData

    @Synchronized
    fun getFavoritesList() {
        favoritesListMutableLiveData.value = LoadingState

        Thread {

            localRepo.getAllHistory(object : LocalRepoImpl.GetAllHistoryListener<List<LocalMovie>> {
                @Synchronized
                override fun loadSuccess(value: List<LocalMovie>) {
                    val cdl = CountDownLatch(value.size)

                        favoritesMoviesList.addAll(convertLocalMovieToDTO(value, cdl))
                        favoritesListMutableLiveData.postValue(Success(favoritesMoviesList))
                }

                override fun loadError(throwable: Throwable) {
                    favoritesListMutableLiveData
                        .postValue(Error<Throwable>(Exception(throwable.localizedMessage)))
                }
            })

        }.start()
    }

    @Synchronized
    fun convertLocalMovieToDTO(
        localMovieList: List<LocalMovie>,
        cdl: CountDownLatch
    ): List<MovieDTO> {

        val list = mutableListOf<MovieDTO>()

        localMovieList.forEach {
            it.id?.apply {
                RepositoryImpl.getRemoteMovieData(
                    this,
                    object : RepositoryImpl.OnMovieLoadListener<MovieDTO> {

                        @Synchronized
                        override fun loadSuccess(value: MovieDTO) {
                            try {
                                list.add(value)
                            } finally {
                                cdl.countDown()
                            }
                        }

                        @Synchronized
                        override fun loadError(throwable: Throwable) {
                            favoritesListMutableLiveData
                                .postValue(
                                    Error<Throwable>(
                                        Exception(throwable.localizedMessage)
                                    )
                                )
                            cdl.countDown()
                        }
                    })
            }
        }

        try {
            cdl.await()
            return list
        } catch (e:Exception) {
            throw e.fillInStackTrace()
        }
    }
}