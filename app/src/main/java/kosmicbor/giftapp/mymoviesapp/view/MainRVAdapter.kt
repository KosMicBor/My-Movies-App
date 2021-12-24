package kosmicbor.giftapp.mymoviesapp.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import kosmicbor.giftapp.mymoviesapp.MainActivity
import kosmicbor.giftapp.mymoviesapp.R

import kosmicbor.giftapp.mymoviesapp.domain.Movie

class MainRVAdapter (val fragment: Fragment): RecyclerView.Adapter<MainRVAdapter.MainViewHolder>(), MoviesListAdapterClickListener {

    private var moviesList: MutableList<Movie> = mutableListOf()
    private lateinit var moviesListAdapterClickListener: MoviesListAdapterClickListener

    class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.movie_title)
        val movieImage: ShapeableImageView = itemView.findViewById(R.id.recycler_view_item_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_recyclerview_item, parent, false)
        return MainViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.title.text = moviesList[position].title
        holder.movieImage.setImageResource(R.drawable.ic_launcher_background)
        moviesListAdapterClickListener = fragment.activity as MoviesListAdapterClickListener
        holder.itemView.setOnClickListener {
            moviesListAdapterClickListener.onClick(moviesList[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newData: List<Movie>) {
        moviesList.clear()
        moviesList.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount() = moviesList.size

    override fun onClick(movie: Movie) {
        TODO("Not yet implemented")
    }
}

interface MoviesListAdapterClickListener {
    fun onClick(movie: Movie)
}

