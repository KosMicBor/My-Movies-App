package kosmicbor.giftapp.mymoviesapp.domain

import kosmicbor.giftapp.mymoviesapp.view.AppState

interface Repository {

    val favoritesList: MutableList<Movie>

    fun getLocalData(): List<Movie>
    fun getRemoteData()
    fun getCollections(): HashMap<String,List <Movie>>
    fun getFavorites(): List <Movie>
    fun addFavoriteMovie(movie: Movie)
    fun removeFavoriteMovie(movie: Movie)
}
