package kosmicbor.giftapp.mymoviesapp.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kosmicbor.giftapp.mymoviesapp.R
import kosmicbor.giftapp.mymoviesapp.databinding.MovieRecyclerviewItemBinding
import kosmicbor.giftapp.mymoviesapp.domain.Movie

class FavoritesRVAdapter : RecyclerView.Adapter<FavoritesRVAdapter.FavoritesViewHolder>() {

    private val favoriteMoviesList: MutableList<Movie> = mutableListOf()
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
            itemImage.setImageResource(R.drawable.ic_launcher_background)
            root.setOnClickListener {
                itemClick?.onClick(favoriteMoviesList[position])
            }
        }

    }

    override fun getItemCount() = favoriteMoviesList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setFavoritesMovies(moviesList: List<Movie>) {
        favoriteMoviesList.clear()
        favoriteMoviesList.addAll(moviesList)
        notifyDataSetChanged()
    }

    fun interface FavoritesOnClick {
        fun onClick(movie: Movie)
    }
}







