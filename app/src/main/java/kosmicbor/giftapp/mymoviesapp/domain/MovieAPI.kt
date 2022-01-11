package kosmicbor.giftapp.mymoviesapp.domain

import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieCollection
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {
    @GET("3/movie/{movie_id}")
    fun getMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String

    ): Call<MovieDTO>

    @GET("3/movie/{collection}")
    fun getCollection(
        @Path("collection") collection: String,
        @Query("api_key") api_key: String,
        @Query("language") language: String

    ): Call<MovieCollection>
}