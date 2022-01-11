package kosmicbor.giftapp.mymoviesapp.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import kosmicbor.giftapp.mymoviesapp.R
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieDTO
import java.lang.StringBuilder

class MainRVAdapter : RecyclerView.Adapter<MainRVAdapter.MainItemViewHolder>() {

    private var moviesList: HashMap<String, List<MovieDTO>> = hashMapOf()

    inner class MainItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemRecyclerView: RecyclerView = itemView.findViewById(R.id.sub_recycler_view)
        val collectionTitle: MaterialTextView =
            itemView.findViewById(R.id.main_recyclerview_collection_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.collection_recyclerview_item, parent, false)
        return MainItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainItemViewHolder, position: Int) {

        val title = getTitle(moviesList.keys.elementAt(position).uppercase())

        holder.collectionTitle.text = title

        holder.itemRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = MainRVItemAdapter().also { adapter ->
                moviesList[moviesList.keys.elementAt(position)]?.let {
                    adapter.updateList(it)
                }
            }
        }
    }

    private fun getTitle(unformattedTitle: String): String {
        return unformattedTitle.replace('_', ' ')
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newData: HashMap<String, List<MovieDTO>>) {
        moviesList.clear()
        moviesList.putAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount() = moviesList.size
}



