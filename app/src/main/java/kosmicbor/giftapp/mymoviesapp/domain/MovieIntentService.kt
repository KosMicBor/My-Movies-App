package kosmicbor.giftapp.mymoviesapp.domain

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kosmicbor.giftapp.mymoviesapp.domain.repositories.RepositoryImpl
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieDTO

class MovieIntentService : IntentService("MovieIntentService") {

    companion object {
        const val MOVIE_ID_KEY = "MOVIE_ID_KEY"
        const val DEFAULT_MOVIE = 0
        const val REQUEST_MOVIE_RESULT = "REQUEST_MOVIE_RESULT"
        const val RECEIVED_MOVIE = "RECEIVED_MOVIE"

    }

    override fun onHandleIntent(intent: Intent?) {
        intent?.getIntExtra(MOVIE_ID_KEY, DEFAULT_MOVIE)?.let { movieId ->

            RepositoryImpl.getRemoteMovieData(movieId, object :
                RepositoryImpl.OnMovieLoadListener<MovieDTO> {
                override fun loadSuccess(value: MovieDTO) {
                    Intent(REQUEST_MOVIE_RESULT).apply {
                        putExtra(RECEIVED_MOVIE, value)
                    }.also { intent ->
                        LocalBroadcastManager.getInstance(this@MovieIntentService)
                            .sendBroadcast(intent)
                    }
                }

                override fun loadError(throwable: Throwable) {
                    throw Exception(throwable.localizedMessage)
                }
            })
        }
    }
}
