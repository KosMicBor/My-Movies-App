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

class MainRVAdapterTwoViewHolders : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_ITEM = 1
        const val TYPE_COLLECTION_TITLE = 0
        const val MOVIE_CONST = "MOVIE_CONST"
    }

    private var moviesList: HashMap<String, List<Movie>> = hashMapOf()
    private lateinit var recyclerView: RecyclerView
    private val itemAdapter = MainRecyclerViewItemAdapter()
    var moviesListItemClick: MainItemOnClick? = null

    inner class MainItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemRecyclerView: RecyclerView = itemView.findViewById(R.id.sub_recycler_view)

    }

    inner class MainTitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val collectionTitle: MaterialTextView =
            itemView.findViewById(R.id.main_recyclerview_collection_name)
    }


    override fun getItemViewType(position: Int): Int {

        // при таком походе на экран выводится только 2 элемента, т.к. размер recyclerview
        // равен размеру moviesList. Как реализовать логику, чтобы при 2 ViewHolder выводились
        // все 4 элемента списка moviesList вместе с заголовками
        return when (position % 2) {
            0 -> {
                TYPE_COLLECTION_TITLE
            }
            else -> {
                TYPE_ITEM
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var itemView: View

        when (viewType) {
            TYPE_COLLECTION_TITLE -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.main_recyclerview_collection_title, parent, false)
                return MainTitleViewHolder(itemView)
            }
            TYPE_ITEM -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.sub_recyclerview_item, parent, false)
                return MainItemViewHolder(itemView)
            }
            else -> {
                throw RuntimeException(
                    "there is no type that matches the type "
                            + viewType + " + make sure your using types correctly"
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainTitleViewHolder -> {
                holder.collectionTitle.text = moviesList.keys.elementAt(position)
            }
            is MainItemViewHolder -> {
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



