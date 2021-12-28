package kosmicbor.giftapp.mymoviesapp.domain

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.Movie
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieCollection
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieDTO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection


object RepositoryImpl : Repository {

    override val favoritesList: MutableList<Movie> = mutableListOf()

    private const val API_KEY = "7ef9cd19cb8b91926a8247355c1c2ff5"
    private const val TIMEOUT_TIME = 1000
    private val handler = Handler(Looper.myLooper() ?: Looper.getMainLooper())

    override fun getLocalData(): List<Movie> {
        return listOf(
            Movie("Мортал Комбат", "Фильм про драки", "2021", 7.0),
            Movie("Сияние", "Страшно", "1987", 8.0),
            Movie("Зелёная миля", "Шедевр по Кингу", "1999", 10.0),
            Movie("Топган", "Фильм про самолёты", "1986", 6.5),
            Movie("Горячие головы", "Смешной фильм про самолёты", "1991", 7.5),
            Movie("Звёзные войны: эпизод 4", "Звёздная сага", "1987", 7.5),
            Movie("Звёзные войны: эпизод 5", "Звёздная сага", "1989", 7.5),
            Movie("Звёзные войны: эпизод 6", "Звёздная сага", "1991", 7.5),
            Movie("Звёзные войны: эпизод 1", "Звёздная сага", "2001", 7.5),
            Movie("Звёзные войны: эпизод 2", "Звёздная сага", "2003", 7.5),
            Movie("Звёзные войны: эпизод 3", "Звёздная сага", "2007", 7.5)
        )
    }

    override fun getRemoteCollectionData(
        collectionName: String,
        onMovieLoadListener: OnMovieLoadListener<MovieCollection>
    ) {
        var urlConnection: HttpsURLConnection? = null
        try {
            val uri =
                URL("https://api.themoviedb.org/3/movie/${collectionName}?api_key=${API_KEY}&language=en-US&page=1")

            urlConnection = (uri.openConnection() as HttpsURLConnection).apply {
                requestMethod = "GET"
                addRequestProperty("api_key", API_KEY)
                readTimeout = TIMEOUT_TIME
                connectTimeout = TIMEOUT_TIME
            }
            val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                reader.lines().collect(Collectors.joining("\n"))
            } else {
                throw Exception("Can't build it")
            }

            val collection = Gson().fromJson(result, MovieCollection::class.java)
                onMovieLoadListener.loadSuccess(collection)
        } catch (e: Exception) {
                onMovieLoadListener.loadError(e)
        } finally {
            urlConnection?.disconnect()
        }
    }

    override fun getRemoteMovieData(
        movieId: Int,
        onMovieLoadListener: OnMovieLoadListener<MovieDTO>
    ) {
        var urlConnection: HttpsURLConnection? = null

        try {
            val uri =
                URL("https://api.themoviedb.org/3/movie/${movieId}?api_key=${API_KEY}&language=en-US")

            urlConnection = (uri.openConnection() as HttpsURLConnection).apply {
                requestMethod = "GET"
                addRequestProperty("api_key", API_KEY)
                readTimeout = TIMEOUT_TIME
                connectTimeout = TIMEOUT_TIME
            }
            val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                reader.lines().collect(Collectors.joining("\n"))
            } else {
                throw Exception("Can't build it")
            }

            val movie = Gson().fromJson(result, MovieDTO::class.java)
            onMovieLoadListener.loadSuccess(movie)
        } catch (e: Exception) {
            onMovieLoadListener.loadError(e)
        } finally {
            urlConnection?.disconnect()
        }
    }

    @Synchronized
    override fun getCollections(onCollectionLoadListener: OnCollectionLoadListener<HashMap<String, List<MovieDTO>?>>) {

        var popularList: List<MovieDTO>? = null
        var upcomingList: List<MovieDTO>? = null
        var topRatedList: List<MovieDTO>? = null
        var nowPlayingList: List<MovieDTO>? = null

        Thread {

            try {
                var collectionsList = hashMapOf(
                    "upcoming" to upcomingList,
                    "popular" to popularList,
                    "top_rated" to topRatedList,
                    "now_playing" to nowPlayingList,
                )

                collectionsList.forEach { collection ->
                    getRemoteCollectionData(
                        collection.key,
                        object : OnMovieLoadListener<MovieCollection> {
                            override fun loadSuccess(value: MovieCollection) {
                                collectionsList[collection.key] = value.results
                            }

                            override fun loadError(throwable: Throwable) {
                                throw Exception(throwable)
                            }

                        })
                }
                handler.post {
                    onCollectionLoadListener.loadSuccess(collectionsList)
                }


            } catch (e: Exception) {
                handler.post {
                    onCollectionLoadListener.loadError(e)
                }
            }
        }.start()
    }

    override fun getFavorites(): List<Movie> = favoritesList

    override fun addFavoriteMovie(movie: Movie) {
        favoritesList.add(movie)
    }

    override fun removeFavoriteMovie(movie: Movie) {
        favoritesList.remove(movie)
    }

    interface OnMovieLoadListener<T> {
        fun loadSuccess(value: T)
        fun loadError(throwable: Throwable)
    }

    interface OnCollectionLoadListener<T> {
        fun loadSuccess(value: T)
        fun loadError(throwable: Throwable)
    }
}