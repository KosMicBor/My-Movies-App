package kosmicbor.giftapp.mymoviesapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import kosmicbor.giftapp.mymoviesapp.R
import kosmicbor.giftapp.mymoviesapp.domain.Movie

class MainRVAdapter : RecyclerView.Adapter<MainRVAdapter.MainItemViewHolder>() {

    companion object {
        const val MOVIE_CONST = "MOVIE_CONST"
    }

    private var moviesList: HashMap<String, List<Movie>> = hashMapOf()
    private lateinit var recyclerView: RecyclerView
    private val itemAdapter = MainRecyclerViewItemAdapter()
    var moviesListItemClick: MainItemOnClick? = null

    inner class MainItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemRecyclerView: RecyclerView = itemView.findViewById(R.id.sub_recycler_view)
        val collectionTitle: MaterialTextView =
            itemView.findViewById(R.id.main_recyclerview_collection_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRVAdapter.MainItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.sub_recyclerview_item, parent, false)
        return MainItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainRVAdapter.MainItemViewHolder, position: Int) {

        holder.collectionTitle.text = moviesList.keys.elementAt(position)

        recyclerView = holder.itemRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = itemAdapter.apply {
                moviesList[moviesList.keys.elementAt(position)]?.let { submitList(it) }
                moviesListItemClick = MainRecyclerViewItemAdapter.MoviesListItemOnClick {
                    val bundle = Bundle()
                    bundle.putParcelable(MOVIE_CONST, it)
                    (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.main_container, FragmentMoviePage.newInstance(bundle))
                        .addToBackStack("MoviesPage")
                        .commit()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newData: HashMap<String, List<Movie>>) {
        moviesList.clear()
        moviesList.putAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount() = moviesList.size

    fun interface MainItemOnClick {
        fun onClick(movie: Movie)
    }
}



