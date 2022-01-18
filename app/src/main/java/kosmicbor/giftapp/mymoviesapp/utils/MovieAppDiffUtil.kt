package kosmicbor.giftapp.mymoviesapp.utils

import androidx.recyclerview.widget.DiffUtil
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieDTO

class MovieAppDiffUtil(
    private val oldList: List<MovieDTO>,
    private val newList: List<MovieDTO>
) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

}