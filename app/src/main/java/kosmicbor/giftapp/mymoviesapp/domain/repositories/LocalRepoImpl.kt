package kosmicbor.giftapp.mymoviesapp.domain.repositories

import android.os.Handler
import android.os.Looper
import android.util.Log
import kosmicbor.giftapp.mymoviesapp.domain.convertEntityToMovie
import kosmicbor.giftapp.mymoviesapp.domain.convertEntityToMoviesList
import kosmicbor.giftapp.mymoviesapp.domain.convertMovieToEntity
import kosmicbor.giftapp.mymoviesapp.domain.room.HistoryDao
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.LocalMovie

class LocalRepoImpl(private val localDataSource: HistoryDao) : LocalRepository {

    private val handler = Handler(Looper.myLooper() ?: Looper.getMainLooper())

    override fun getAllHistory(
        getAllHistoryListener: GetAllHistoryListener<List<LocalMovie>>
    ) {

            handler.post {
                Thread {
                    try {
                        getAllHistoryListener.loadSuccess(
                            convertEntityToMoviesList(
                                localDataSource.all()
                            )
                        )
                    } catch (e: Exception) {
                        handler.post { getAllHistoryListener.loadError(Exception(e.localizedMessage)) }
                    }
                }.start()
            }
    }

    override fun saveEntity(movie: LocalMovie) {
            handler.post {
                Thread {
                    try {
                        localDataSource.insert(convertMovieToEntity(movie))
                        Log.d("TAG", convertMovieToEntity(movie).toString())
                    } catch (e: Exception) {
                        throw e.fillInStackTrace()
                    }
                }.start()
            }
    }

    override fun updateEntity(movie: LocalMovie) {

            handler.post {
                Thread {
                    localDataSource.update(convertMovieToEntity(movie))
                    Log.d("UPDATE", convertMovieToEntity(movie).isFavorite.toString())
                }.start()
            }
    }

    override fun removeEntity(movie: LocalMovie) {
        handler.post {
            Thread {
            localDataSource.delete(convertMovieToEntity(movie))
            }.start()
        }
    }

    override fun getEntity(
        id: Int?,
        getEntityListener: GetEntityListener<LocalMovie>
    ) {
            handler.post {
                Thread {
                    try {

                        getEntityListener.loadSuccess(
                            convertEntityToMovie(
                                localDataSource.getDataById(
                                    id
                                )
                            )
                        )
                    } catch (e: Exception) {
                        getEntityListener.loadError(e.fillInStackTrace())
                    }
                }.start()
            }
    }

    interface GetAllHistoryListener<T> {
        fun loadSuccess(value: T)
        fun loadError(throwable: Throwable)
    }

    interface GetEntityListener<T> {
        fun loadSuccess(value: T)
        fun loadError(throwable: Throwable)
    }
}