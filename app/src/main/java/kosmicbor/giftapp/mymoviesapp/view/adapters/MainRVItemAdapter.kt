package kosmicbor.giftapp.mymoviesapp.view.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import kosmicbor.giftapp.mymoviesapp.R
import kosmicbor.giftapp.mymoviesapp.Router
import kosmicbor.giftapp.mymoviesapp.domain.MovieIntentService
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieDTO

class MainRVItemAdapter() : RecyclerView.Adapter<MainRVItemAdapter.MainViewHolder>() {

    private var moviesList: MutableList<MovieDTO> = mutableListOf()

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.item_movie_title)
        val movieImage: ShapeableImageView = itemView.findViewById(R.id.item_image)
        val movieYear: TextView = itemView.findViewById(R.id.item_movie_year)
        val movieRating: TextView = itemView.findViewById(R.id.item_movie_rating)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_recyclerview_item, parent, false)
        return MainViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.apply {
            title.text = moviesList[position].title
            movieYear.text = moviesList[position].releaseDate
            movieRating.text = moviesList[position].voteAverage.toString()
            Glide.with(holder.itemView)
                .load("https://image.tmdb.org/t/p/w500${moviesList[position].backdropPath}")
                .into(movieImage)
            //movieImage.setImageResource(R.drawable.ic_launcher_background)
            itemView.setOnClickListener {
                it.context.startService(Intent(it.context, MovieIntentService::class.java).apply {
                    putExtra(MovieIntentService.MOVIE_ID_KEY, moviesList[position].id)
                })

                Router.openFragmentMoviePage((it.context as AppCompatActivity).supportFragmentManager)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newData: List<MovieDTO>) {
        moviesList.clear()
        moviesList.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount() = moviesList.size

    fun interface MoviesListItemOnClick {
        fun onClick(movie: MovieDTO)
    }
}



