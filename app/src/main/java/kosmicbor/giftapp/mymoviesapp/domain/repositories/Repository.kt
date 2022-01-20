package kosmicbor.giftapp.mymoviesapp.domain.repositories

import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieCollection
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieDTO

interface Repository {

    fun getCollections(onCollectionLoadListener: RepositoryImpl.OnCollectionLoadListener<HashMap<String, List<MovieDTO>?>>)
    fun getRemoteCollectionData(collectionName: String ,onMovieLoadListener: RepositoryImpl.OnMovieLoadListener<MovieCollection>)
    fun getRemoteMovieData(movieId: Int, onMovieLoadListener: RepositoryImpl.OnMovieLoadListener<MovieDTO>)
}
