package kosmicbor.giftapp.mymoviesapp.domain

import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.Movie
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieCollection
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieDTO

interface Repository {

    val favoritesList: MutableList<Movie>

    fun getLocalData(): List<Movie>
    fun getCollections(onCollectionLoadListener: RepositoryImpl.OnCollectionLoadListener<HashMap<String, List<MovieDTO>?>>)
    fun getFavorites(): List <Movie>
    fun addFavoriteMovie(movie: Movie)
    fun removeFavoriteMovie(movie: Movie)
    fun getRemoteCollectionData(collectionName: String ,onMovieLoadListener: RepositoryImpl.OnMovieLoadListener<MovieCollection>)
    fun getRemoteMovieData(movieId: Int, onMovieLoadListener: RepositoryImpl.OnMovieLoadListener<MovieDTO>)
}
