package kosmicbor.giftapp.mymoviesapp.domain

import kosmicbor.giftapp.mymoviesapp.view.AppState

interface Repository {

    fun createMovies(): List<Movie>
    fun getData(callback: (result: AppState<List<Movie>>) -> Unit)
}
