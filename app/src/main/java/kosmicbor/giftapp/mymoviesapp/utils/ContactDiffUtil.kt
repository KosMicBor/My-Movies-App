package kosmicbor.giftapp.mymoviesapp.utils

import androidx.recyclerview.widget.DiffUtil
import kosmicbor.giftapp.mymoviesapp.domain.contacts_data.Contact

class ContactDiffUtil(
    private val oldList: List<Contact>,
    private val newList: List<Contact>
) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.name == newItem.name && oldItem.phone == newItem.phone
    }

}