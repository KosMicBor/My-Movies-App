package kosmicbor.giftapp.mymoviesapp.domain

import android.os.Looper
import kosmicbor.giftapp.mymoviesapp.view.AppState
import kosmicbor.giftapp.mymoviesapp.view.Error
import kosmicbor.giftapp.mymoviesapp.view.Success
import java.lang.RuntimeException
import java.util.concurrent.Executors
import kotlin.random.Random

class RepositoryImpl : Repository {

    private val executor = Executors.newSingleThreadExecutor()
    private val mainThreadHandler = android.os.Handler(Looper.getMainLooper())

    override fun createMovies(): List<Movie> {

        return listOf(
            Movie("Мортал Комбат", "Фильм про драки", 7.0),
            Movie("Сияние", "Страшно", 8.0),
            Movie("Зелёная миля", "Шедевр по Кингу", 10.0),
            Movie("Топган", "Фильм про самолёты", 6.5),
            Movie("Горячие головы", "Смешной фильм про самолёты", 7.5),
            Movie("Звёзные войны", "Звёздная сага", 7.5)
        )
    }

    override fun getData(callback: (result: AppState<List<Movie>>) -> Unit) {
        executor.execute {

            Thread.sleep(2000L)

            val isItHappened = Random.nextBoolean()

            if (isItHappened) {

                val moviesList = createMovies()

                mainThreadHandler.post {
                    callback(Success(moviesList))
                }
            } else {

                mainThreadHandler.post {
                    callback(Error(RuntimeException("Can't load movies base")))
                }
            }
        }
    }

    fun shutDown() {
        executor.shutdown()
    }
}
