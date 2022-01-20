package kosmicbor.giftapp.mymoviesapp.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import kosmicbor.giftapp.mymoviesapp.R
import kosmicbor.giftapp.mymoviesapp.Router
import kosmicbor.giftapp.mymoviesapp.domain.MovieIntentService
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieDTO
import kosmicbor.giftapp.mymoviesapp.utils.MovieAppDiffUtil

class MainRVItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val IS_ADULT = "IS_ADULT"
        const val ONE_VAL = 1
        const val ZERO_VAL = 0
        const val LOAD_MORE_VIEW = -1
    }

    private var moviesList: MutableList<MovieDTO> = mutableListOf()
    private lateinit var diffUtil: MovieAppDiffUtil

    inner class MovieItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.item_movie_title)
        val movieImage: ShapeableImageView = itemView.findViewById(R.id.item_image)
        val movieYear: TextView = itemView.findViewById(R.id.item_movie_year)
        val movieRating: TextView = itemView.findViewById(R.id.item_movie_rating)

    }

    inner class LoadMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardView: MaterialCardView = itemView.findViewById(R.id.load_more_card_view)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == LOAD_MORE_VIEW) {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.load_more_recyclerview_item, parent, false)
            LoadMoreViewHolder(itemView)
        } else {
            val itemView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.movie_recyclerview_item, parent, false)
            MovieItemHolder(itemView)
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if (position == moviesList.size) {
            LOAD_MORE_VIEW
        } else {
            super.getItemViewType(position)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is MovieItemHolder -> {
                if ((holder.itemView.context as AppCompatActivity).getPreferences(Context.MODE_PRIVATE)
                        .getBoolean(IS_ADULT, false)
                ) {

                    if (moviesList[position].adult) {
                        drawItem(holder, position)

                    } else {
                        holder.itemView.layoutParams.width = ZERO_VAL
                        holder.itemView.layoutParams.height = ZERO_VAL
                    }

                } else {
                    drawItem(holder, position)
                }
            }
            is LoadMoreViewHolder -> {
                holder.cardView.setOnClickListener {
                    //TODO добавить загрузку следующей страницы фильмов из коллекции
                }
            }
        }
    }

    private fun drawItem(holder: MainRVItemAdapter.MovieItemHolder, position: Int) {


        holder.apply {
            title.text = moviesList[position].title
            movieYear.text = moviesList[position].releaseDate
            movieRating.text = moviesList[position].voteAverage.toString()
            Glide.with(holder.itemView)
                .load("https://image.tmdb.org/t/p/w500${moviesList[position].backdropPath}")
                .into(movieImage)
            itemView.setOnClickListener {
                it.context.startService(
                    Intent(
                        it.context,
                        MovieIntentService::class.java
                    ).apply {
                        putExtra(MovieIntentService.MOVIE_ID_KEY, moviesList[position].id)
                    })

                Router.openFragmentMoviePage((it.context as AppCompatActivity).supportFragmentManager)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newData: List<MovieDTO>) {
        diffUtil = MovieAppDiffUtil(moviesList, newData)
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        moviesList.clear()
        moviesList.addAll(newData)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = moviesList.size + ONE_VAL
}



