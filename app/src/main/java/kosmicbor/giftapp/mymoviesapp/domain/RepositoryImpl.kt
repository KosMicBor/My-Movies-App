package kosmicbor.giftapp.mymoviesapp.domain

import android.os.Build
import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.Movie
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieCollection
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieDTO
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection


object RepositoryImpl : Repository {

    override val favoritesList: MutableList<Movie> = mutableListOf()

    private const val API_KEY = "7ef9cd19cb8b91926a8247355c1c2ff5"
    private const val TIMEOUT_TIME = 1000L
    private const val LANGUAGE = "en-US"

    private val handler = Handler(Looper.myLooper() ?: Looper.getMainLooper())

    private val client: OkHttpClient = OkHttpClient.Builder()

        .addInterceptor(Interceptor { chain ->
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader("api_key", API_KEY)
                    .build()
            )
        })

        .callTimeout(TIMEOUT_TIME, TimeUnit.MILLISECONDS)
        .connectTimeout(TIMEOUT_TIME, TimeUnit.MILLISECONDS)

        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .build()

    private val movieAPI: MovieAPI = Retrofit.Builder()
        .client(client)
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
        .create(MovieAPI::class.java)

    @Synchronized
    override fun getRemoteCollectionData(
        collectionName: String,
        onMovieLoadListener: OnMovieLoadListener<MovieCollection>
    ) {
        try {
            movieAPI.getCollection(
                collectionName,
                API_KEY,
                LANGUAGE
            ).execute().apply {
                if (this.isSuccessful) {
                    this.body()?.let { onMovieLoadListener.loadSuccess(it) }
                } else {
                    throw (Exception(this.message()))
                }
            }

        } catch (e: Exception) {
            onMovieLoadListener.loadError(e)
        }
    }

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


    @Synchronized
    override fun getRemoteMovieData(
        movieId: Int,
        onMovieLoadListener: OnMovieLoadListener<MovieDTO>
    ) {

        movieAPI.getMovie(movieId, API_KEY, LANGUAGE)
            .enqueue(object : Callback<MovieDTO> {
                override fun onResponse(call: Call<MovieDTO>, response: Response<MovieDTO>) {
                    if (response.isSuccessful) {
                        response.body()?.let { onMovieLoadListener.loadSuccess(it) }
                    } else {
                        onMovieLoadListener.loadError(Exception(response.message()))
                    }
                }

                override fun onFailure(call: Call<MovieDTO>, t: Throwable) {
                    onMovieLoadListener.loadError(t)
                }

            })
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