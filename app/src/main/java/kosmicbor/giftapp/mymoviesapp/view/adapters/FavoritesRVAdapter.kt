package kosmicbor.giftapp.mymoviesapp.view.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kosmicbor.giftapp.mymoviesapp.Router
import kosmicbor.giftapp.mymoviesapp.databinding.MovieRecyclerviewItemBinding
import kosmicbor.giftapp.mymoviesapp.domain.MovieIntentService
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieDTO

class FavoritesRVAdapter : RecyclerView.Adapter<FavoritesRVAdapter.FavoritesViewHolder>() {

    private val favoriteMoviesList: MutableList<MovieDTO> = mutableListOf()
    var itemClick: FavoritesOnClick? = null

    inner class FavoritesViewHolder(val binding: MovieRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding = MovieRecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.binding.apply {
            itemMovieTitle.text = favoriteMoviesList[position].title
            itemMovieYear.text = favoriteMoviesList[position].releaseDate
            itemMovieRating.text = favoriteMoviesList[position].voteAverage.toString()
            itemImage.load("https://image.tmdb.org/t/p/w500${favoriteMoviesList[position].backdropPath}")
            root.setOnClickListener {
                it.context.startService(
                    Intent(
                        it.context,
                        MovieIntentService::class.java
                    ).apply {
                        putExtra(MovieIntentService.MOVIE_ID_KEY, favoriteMoviesList[position].id)
                    })

                Router.openFragmentMoviePage((it.context as AppCompatActivity).supportFragmentManager)
            }
        }

    }

    override fun getItemCount() = favoriteMoviesList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setFavoritesMovies(moviesList: List<MovieDTO>) {
        favoriteMoviesList.clear()
        favoriteMoviesList.addAll(moviesList)
        notifyDataSetChanged()
    }

    fun interface FavoritesOnClick {
        fun onClick(movie: MovieDTO)
    }
}







