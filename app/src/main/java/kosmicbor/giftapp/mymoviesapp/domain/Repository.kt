package kosmicbor.giftapp.mymoviesapp.domain

import kosmicbor.giftapp.mymoviesapp.view.AppState

interface Repository {

    val favoritesList: MutableList<Movie>

    fun getLocalData(): List<Movie>
    //fun getCollection(): HashMap<String,List <MovieDTO>?>
    fun getCollections(onCollectionLoadListener: RepositoryImpl.OnCollectionLoadListener<HashMap<String, List<MovieDTO>?>>)
    fun getFavorites(): List <Movie>
    fun addFavoriteMovie(movie: Movie)
    fun removeFavoriteMovie(movie: Movie)
    fun getRemoteCollectionData(collectionName: String ,onMovieLoadListener: RepositoryImpl.OnMovieLoadListener<MovieCollection>)
}
